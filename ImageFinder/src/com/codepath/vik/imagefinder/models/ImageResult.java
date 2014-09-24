package com.codepath.vik.imagefinder.models;
import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class ImageResult implements Serializable{

	private static final long serialVersionUID = -1769682487825391297L;
	public String fullURL, tbUrl, title;
	public int width, height;
	public ImageResult(JSONObject jObj) {
		try {
			this.fullURL = jObj.getString("url");
			this.tbUrl = jObj.getString("tbUrl");
			this.title = jObj.getString("title");
			this.width = jObj.getInt("width");
			this.height = jObj.getInt("height");
		} catch (JSONException e) {
			this.fullURL = this.tbUrl = this.title = "NotAvailable";
			this.width = this.height = 0;
			Log.e("ERROR", e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<ImageResult> fromJSONArray(JSONArray jsonArray){
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i = 0; i<jsonArray.length(); i++) {
			try {
				results.add(new ImageResult(jsonArray.getJSONObject(i)));
			} catch (JSONException e) {
				Log.e("ERROR", e.getMessage());
				e.printStackTrace();
			}
		}
		return results;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.title.toString();
	}
}
