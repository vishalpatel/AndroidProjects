package com.qwiktweeter.android.basictweeter.fragments;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.qwiktweeter.android.basictweeter.TwitterClient;

public class UserTimelineFragment extends TweetsListFragment {
	private long user_id = 0;
	@Override
	protected void getTimeline(int mode, long tweet_id,
			JsonHttpResponseHandler jsonHandler) {
		client.getUserTimeline(user_id, mode, tweet_id, jsonHandler);

	}
	
	public void setUserId(long uid){
		user_id = uid;
		clearAllTweets();
		populateTimeline(TwitterClient.GET_NEW_TWEETS);
	}

	@Override
	protected Boolean shouldSaveTweets() {
		return false;
	}

}
