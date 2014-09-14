package com.codepath.vik.instapopviewer;

import org.json.JSONException;
import org.json.JSONObject;

public class InstagramPhoto {
	public String userName;
	public String caption;
	public String imageURL;
	public String userProfileImageUrl;
	
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
		userName = jsonObject.getJSONObject("user").getString("username");
		userProfileImageUrl = jsonObject.getJSONObject("user").getString("profile_picture");
		if (jsonObject.getJSONObject("caption") != null) {
			caption = jsonObject.getJSONObject("caption").getString("text");
		}
		JSONObject stdImageObject = jsonObject.getJSONObject("images").getJSONObject("standard_resolution");
		imageURL = stdImageObject.getString("url");
		imageHeight = stdImageObject.getInt("height");
		likesCount = jsonObject.getJSONObject("likes").getInt("count");
		
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
}
