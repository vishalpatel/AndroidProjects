package com.qwiktweeter.android.basictweeter.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/*
 * username: [x]=>user=>name
 user profile image: [x]=>user=>profile_image_url
 user followers count: [x]=>user=>followers_count
 user tweet count: [x]=>user=>statuses_count
 following: [x]=>user=>following

 */
@Table(name = "QTUsers")
public class User extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5525042597868378940L;

	@Column(name = "qtuuid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;

	@Column(name = "qtname")
	private String name;

	@Column(name = "qtscreenName")
	private String screenName;

	@Column(name = "qtprofileImageUrl")
	private String profileImageUrl;

	@Column(name = "qtprofileBGImageUrl")
	private String profileBGImageUrl;

	@Column(name = "qtfollowersCount")
	private long followersCount;

	@Column(name = "qtstatusesCount")
	private long statusesCount;

	@Column(name = "qtfriendsCount")
	private long friendsCount;

	@Column(name = "qtfollowing")
	private Boolean following;

	@Column(name = "qtdescription")
	private String description;

	public User() {
		super();
	}

	public static User fromJSON(JSONObject obj) {
		User o = new User();
		// deserialize json object
		try {
			o.name = obj.optString("name");
			o.uid = obj.getLong("id");
			o.screenName = obj.optString("screen_name");
			o.profileImageUrl = obj
					.optString(
							"profile_image_url",
							"https://abs.twimg.com/sticky/default_profile_images/default_profile_2_normal.png");
			o.followersCount = obj.optLong("followers_count");
			o.statusesCount = obj.optLong("statuses_count");
			o.friendsCount = obj.optLong("friends_count");
			o.profileBGImageUrl = obj.optString("profile_background_image_url");
			o.description = obj.optString("description");
			o.following = obj.optBoolean("following");
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("Error", "Failed to convert json to user :" + obj.toString());
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

	public String getProfileBGImageUrl() {
		return profileBGImageUrl;
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

	public long getFriendsCount() {
		return friendsCount;
	}

	public String getDescription() {
		return description;
	}
}
