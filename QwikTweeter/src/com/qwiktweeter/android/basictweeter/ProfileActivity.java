package com.qwiktweeter.android.basictweeter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qwiktweeter.android.basictweeter.fragments.UserTimelineFragment;
import com.qwiktweeter.android.basictweeter.models.User;

public class ProfileActivity extends FragmentActivity {
	private ImageView ivProfileImage, ivProfileBackgroundImage;
	private TextView tvUserName, tvScreenName, tvTagline;
	private TextView tvTweetCount, tvFollowersCount, tvFollowingCount;
	private UserTimelineFragment userTimelineFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_profile);
		loadProfileInfo((User) getIntent().getSerializableExtra("user"));
		userTimelineFragment = (UserTimelineFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragmentUserTimeline);
		setupViews();
	}

	private void setupViews() {
		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvScreenName = (TextView) findViewById(R.id.tvScreenName);
		tvTagline = (TextView) findViewById(R.id.tvTagline);
		tvTweetCount = (TextView) findViewById(R.id.tvTweetCount);
		tvFollowersCount = (TextView) findViewById(R.id.tvFollowersCount);
		tvFollowingCount = (TextView) findViewById(R.id.tvFollowingCount);
		ivProfileBackgroundImage = (ImageView) findViewById(R.id.ivProfileBackgroundImage);
		if (getSupportLoaderManager().hasRunningLoaders()) {
			setProgressBarIndeterminateVisibility(Boolean.TRUE);
		}

	}

	private void updateUserProfile(User u) {
		userTimelineFragment.setUserId(u.getUid());
		tvUserName.setText(u.getName());
		tvScreenName.setText("@" + u.getScreenName());
		tvTweetCount.setText(Long.toString(u.getStatusesCount()) + "\nTWEETS");
		tvFollowersCount.setText(Long.toString(u.getFollowersCount())
				+ "\nFOLLOWERS");
		tvFollowingCount.setText(Long.toString(u.getFriendsCount())
				+ "\nFOLLOWING");
		tvTagline.setText(u.getDescription());
		ivProfileImage.forceLayout();
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(),
				ivProfileImage);
		if (getSupportLoaderManager().hasRunningLoaders()) {
			setProgressBarIndeterminateVisibility(Boolean.FALSE);
		}
		if (u.getProfileBGImageUrl().length() > 0) {
			ImageLoader.getInstance().displayImage(u.getProfileBGImageUrl(),
					ivProfileBackgroundImage);
		}

	}

	private void loadProfileInfo(User user) {
		QwikTweeterApplication.getRestClient().getUserProfileInfo(
				user.getUid(), new JsonHttpResponseHandler() {
					public void onSuccess(JSONObject obj) {
						User u = User.fromJSON(obj);

						// need to do stuff with u or user
						// getActionBar().setTitle("@" + u.getScreenName());
						updateUserProfile(u);
					};
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
