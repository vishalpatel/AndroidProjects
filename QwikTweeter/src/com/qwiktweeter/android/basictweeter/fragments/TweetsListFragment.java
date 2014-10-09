package com.qwiktweeter.android.basictweeter.fragments;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.qwiktweeter.android.basictweeter.EndlessScrollListener;
import com.qwiktweeter.android.basictweeter.InternetConnectivity;
import com.qwiktweeter.android.basictweeter.Persistance;
import com.qwiktweeter.android.basictweeter.QwikTweeterApplication;
import com.qwiktweeter.android.basictweeter.R;
import com.qwiktweeter.android.basictweeter.TweetsArrayAdapter;
import com.qwiktweeter.android.basictweeter.TwitterClient;
import com.qwiktweeter.android.basictweeter.models.Tweet;

abstract public class TweetsListFragment extends Fragment {

	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	private TweetsArrayAdapter aTweets;
	private SwipeRefreshLayout swipeContainer;

	protected TwitterClient client;
	private long oldestTweetID;
	private long newestTweetID;
	private Boolean shouldClear;
	private int refresh_mode;

	abstract protected void getTimeline(int mode, long tweet_id,
			JsonHttpResponseHandler jsonHandler);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetsArrayAdapter(getActivity(), tweets);
		client = QwikTweeterApplication.getRestClient();

		oldestTweetID = 0;
		newestTweetID = 0;
		refresh_mode = 0;
		shouldClear = false;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,
				false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);

		lvTweets.setAdapter(aTweets);
		swipeContainer = (SwipeRefreshLayout) v
				.findViewById(R.id.swipeContainerTimeline);

		swipeContainer.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		setPullToRefreshHandler(new OnRefreshListener() {
			@Override
			public void onRefresh() {
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

	public void setRefreshingState(Boolean state) {
		swipeContainer.setRefreshing(state);
	}

	public void setPullToRefreshHandler(OnRefreshListener listener) {
		if (listener != null)
			swipeContainer.setOnRefreshListener(listener);
	}

	public void setEndlessScrollListener(EndlessScrollListener listener) {
		if (listener != null)
			lvTweets.setOnScrollListener(listener);
	}

	public void addAllTweets(Collection<Tweet> tweets) {
		aTweets.addAll(tweets);
	}

	public void clearAllTweets() {
		aTweets.clear();
		oldestTweetID = 0;
		newestTweetID = 0;

	}

	public int getListSize() {
		return tweets.size();
	}

	public void setListViewToTop() {
		lvTweets.setSelectionAfterHeaderView();

	}

	public Collection<? extends Tweet> getAllTweets() {
		return tweets;
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

		JsonHttpResponseHandler jsonHandler = new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray arr) {

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

		};
		getTimeline(mode, tweet_id, jsonHandler);

	}

}
