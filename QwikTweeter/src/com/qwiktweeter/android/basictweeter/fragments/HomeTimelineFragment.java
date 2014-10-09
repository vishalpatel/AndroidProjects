package com.qwiktweeter.android.basictweeter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	protected void getTimeline(int mode, long tweet_id,
			JsonHttpResponseHandler jsonHandler) {
		client.getHomeTimeline(mode, tweet_id,jsonHandler);
	}

	@Override
	protected Boolean shouldSaveTweets() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
