package com.qwiktweeter.android.basictweeter;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.qwiktweeter.android.basictweeter.models.User;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		// TBD look for local on disk cache too
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(LoginActivity.this);
		String userjson = pref.getString("userjson", "");
		if (userjson.length() > 0) {
			try {
				User u = User.fromJSON(new JSONObject(userjson));
				QwikTweeterApplication.setCurrentUser(u);
				showTimelineActivity();
				return;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		getClient().getAccountInfo(new JsonHttpResponseHandler() {
			public void onSuccess(JSONObject obj) {
				User u = User.fromJSON(obj);
				SharedPreferences pref = PreferenceManager
						.getDefaultSharedPreferences(LoginActivity.this);
				Editor edit = pref.edit();
				edit.putString("userjson", obj.toString());
				edit.commit();
				QwikTweeterApplication.setCurrentUser(u);
				showTimelineActivity();
				
			};
		});
		// Toast.makeText(this, "Success, connected to twitter",
		// Toast.LENGTH_SHORT).show();
	}

	public void showTimelineActivity() {
		Intent i = new Intent(this, TimelineActivity.class);
		this.startActivity(i);
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		Toast.makeText(this, "Failed to connect to twitter", Toast.LENGTH_SHORT)
				.show();
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}
