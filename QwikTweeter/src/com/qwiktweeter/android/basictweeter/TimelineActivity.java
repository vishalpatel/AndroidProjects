package com.qwiktweeter.android.basictweeter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.qwiktweeter.android.basictweeter.models.Tweet;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	private TweetsArrayAdapter aTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = QwikTweeterApplication.getRestClient();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetsArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		populateTimeline();
	}

	public void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray arr) {
				Log.i("info", arr.toString());
				aTweets.addAll(Tweet.fromJSON(arr));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", s.toString());
				Log.d("debug", e.toString());
			}

		});
	}
}
