package com.qwiktweeter.android.basictweeter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.qwiktweeter.android.basictweeter.fragments.HomeTimelineFragment;
import com.qwiktweeter.android.basictweeter.fragments.MentionsTimelineFragement;
import com.qwiktweeter.android.basictweeter.listeners.FragmentTabListener;

public class TimelineActivity extends FragmentActivity {
	private static int POST_REQ = 51;

	// private HomeTimelineFragment fragmentTweetList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		setupTabs();
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.ic_action_home)
				.setTag("HomeTimelineFragment")
				.setTabListener(
						new FragmentTabListener<HomeTimelineFragment>(
								R.id.flContainer, this, "home",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.ic_action_mentions)
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimelineFragement>(
								R.id.flContainer, this,
								"mentions",
								MentionsTimelineFragement.class));
		actionBar.addTab(tab2);
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
				//fragmentTweetList.populateTimeline(TwitterClient.GET_NEW_TWEETS);
				//fragmentTweetList.setListViewToTop();
				// lvTweets.setSelectionAfterHeaderView();
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
