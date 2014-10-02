package com.qwiktweeter.android.basictweeter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.qwiktweeter.android.basictweeter.models.Tweet;

public class TimelineActivity extends Activity {
	private static int POST_REQ = 51;
	private TwitterClient client;
	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	private TweetsArrayAdapter aTweets;
	private SwipeRefreshLayout swipeContainer;
	private long oldestTweetID;
	private long newestTweetID;
	private Boolean shouldClear;
	private int refresh_mode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		client = QwikTweeterApplication.getRestClient();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetsArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerTimeline);
		oldestTweetID = 0;
		newestTweetID = 0;
		refresh_mode = 0;
		shouldClear = false;
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Your code to refresh the list here.
				// Make sure you call swipeContainer.setRefreshing(false)
				// once the network request has completed successfully.
				populateTimeline(TwitterClient.GET_NEW_TWEETS);
			}
		});
		swipeContainer.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		populateTimeline(TwitterClient.GET_NEW_TWEETS);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				populateTimeline(TwitterClient.GET_MORE_TWEETS);
				
			}
		});
	}

	public void populateTimeline(int mode) {
		refresh_mode = mode;
		long tweet_id = 1;
		if (mode == TwitterClient.GET_NEW_TWEETS) {
			tweet_id = newestTweetID + 1;
		} else {
			tweet_id = oldestTweetID - 1;
			if (tweet_id < 0) {
				tweet_id = 0;
			}
		}

		client.getHomeTimeline(mode, tweet_id, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray arr) {
				Log.i("info", arr.toString());
				ArrayList<Tweet> tweetArr = Tweet.fromJSON(arr);

				if (shouldClear) {
					aTweets.clear();
				}
				if (refresh_mode == TwitterClient.GET_MORE_TWEETS) {
					aTweets.addAll(tweetArr);
				} else {
					ArrayList<Tweet> oldtweets = new ArrayList<Tweet>(tweets);
					aTweets.clear();
					aTweets.addAll(tweetArr);
					aTweets.addAll(oldtweets);
				}
				for (Tweet t : tweetArr) {
					if (t.getUid() > newestTweetID) {
						newestTweetID = t.getUid();
					}
					if (oldestTweetID == 0) {
						oldestTweetID = t.getUid();
					}
					if (t.getUid() < oldestTweetID) {
						oldestTweetID = t.getUid();
					}
				}
				swipeContainer.setRefreshing(false);
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", s.toString());
				Log.d("debug", e.toString());
			}

		});
	}

	public void onComposeAction(MenuItem v) {

		Intent i = new Intent(this, PostTweetActivity.class);
		startActivityForResult(i, POST_REQ);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == POST_REQ) {
			if (resultCode == RESULT_OK) {
				// pull out the id of the post 
				/*
				Tweet newTweet = (Tweet) data.getSerializableExtra("posted_tweet");
				ArrayList<Tweet> oldtweets = new ArrayList<Tweet>(tweets);
				aTweets.clear();
				aTweets.add(newTweet);
				aTweets.addAll(oldtweets);
				lvTweets.scrollTo(0, 0);
				*/
				populateTimeline(TwitterClient.GET_NEW_TWEETS);
				lvTweets.setSelectionAfterHeaderView();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_timeline, menu);
		return true;
	}

}
