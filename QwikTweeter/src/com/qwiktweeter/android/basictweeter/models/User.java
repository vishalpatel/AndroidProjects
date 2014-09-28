package com.qwiktweeter.android.basictweeter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * username: [x]=>user=>name
 user profile image: [x]=>user=>profile_image_url
 user followers count: [x]=>user=>followers_count
 user tweet count: [x]=>user=>statuses_count
 following: [x]=>user=>following

 */
public class User {
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private long followersCount;
	private long statusesCount;
	private Boolean following;
	
	public static User fromJSON(JSONObject obj) {
		User o = new User();
		// deserialize json object
		try {
			o.name = obj.getString("name");
			o.uid = obj.getLong("id");
			o.screenName = obj.getString("screen_name");
			o.profileImageUrl = obj.getString("profile_image_url");
			o.followersCount = obj.optLong("followers_count");
			o.statusesCount = obj.optLong("statuses_count");
			o.following = obj.optBoolean("following");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return o;
	}

	public static ArrayList<User> fromJSON(JSONArray arr) {
		ArrayList<User> datalist = new ArrayList<User>(arr.length());
		for (int i = 0; i < arr.length(); i++) {

			JSONObject jobj = null;
			// deserialize json object
			try {
				jobj = arr.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}

			User o = User.fromJSON(jobj);
			if (o != null) {
				datalist.add(o);
			}
		}
		return datalist;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getName() {
		return name;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public long getFollowersCount() {
		return followersCount;
	}

	public long getStatusesCount() {
		return statusesCount;
	}

	public Boolean getFollowing() {
		return following;
	}
}
