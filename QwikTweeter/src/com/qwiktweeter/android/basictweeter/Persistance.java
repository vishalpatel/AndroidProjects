package com.qwiktweeter.android.basictweeter;

import java.util.ArrayList;
import java.util.Collection;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.activeandroid.query.Delete;
import com.qwiktweeter.android.basictweeter.models.Tweet;
import com.qwiktweeter.android.basictweeter.models.User;

public class Persistance {

	public static void saveAll(ProgressBar pbar, ArrayList<Tweet> tweetArr) {
		new PersistenceTask(tweetArr, pbar).execute();

	}

	public static void clearAll() {
		new Delete().from(Tweet.class).execute();
		new Delete().from(User.class).execute();
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
			//ActiveAndroid.beginTransaction();
			try {
				for (Tweet o : objs) {
					o.savedata();
				}
				//ActiveAndroid.setTransactionSuccessful();
			}catch (Exception e) {
				e.printStackTrace();
				clearAll();
			}
			finally {
				//ActiveAndroid.endTransaction();
			}
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