package com.qwiktweeter.android.basictweeter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 Text: [x]=>text
 time: [x]=>created_at
 id: [x]=>id
 retweet_count: [x]=>retweet_count

 */
public class Tweet {
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	private long retweetCount;

	public static Tweet fromJSON(JSONObject obj) {
		Tweet o = new Tweet();
		// deserialize json object
		try {
			o.body = obj.getString("text");
			o.createdAt = obj.getString("created_at");
			o.uid = obj.getLong("id");
			o.retweetCount = obj.getLong("retweet_count");
			o.user = User.fromJSON(obj.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return o;
	}

	public static ArrayList<Tweet> fromJSON(JSONArray arr) {
		ArrayList<Tweet> datalist = new ArrayList<Tweet>(arr.length());
		for (int i = 0; i < arr.length(); i++) {

			JSONObject jobj = null;
			// deserialize json object
			try {
				jobj = arr.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}

			Tweet o = Tweet.fromJSON(jobj);
			if (o != null) {
				datalist.add(o);
			}
		}
		return datalist;
	}

	public String toString(){
		return this.getUser().getScreenName() + " : " + this.getBody();
	}
	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public long getRetweetCount() {
		return retweetCount;
	}
}
