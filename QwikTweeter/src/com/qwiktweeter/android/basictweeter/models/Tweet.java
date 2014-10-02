package com.qwiktweeter.android.basictweeter.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/*
 Text: [x]=>text
 time: [x]=>created_at
 id: [x]=>id
 retweet_count: [x]=>retweet_count
 entities => media => [x]=> media_url
 entities => media => [x]=> type ''== photo''
 entities => media => [x]=> sizes => small => x,y,resize (fit,crop)
 */
public class Tweet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1645714390462363725L;
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	private String media_photo_url = null;
	private static final int MEDIA_RESIZE_FIT = 1, MEDIA_RESIZE_CROP = 2;
	private int media_size_x = 0, media_size_y = 0, media_size_resize;
	private long retweetCount;
	private long favoriteCount;

	public static Tweet fromJSON(JSONObject obj) {
		Tweet o = new Tweet();
		// deserialize json object
		try {
			o.body = obj.getString("text");
			o.createdAt = obj.getString("created_at");
			o.uid = obj.getLong("id");
			o.retweetCount = obj.getLong("retweet_count");
			o.user = User.fromJSON(obj.getJSONObject("user"));
			o.favoriteCount = obj.optLong("favorite_count", 0);
			if (obj.has("entities")) {
				JSONObject entities = obj.getJSONObject("entities");
				if (entities.has("media")) {
					JSONArray media_entities = entities.getJSONArray("media");
					for (int i = 0; i < media_entities.length(); i++) {
						JSONObject media_e = media_entities.getJSONObject(i);
						if (media_e.has("type") == true
								&& media_e.getString("type").equals("photo")) {
							o.media_photo_url = media_e.getString("media_url");
							JSONObject size_small = media_e.getJSONObject(
									"sizes").getJSONObject("small");
							o.media_size_x = size_small.optInt("w", 200);
							o.media_size_y = size_small.optInt("h", 80);
							if (size_small.has("resize") == true
									&& size_small.equals("fit")) {
								o.media_size_resize = MEDIA_RESIZE_FIT;
							} else {
								o.media_size_resize = MEDIA_RESIZE_CROP;
							}

							break;
						}
					}
				}
			}
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

	public String toString() {
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

	public long getFavoriteCount() {
		return favoriteCount;
	}

	

	public String getCreateTimeAgo() {
		return String.valueOf(DateUtils
				.getRelativeTimeSpanString(getCreateTime()));

	}

	public static Date getTwitterDate(String date) {

		final String twitter_format = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitter_format, Locale.US);
		sf.setLenient(true);
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
	}

	private long getCreateTime() {
		return getTwitterDate(getCreatedAt()).getTime();

	}

	public boolean hasMediaUrl() {
		if (this.media_photo_url != null) {
			return true;
		}
		return false;
	}

	public String getMediaImageUrl() {
		return this.media_photo_url;
	}
	
	public int getMediaPhotoWidth(){
		return this.media_size_x;
	}
	
	public int getMediaPhotoHeight(){
		return this.media_size_y;
	}
	
	public ScaleType getMediaPhotoScaleType(){
		if (this.media_size_resize == MEDIA_RESIZE_CROP) {
		return ImageView.ScaleType.CENTER_CROP;
		}
		return ImageView.ScaleType.FIT_XY;
	}
}
