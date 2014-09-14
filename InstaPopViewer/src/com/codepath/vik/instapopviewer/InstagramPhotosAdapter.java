package com.codepath.vik.instapopviewer;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, android.R.layout.simple_list_item_1, photos);
	}

	// getView method
	// default will call toString

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// get the data from source
		InstagramPhoto photo = getItem(position);
		
		// check if we are using recycled view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
		}
		// Lookup view subviews
		TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);
		
		// populate subviews with image and text with correct data
		tvCaption.setText(photo.caption);
		imgPhoto.getLayoutParams().height = photo.imageHeight;
		
		//since this may be recycled, better reset the image from previous View.
		imgPhoto.setImageResource(R.drawable.ic_launcher);
		
		
		// ask for the photo to be added to image view based on the photo url
		// send a network request to the url, download the image bytes (optionally resize the image)
		// convert to bitmap
		// insert bitmap to imageview
		Picasso.with(getContext()).load(photo.imageURL).into(imgPhoto);
		// return the view created
		return convertView;
	}

}
