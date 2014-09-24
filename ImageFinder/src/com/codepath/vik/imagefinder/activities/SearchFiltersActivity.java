package com.codepath.vik.imagefinder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.vik.imagefinder.R;
import com.codepath.vik.imagefinder.models.SearchFilter;

public class SearchFiltersActivity extends Activity {
	private SearchFilter currentSearchFilter;
	private Spinner spImgSize, spImgColor, spImgType;
	private EditText etSite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_filters);
		currentSearchFilter = (SearchFilter) getIntent().getSerializableExtra("filters");
		if (currentSearchFilter == null) {
			Toast.makeText(this, "Failed to get search settings", Toast.LENGTH_SHORT).show();
			setResult(RESULT_CANCELED);
			finish();
		}
		
		spImgSize = (Spinner) findViewById(R.id.spSize);
		spImgColor = (Spinner) findViewById(R.id.spColor);
		spImgType = (Spinner) findViewById(R.id.spType);
		etSite = (EditText) findViewById(R.id.etSite);
		
		spImgSize.setSelection(getPosition(getResources().getStringArray(R.array.filter_size_options), currentSearchFilter.size));
		spImgColor.setSelection(getPosition(getResources().getStringArray(R.array.filter_color_options), currentSearchFilter.color));
		spImgType.setSelection(getPosition(getResources().getStringArray(R.array.filter_type_options), currentSearchFilter.type));
		etSite.setText(currentSearchFilter.site);
		//update the views with correct value
	}
	private int getPosition(String[] stringArray, String searchstr) {
		for (int i = 0; i < stringArray.length ; i++) {
			if (searchstr.equalsIgnoreCase(stringArray[i])) {
				return i;
			}
		}
		return 0;
	}
	
	public void onSubmit(MenuItem item){
		// get data from views and save it to the currentSearchFilter
		currentSearchFilter.site = etSite.getText().toString();
		currentSearchFilter.color = getStringForPosition(getResources().getStringArray(R.array.filter_color_options), spImgColor.getSelectedItemPosition());
		currentSearchFilter.type = getStringForPosition(getResources().getStringArray(R.array.filter_type_options), spImgType.getSelectedItemPosition());
		currentSearchFilter.size = getStringForPosition(getResources().getStringArray(R.array.filter_size_options), spImgSize.getSelectedItemPosition());
		
		// set result data 
		Intent data = new Intent();
		data.putExtra("filters", currentSearchFilter);
		setResult(RESULT_OK, data);
		finish();
	}

	private String getStringForPosition(String[] stringArray,
			int selectedItemPosition) {
		if (selectedItemPosition == 0)
			return "";
		return stringArray[selectedItemPosition];
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_filters, menu);
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
}
