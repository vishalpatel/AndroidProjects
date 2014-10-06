package com.qwiktweeter.android.basictweeter.fragments;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qwiktweeter.android.basictweeter.EndlessScrollListener;
import com.qwiktweeter.android.basictweeter.R;
import com.qwiktweeter.android.basictweeter.TweetsArrayAdapter;
import com.qwiktweeter.android.basictweeter.models.Tweet;

public class TweetsListFragment extends Fragment {

	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	private TweetsArrayAdapter aTweets;
	private SwipeRefreshLayout swipeContainer;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetsArrayAdapter(getActivity(), tweets);

	}

	@Override
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
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Log.e("Error", "Unimplemented method for swipe to refresh.");
				swipeContainer.setRefreshing(false);
			}
			
		});
		/*
		 * swipeContainer.setOnRefreshListener(new OnRefreshListener() {
		 * 
		 * @Override public void onRefresh() { // Your code to refresh the list
		 * here. // Make sure you call swipeContainer.setRefreshing(false) //
		 * once the network request has completed successfully.
		 * populateTimeline(TwitterClient.GET_NEW_TWEETS); } });
		 * 
		 * populateTimeline(TwitterClient.GET_NEW_TWEETS);
		 * 
		 * lvTweets.setOnScrollListener(new EndlessScrollListener() {
		 * 
		 * @Override public void onLoadMore(int page, int totalItemsCount) {
		 * populateTimeline(TwitterClient.GET_MORE_TWEETS);
		 * 
		 * } });
		 */
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
}
