package com.qwiktweeter.android.basictweeter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.qwiktweeter.android.basictweeter.EndlessScrollListener;
import com.qwiktweeter.android.basictweeter.InternetConnectivity;
import com.qwiktweeter.android.basictweeter.Persistance;
import com.qwiktweeter.android.basictweeter.QwikTweeterApplication;
import com.qwiktweeter.android.basictweeter.TwitterClient;
import com.qwiktweeter.android.basictweeter.models.Tweet;

public class MentionsTimelineFragement extends TweetsListFragment {
	private TwitterClient client;
	private long oldestTweetID;
	private long newestTweetID;
	private Boolean shouldClear;
	private int refresh_mode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		client = QwikTweeterApplication.getRestClient();

		oldestTweetID = 0;
		newestTweetID = 0;
		refresh_mode = 0;
		shouldClear = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);

		setPullToRefreshHandler(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Your code to refresh the list here.
				// Make sure you call swipeContainer.setRefreshing(false)
				// once the network request has completed successfully.
				populateTimeline(TwitterClient.GET_NEW_TWEETS);
			}
		});

		setEndlessScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				populateTimeline(TwitterClient.GET_MORE_TWEETS);

			}
		});

		populateTimeline(TwitterClient.GET_NEW_TWEETS);

		return v;
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
		if (!InternetConnectivity.getInstance(getActivity()).isConnected()) {
			if (getListSize() == 0)
				addAllTweets(Tweet.getAll());
			return;
		}

		client.getMentionsTimeline(mode, tweet_id, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray arr) {
				Log.i("info", arr.toString());
				ArrayList<Tweet> tweetArr = Tweet.fromJSON(arr);

				if (shouldClear) {
					clearAllTweets();
				}
				if (refresh_mode == TwitterClient.GET_MORE_TWEETS) {
					addAllTweets(tweetArr);
				} else {
					ArrayList<Tweet> oldtweets = new ArrayList<Tweet>(
							getAllTweets());
					clearAllTweets();
					addAllTweets(tweetArr);
					addAllTweets(oldtweets);
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
				Persistance.saveAll(null, tweetArr);
				setRefreshingState(false);
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", s.toString());
				Log.d("debug", e.toString());
			}

		});
	}

	

}
