package com.codepath.vik.imagefinder.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.codepath.vik.imagefinder.R;
import com.codepath.vik.imagefinder.adapters.ImageResultsAdapter;
import com.codepath.vik.imagefinder.models.ImageResult;
import com.codepath.vik.imagefinder.models.SearchFilter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	static private int UPDATE_FILTERS = 500;
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;

	private SearchFilter searchFilterSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		searchFilterSettings = new SearchFilter();
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		gvResults.setAdapter(aImageResults);
	}

	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Launch the image display activity.
				Intent i = new Intent(SearchActivity.this, ImageDisplay.class);
				ImageResult imgInfo = imageResults.get(position);
				i.putExtra("result", imgInfo);
				startActivity(i);
			}
		});
	}

	public void onImageSearch(View v) {
		// this will automatically fired when search button is pressed
		String searchString = etQuery.getText().toString();
		if (searchString.length() < 2) {
			return;
		}
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=fall&rsz=8
		String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&"
				+ "q="
				+ Uri.encode(searchString)
				+ searchFilterSettings.getEncodedURIString();
		client.get(searchUrl, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				Log.d("DEBUG", response.toString());
				JSONArray imageResultArray = null;
				if (response.has("responseData")) {
					try {
						imageResultArray = response.getJSONObject(
								"responseData").getJSONArray("results");
						aImageResults.clear(); // clear this only when its new
												// search
						aImageResults.addAll(ImageResult
								.fromJSONArray(imageResultArray));

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e("ERROR", e.getMessage());
					}
					Log.i("INFO", imageResults.toString());
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				Toast.makeText(getApplicationContext(), responseString,
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateSearchFilterSettings() {
		Intent i = new Intent(this, SearchFiltersActivity.class);
		i.putExtra("filters", searchFilterSettings);
		startActivityForResult(i, UPDATE_FILTERS);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == UPDATE_FILTERS) {
			if (resultCode == RESULT_OK) {
				searchFilterSettings = (SearchFilter) data
						.getSerializableExtra("filters");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
