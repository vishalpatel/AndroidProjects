package com.codepath.vik.instapopviewer;

import org.apache.http.Header;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class PhotosActivity extends Activity {
	public static final String CLIENT_ID = "a6a9a86df296447db1abcb1878a045c6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        fetchPopularPhotos();
    }


    private void fetchPopularPhotos() {
		// https://api.instagram.com/v1/media/popular?client_id=<clientid>
    	// id = a6a9a86df296447db1abcb1878a045c6
    	// {"data" => [x] => "images" => "standard_resolution" => "url" }
		
    	//setup popular endpoint
    	String popularURL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    	
    	// create network client
    	AsyncHttpClient networkClient = new AsyncHttpClient();
    	
    	networkClient.get(popularURL, new JsonHttpResponseHandler() {
    		// define success and failure callbacks
    		// handle success response
    		@Override
    		public void onSuccess(int statusCode, Header[] headers,
    				JSONObject response) {
    			// TODO Auto-generated method stub
    			super.onSuccess(statusCode, headers, response);
    			// fired when successful results
    			// response is json_response from instagram
    			// {"data" => [x] => "images" => "standard_resolution" => "url" }
    			Log.i("INFO", response.toString());
    		}
    		
    		@Override
    		public void onFailure(int statusCode, Header[] headers,
    				Throwable throwable, JSONObject errorResponse) {
    			// TODO Auto-generated method stub
    			super.onFailure(statusCode, headers, throwable, errorResponse);
    			Log.e("ERROR", "Failed to get popular photos endpoint (" + this.getRequestURI().toString() +"): ["+ Integer.toString(statusCode) +"] response: "  + errorResponse.toString());
    			Toast.makeText(getApplicationContext(), R.string.api_failure_message, Toast.LENGTH_SHORT).show();
    		}
    	});
    	
    	// trigger the network request
    	
    	// handle the successful response
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
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
