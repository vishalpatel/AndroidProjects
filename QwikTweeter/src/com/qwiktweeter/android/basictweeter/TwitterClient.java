package com.qwiktweeter.android.basictweeter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final int GET_NEW_TWEETS = 1;
	public static final int GET_MORE_TWEETS = 2;
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change
																				// this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change
																			// this,
																			// base
																			// API
																			// URL
	public static final String REST_CONSUMER_KEY = "AKaZCXKbFSghNjTZ4FopRp2Xw"; // Change
																				// this
	public static final String REST_CONSUMER_SECRET = "iZCaBvekB6koybv3UjMPlUc8zPrC5WKWCSTReGS9rDOPE496G2"; // Change
																											// this
	public static final String REST_CALLBACK_URL = "oauth://qwiktweeter"; // Change
																			// this
																			// (here
																			// and
																			// in
																			// manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(int mode, long tweet_id,
			AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		if (mode == GET_NEW_TWEETS) {
			params.put("since_id", Long.toString(tweet_id));
		} else {
			params.put("max_id", Long.toString(tweet_id));
		}

		client.get(apiUrl, params, handler);
	}

	public void postTweet(String tweetText, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams param = new RequestParams();
		param.put("status", tweetText);
		client.post(apiUrl, param, handler);
	}

	public void getAccountInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, handler);
	}

	public void postFavoriteATweet(long tweet_id,
			AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/create.json");
		RequestParams params = new RequestParams();
		params.put("id", Long.toString(tweet_id));
		client.put(apiUrl, params, handler);
	}

	/*
	 * // CHANGE THIS // DEFINE METHODS for different API endpoints here public
	 * void getInterestingnessList(AsyncHttpResponseHandler handler) { String
	 * apiUrl =
	 * getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList"); //
	 * Can specify query string params directly or through RequestParams.
	 * RequestParams params = new RequestParams(); params.put("format", "json");
	 * client.get(apiUrl, params, handler); }
	 */
	/*
	 * 1. Define the endpoint URL with getApiUrl and pass a relative path to the
	 * endpoint i.e getApiUrl("statuses/home_timeline.json"); 2. Define the
	 * parameters to pass to the request (query or body) i.e RequestParams
	 * params = new RequestParams("foo", "bar"); 3. Define the request method
	 * and make a call to the client i.e client.get(apiUrl, params, handler);
	 * i.e client.post(apiUrl, params, handler);
	 */

	public void getMentionsTimeline(int mode, long tweet_id,
			AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		if (mode == GET_NEW_TWEETS) {
			params.put("since_id", Long.toString(tweet_id));
		} else {
			params.put("max_id", Long.toString(tweet_id));
		}

		client.get(apiUrl, params, handler);

	}
}