package com.qwiktweeter.android.basictweeter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectivity {
	private static InternetConnectivity instance = null;
	private Boolean isConnected;
	private Context ctx;
	public InternetConnectivity(Context context) {
		isConnected = false;
		ctx = context;
	}

	public static InternetConnectivity getInstance(Context ctx) {
		if (instance == null) {
			instance = new InternetConnectivity(ctx);
			instance.verifyConnection();
		}
		return instance;
	}

	public Boolean isConnected() {
		return isConnected;
	}

	public void verifyConnection() {
		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		isConnected = (activeNetworkInfo != null && activeNetworkInfo
				.isConnectedOrConnecting());
	}
}
