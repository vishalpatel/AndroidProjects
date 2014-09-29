package com.qwiktweeter.android.basictweeter;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.qwiktweeter.android.basictweeter.models.User;

public class PostTweetActivity extends Activity {
	private EditText etCompose;
	private TextView tvCharCount;
	private Button btnPost;
	private User currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_tweet);
		etCompose = (EditText) findViewById(R.id.etComposeTweet);
		tvCharCount = (TextView) findViewById(R.id.tvCharCount);
		btnPost = (Button) findViewById(R.id.btnPost);
		currentUser = QwikTweeterApplication.getCurrentUser();
		tvCharCount.setText(currentUser.getScreenName());
		
	}

	public void onPostAction(View v) {
		Toast.makeText(this, "Post", Toast.LENGTH_SHORT).show();
		publishTweet(etCompose.getText().toString());
		btnPost.setText("Posting");
	}

	private void publishTweet(String tweetText) {
		TwitterClient client = QwikTweeterApplication.getRestClient();
		client.postTweet(tweetText, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject obj) {
				finish();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(PostTweetActivity.this, "Post", Toast.LENGTH_SHORT).show();
				Log.d("debug", s.toString());
				Log.d("debug", e.toString());
			}
		});

	}
}
