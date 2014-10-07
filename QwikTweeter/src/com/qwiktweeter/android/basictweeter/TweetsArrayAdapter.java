package com.qwiktweeter.android.basictweeter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qwiktweeter.android.basictweeter.models.Tweet;
import com.qwiktweeter.android.basictweeter.models.User;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
	private class ViewHolder {
		TextView tvScreenName, tvTweetBody, tvUserName, tvTweetTime,
				tvRetweetCount, tvFavorieCount;
		ImageView ivProfileImage, ivTweetMedia, ivFavorite;
	}

	private ImageLoader imageLoader;
	private OnClickListener profilePicClickListener;
	private Context context;
	
	public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		
		imageLoader = ImageLoader.getInstance();
		this.context = context;
		profilePicClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User u = (User)v.getTag();
				Intent i = new Intent(TweetsArrayAdapter.this.context, ProfileActivity.class);
				i.putExtra("user", u);
				TweetsArrayAdapter.this.context.startActivity(i);
			}
		};
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet t = getItem(position);
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.tweet_item, parent, false);
			vh = new ViewHolder();
			vh.tvScreenName = (TextView) convertView
					.findViewById(R.id.tvScreenName);
			vh.tvTweetBody = (TextView) convertView.findViewById(R.id.tvBody);
			vh.tvTweetBody.setMovementMethod(LinkMovementMethod.getInstance());
			vh.tvUserName = (TextView) convertView
					.findViewById(R.id.tvUserName);

			vh.tvRetweetCount = (TextView) convertView
					.findViewById(R.id.tvRetweets);
			vh.tvFavorieCount = (TextView) convertView
					.findViewById(R.id.tvFavoriteCount);
			vh.ivFavorite = (ImageView) convertView.findViewById(R.id.ivFavorite);
			OnClickListener favClickListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					Tweet t = (Tweet)v.getTag();
					Log.i("info", "posting a favorite for " + t.toString());
					QwikTweeterApplication.getRestClient().postFavoriteATweet(
							t.getUid(),
							new JsonHttpResponseHandler() {
								public void onSuccess(int arg0,
										org.json.JSONObject arg1) {
									Toast.makeText(getContext(), "Favorited",
											Toast.LENGTH_SHORT).show();

								};
								@Override
								public void onFailure(Throwable arg0,
										String arg1) {
									Log.e("error", arg1);
									
								}
							});

				}
			};
			vh.tvFavorieCount.setOnClickListener(favClickListener);
			vh.ivFavorite.setOnClickListener(favClickListener);
			vh.tvTweetTime = (TextView) convertView
					.findViewById(R.id.tvTweetTime);
			vh.ivProfileImage = (ImageView) convertView
					.findViewById(R.id.ivProfileImage);
			vh.ivTweetMedia = (ImageView) convertView
					.findViewById(R.id.ivTweetMedia);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.ivProfileImage.setImageResource(0);
		vh.ivTweetMedia.setImageResource(android.R.color.transparent);
		vh.ivTweetMedia.forceLayout();
		vh.ivTweetMedia.setImageResource(0);

		vh.tvFavorieCount.setTag(t);
		vh.ivFavorite.setTag(t);
		vh.tvScreenName.setText("@" + t.getUser().getScreenName());
		vh.tvTweetBody.setText(t.getBody());
		vh.tvRetweetCount.setText(Long.toString(t.getRetweetCount()));
		vh.tvFavorieCount.setText(Long.toString(t.getFavoriteCount()));
		vh.tvTweetTime.setText(t.getCreateTimeAgo());
		vh.tvUserName.setText(t.getUser().getName());

		Linkify.addLinks(vh.tvTweetBody, Linkify.WEB_URLS);
		vh.ivProfileImage.setImageResource(android.R.color.transparent);
		vh.ivProfileImage.setTag(t.getUser());
		vh.ivProfileImage.setOnClickListener(profilePicClickListener);
		imageLoader.displayImage(t.getUser().getProfileImageUrl(),
				vh.ivProfileImage);
		
		if (t.hasMediaUrl()) {
			vh.ivTweetMedia.setScaleType(t.getMediaPhotoScaleType());
			vh.ivTweetMedia.setAdjustViewBounds(true);
			imageLoader.displayImage(t.getMediaImageUrl(), vh.ivTweetMedia);
		}else {
			
		}
		return convertView;
	}

}
