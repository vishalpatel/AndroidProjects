package com.codepath.vik.instapopviewer;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.R.color;
import android.content.Context;
import android.text.AndroidCharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	private static class InstagramPhotoViewHolder {
		ImageView imgPhoto, imgProfilePicture;
		TextView tvCaption, tvUserName, tvLikeCount, tvComment;
	}

	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, android.R.layout.simple_list_item_1, photos);
	}

	// getView method
	// default will call toString

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		InstagramPhotoViewHolder viewHolder;
		// get the data from source
		InstagramPhoto photo = getItem(position);
		
		// check if we are using recycled view
		if (convertView == null) {
			viewHolder = new InstagramPhotoViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
			viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
			viewHolder.tvLikeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);
			viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
			viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			viewHolder.imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
			viewHolder.imgProfilePicture = (ImageView) convertView.findViewById(R.id.imgUserProfilePhoto);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (InstagramPhotoViewHolder) convertView.getTag();
		}

		
		// populate subviews with image and text with correct data
		viewHolder.tvCaption.setText(photo.caption);
		viewHolder.tvUserName.setText(photo.userName);
		viewHolder.tvUserName.setTextColor(android.graphics.Color.argb(250, 0xF0, 0xAA, 0x52));
		viewHolder.tvLikeCount.setText(photo.getHumanizedLikeCount());
		viewHolder.tvLikeCount.setTextColor(android.graphics.Color.argb(200, 0xBD, 0xBD, 0xBD));
		//tvLikeCount.setTextColor(android.graphics.Color.argb(200, 0x2F, 0x86, 0xAA));
		viewHolder.imgPhoto.getLayoutParams().height = photo.imageHeight;
		if (photo.hasComments()) {
			viewHolder.tvComment.setText(photo.getComments());
			viewHolder.tvComment.getLayoutParams().height = 50;
		}else
		{
			viewHolder.tvComment.getLayoutParams().height = 0;
		}
		//since this may be recycled, better reset the image from previous View.
		viewHolder.imgPhoto.setImageResource(0);
		viewHolder.imgProfilePicture.setImageResource(R.drawable.default_loading);
		
		// ask for the photo to be added to image view based on the photo url
		// send a network request to the url, download the image bytes (optionally resize the image)
		// convert to bitmap
		// insert bitmap to imageview
		Picasso.with(getContext()).load(photo.imageURL).into(viewHolder.imgPhoto);
		Picasso.with(getContext()).load(photo.userProfileImageUrl).into(viewHolder.imgProfilePicture);
		// return the view created
		return convertView;
	}
}
