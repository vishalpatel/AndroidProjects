package com.codepath.vik.imagefinder.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.vik.imagefinder.R;
import com.codepath.vik.imagefinder.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
	private class ImageResultItemViewHolder {
		ImageView imageView;
		TextView title;
	}
	
	public ImageResultsAdapter(Context context,
			ArrayList<ImageResult> images) {
		super(context, R.layout.item_image_result, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = getItem(position);
		ImageResultItemViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
			holder = new ImageResultItemViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.ivImageResultThumbnail);
			holder.title = (TextView) convertView.findViewById(R.id.tvImageResultTitle);
			convertView.setTag(holder);
		} else {
			holder = (ImageResultItemViewHolder) convertView.getTag();
		}
		holder.imageView.setImageResource(0);
		holder.title.setText(Html.fromHtml(imageInfo.title));
		Picasso.with(getContext()).load(imageInfo.tbUrl).into(holder.imageView);
		
		return convertView;
	}
}
