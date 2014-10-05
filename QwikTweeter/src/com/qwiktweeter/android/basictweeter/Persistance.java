package com.qwiktweeter.android.basictweeter;

import java.util.ArrayList;
import java.util.Collection;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.qwiktweeter.android.basictweeter.models.Tweet;

public class Persistance {

	public static void saveAll(ProgressBar pbar, ArrayList<Tweet> tweetArr) {
		new PersistenceTask(tweetArr, pbar).execute();

	}

	public static class PersistenceTask extends AsyncTask<Void, Void, Long> {
		Collection<Tweet> objs;
		ProgressBar progressBar;

		public PersistenceTask(ArrayList<Tweet> tweetArr, ProgressBar pgbar) {
			objs = tweetArr;
			progressBar = pgbar;
		}

		@Override
		protected Long doInBackground(Void... params) {
			ActiveAndroid.beginTransaction();
			for (Tweet o : objs) {
				o.getUser().save();
				o.save();
			}
			ActiveAndroid.setTransactionSuccessful();
			ActiveAndroid.endTransaction();
			return (long) objs.size();
		}

		@Override
		protected void onPreExecute() {
			if (progressBar != null) {
				progressBar.setVisibility(ProgressBar.VISIBLE);
			}
		}

		@Override
		protected void onPostExecute(Long result) {
			if (progressBar != null) {
				progressBar.setVisibility(ProgressBar.INVISIBLE);
			}
		}

	}
}