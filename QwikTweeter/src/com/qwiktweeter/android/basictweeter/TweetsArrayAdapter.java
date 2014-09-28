package com.qwiktweeter.android.basictweeter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qwiktweeter.android.basictweeter.models.Tweet;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
	private class ViewHolder {
		TextView tvScreenName, tvTweetBody;
		ImageView ivProfileImage;
	}
	private ImageLoader imageLoader;
	
	public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		imageLoader = ImageLoader.getInstance();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet t = getItem(position);
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
			vh = new ViewHolder();
			vh.tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
			vh.tvTweetBody = (TextView) convertView.findViewById(R.id.tvBody);
			vh.tvTweetBody.setMovementMethod(LinkMovementMethod.getInstance());
			vh.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
			convertView.setTag(vh);
		}else {
			vh = (ViewHolder) convertView.getTag();
		}
		
		vh.tvScreenName.setText(t.getUser().getScreenName());
		vh.tvTweetBody.setText(t.getBody());
		Linkify.addLinks(vh.tvTweetBody, Linkify.WEB_URLS);
		vh.ivProfileImage.setImageResource(android.R.color.transparent);
		imageLoader.displayImage(t.getUser().getProfileImageUrl(), vh.ivProfileImage);
		return convertView;
	}

}
