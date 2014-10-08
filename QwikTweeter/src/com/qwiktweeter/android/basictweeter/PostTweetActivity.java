package com.qwiktweeter.android.basictweeter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qwiktweeter.android.basictweeter.models.Tweet;
import com.qwiktweeter.android.basictweeter.models.User;

public class PostTweetActivity extends Activity {
	public static final int TOTAL_TWEET_LENGTH = 140;
	private int tweetCharsRemain;
	private EditText etCompose;
	private TextView tvCharCount;
	private Button btnPost;
	private User currentUser;
	private ImageView ivPostProfileImage;
	private TextView ivPostUserName, ivPostScreenName;
	private String reply_user;
	private long reply_tweet_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_tweet);
		tweetCharsRemain = TOTAL_TWEET_LENGTH;
		etCompose = (EditText) findViewById(R.id.etComposeTweet);
		tvCharCount = (TextView) findViewById(R.id.tvCharCount);
		btnPost = (Button) findViewById(R.id.btnPost);
		currentUser = QwikTweeterApplication.getCurrentUser();
		tvCharCount.setText(currentUser.getScreenName());
		ivPostProfileImage = (ImageView) findViewById(R.id.ivPostProfileImage);
		ivPostScreenName = (TextView) findViewById(R.id.tvPostScreenName);
		ivPostScreenName.setText("@" + currentUser.getScreenName());
		ivPostUserName = (TextView) findViewById(R.id.tvPostUserName);
		ivPostUserName.setText(currentUser.getName());
		ImageLoader.getInstance().displayImage(
				currentUser.getProfileImageUrl(), ivPostProfileImage);
		updateCharCount();
		etCompose.setAutoLinkMask(Linkify.ALL);
		etCompose.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				TOTAL_TWEET_LENGTH) });
		;
		etCompose.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				tweetCharsRemain = TOTAL_TWEET_LENGTH - s.length();
				updateCharCount();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		Intent data = getIntent();
		reply_tweet_id = 0;
		reply_user = "";
		if (data.getExtras().containsKey("reply_user")) {
			reply_user = data.getExtras().getString("reply_user", "");
		}
		if (data.getExtras().containsKey("reply_id")) {
			reply_tweet_id = data.getExtras().getLong("reply_id", 0);
		}
		if (reply_tweet_id != 0 && reply_user.length() > 0) {
			etCompose.setText("@" + reply_user + ":");

		}
	}

	private void updateCharCount() {
		tvCharCount.setText(Integer.toString(tweetCharsRemain)
				+ " characters left.");

	}

	public void onPostAction(View v) {
		// Toast.makeText(this, "Post", Toast.LENGTH_SHORT).show();
		String msg = etCompose.getText().toString();
		if (msg.length() == 0) {
			Toast.makeText(this, "Please enter some message.",
					Toast.LENGTH_SHORT).show();
		}
		publishTweet(msg);
		btnPost.setText("Posting");
	}

	private void publishTweet(String tweetText) {
		TwitterClient client = QwikTweeterApplication.getRestClient();
		client.postTweet(tweetText, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject obj) {
				Tweet t = Tweet.fromJSON(obj);
				Intent data = new Intent();
				data.putExtra("posted_tweet", t);
				setResult(RESULT_OK, data);
				finish();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(PostTweetActivity.this, "Post",
						Toast.LENGTH_SHORT).show();
				Log.d("debug", s.toString());
				Log.d("debug", e.toString());
			}
		}, reply_tweet_id);

	}
}
