package com.codepath.vik.imagefinder.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.codepath.vik.imagefinder.R;
import com.codepath.vik.imagefinder.models.ImageResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageDisplay extends Activity {
	private ImageView ivImageResult;
	private ShareActionProvider miShareAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ImageResult result = (ImageResult) getIntent().getSerializableExtra(
				"result");
		if (result == null) {
			Toast.makeText(this, "Failed to find Image URL", Toast.LENGTH_SHORT)
					.show();
			finish();
		}
		getActionBar().hide();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ivImageResult = (ImageView) findViewById(R.id.ivImageFullView);
		ivImageResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggleActionBar();

			}
		});
		Picasso.with(this).load(result.fullURL)
				.into(ivImageResult, new Callback() {

					@Override
					public void onSuccess() {
						setupShareIntent();
					}

					@Override
					public void onError() {
						// TODO Auto-generated method stub

					}
				});

	}

	private void toggleActionBar() {
		if (getActionBar().isShowing()) {
			getActionBar().hide();
		} else {
			getActionBar().show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		MenuItem item = menu.findItem(R.id.action_share);
		miShareAction = (ShareActionProvider) item.getActionProvider();
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

	// Returns the URI path to the Bitmap displayed in specified ImageView
	public Uri getLocalBitmapUri(ImageView imageView) {
		// Extract Bitmap from ImageView drawable
		Drawable drawable = imageView.getDrawable();
		Bitmap bmp = null;
		if (drawable instanceof BitmapDrawable) {
			bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		} else {
			return null;
		}
		// Store image to default external storage directory
		Uri bmpUri = null;
		try {
			File file = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					"share_image_" + System.currentTimeMillis() + ".png");
			file.getParentFile().mkdirs();
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
			bmpUri = Uri.fromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmpUri;
	}

	/*
	public void onShare(MenuItem item) {

		File cachePath = null;
		try {
			cachePath = File.createTempFile("img", ".jpg");
			FileOutputStream ostream = new FileOutputStream(cachePath);
			bitmap.compress(CompressFormat.JPEG, 100, ostream);
			ostream.close();
		} catch (Exception e) {
			Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
		// start the share intent
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("image/jpg");
		Uri fileuri = Uri.fromFile(cachePath);
		share.setData(fileuri);
		share.putExtra(Intent.EXTRA_STREAM, fileuri.toString());
		startActivity(Intent.createChooser(share, "Share via"));

	}
*/
	// Gets the image URI and setup the associated share intent to hook into the
	// provider
	public void setupShareIntent() {
		// Fetch Bitmap Uri locally
		ImageView ivImage = (ImageView) findViewById(R.id.ivImageFullView);
		Uri bmpUri = getLocalBitmapUri(ivImage); // see previous remote images
													// section
		// Create share intent as described above
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
		shareIntent.setType("image/*");
		// Attach share event to the menu item provider
		miShareAction.setShareIntent(shareIntent);
	}
}
