package com.qwiktweeter.android.basictweeter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.qwiktweeter.android.basictweeter.fragments.HomeTimelineFragment;

public class TimelineActivity extends Activity {
	private static int POST_REQ = 51;
	
	private HomeTimelineFragment fragmentTweetList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		fragmentTweetList = (HomeTimelineFragment) getFragmentManager().findFragmentById(R.layout.fragment_tweets_list);
		
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
				 * Tweet newTweet = (Tweet)
				 * data.getSerializableExtra("posted_tweet"); ArrayList<Tweet>
				 * oldtweets = new ArrayList<Tweet>(tweets); aTweets.clear();
				 * aTweets.add(newTweet); aTweets.addAll(oldtweets);
				 * lvTweets.scrollTo(0, 0);
				 */
				fragmentTweetList.populateTimeline(TwitterClient.GET_NEW_TWEETS);
				fragmentTweetList.setListViewToTop();
				//lvTweets.setSelectionAfterHeaderView();
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
