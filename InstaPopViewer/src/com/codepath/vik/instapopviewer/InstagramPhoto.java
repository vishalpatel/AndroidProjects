package com.codepath.vik.instapopviewer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramPhoto {
	public String userName;
	public String caption;
	public String imageURL;
	public String userProfileImageUrl;
	public ArrayList<String> comments;
	public int imageHeight;
	public int likesCount;
	
	public InstagramPhoto() 
	{
		
	}
	
	public InstagramPhoto(JSONObject jsonObject) throws JSONException{
		super();
		// {"data" => [x] => "images" => "standard_resolution" => "url" }
		// {"data" => [x] => "images" => "standard_resolution" => "height" }
		// {"data" => [x] => "images" => "standard_resolution" => "width" }
		// {"data" => [x] => "user" => "username"}
		// {"data" => [x] => "caption" => "text"}
		comments = new ArrayList<String>();
		userName = jsonObject.getJSONObject("user").getString("username");
		userProfileImageUrl = jsonObject.getJSONObject("user").getString("profile_picture");
		if (jsonObject.has("caption")) {
			caption = jsonObject.getJSONObject("caption").getString("text");
		}else {
			caption = "";
		}
		JSONObject stdImageObject = jsonObject.getJSONObject("images").getJSONObject("standard_resolution");
		imageURL = stdImageObject.getString("url");
		imageHeight = stdImageObject.getInt("height");
		likesCount = jsonObject.getJSONObject("likes").getInt("count");
		
		// "comments" => "data" => [X] => "text"
		if (jsonObject.has("comments")) {
			JSONObject jsonComments = jsonObject.getJSONObject("comments");
			if (jsonComments.has("data")) {
				JSONArray jsonCmtData = jsonComments.getJSONArray("data");
				JSONObject jsCmtObj;
				for (int i=0; i < jsonCmtData.length(); i++) {
					jsCmtObj = jsonCmtData.getJSONObject(i);
					if (jsCmtObj.has("text")) {
						comments.add(jsCmtObj.getString("text"));
					}
					if (i > 2){
						break;
					}
				}
			}
		}
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return userName + imageURL;
	}
	
	public String getHumanizedLikeCount() {
		if (this.likesCount < 1000) {
			return Integer.toString(this.likesCount) + " Likes"; 
		}
		return Integer.toString(this.likesCount/1000) + "K Likes";
	}

	public boolean hasComments() {
		if (comments != null && comments.size() > 0)
			return true;
		return false;
	}

	public CharSequence getComments() {
		String retval = "";
		for (String c : comments) {
			retval = c + "\n";
		}
		return retval;
	}
}
