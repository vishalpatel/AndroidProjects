package com.codepath.vik.imagefinder.activities;

import com.codepath.vik.imagefinder.R;
import com.codepath.vik.imagefinder.R.id;
import com.codepath.vik.imagefinder.R.layout;
import com.codepath.vik.imagefinder.R.menu;
import com.codepath.vik.imagefinder.models.ImageResult;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageDisplay extends Activity {
	private ImageView ivImageResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		if (result == null) {
			Toast.makeText(this, "Failed to find Image URL", Toast.LENGTH_SHORT)
					.show();
			finish();
		}
		getActionBar().hide();
		ivImageResult = (ImageView) findViewById(R.id.ivImageFullView);
		Picasso.with(this).load(result.fullURL).into(ivImageResult);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
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
