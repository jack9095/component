package com.base.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	public static final int NETWORK_TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
	public static final int NETWORK_TYPE_WIFI = ConnectivityManager.TYPE_WIFI;

	public static boolean isNetworkAvaliable(Context ctx) {
		boolean flag = false;
		ConnectivityManager connectivityManager = null;
		try {
			connectivityManager = (ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		} catch (Exception e1) {
			e1.printStackTrace();
			return flag;
		}
		NetworkInfo net = connectivityManager.getActiveNetworkInfo();
		if (net != null) {
			if (net.isAvailable() && net.isConnected()) {
				flag = true;
			}
		}
		return flag;
	}

//	public static boolean isServiceReachable(Context ctx, int hostAddress) {
//		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
//				.getSystemService(Context.CONNECTIVITY_SERVICE);
//		return connectivityManager.requestRouteToHost(connectivityManager
//				.getActiveNetworkInfo().getType(), hostAddress);
//	}

	public static int getNetworkType(Context con) {
		ConnectivityManager cm = (ConnectivityManager) con
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
			return NETWORK_TYPE_MOBILE;
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo != null && netinfo.isAvailable()) {
			if (netinfo.getType() == ConnectivityManager.TYPE_WIFI) {
				return NETWORK_TYPE_WIFI;
			} else {
				return NETWORK_TYPE_MOBILE;
			}
		}
		return NETWORK_TYPE_MOBILE;
	}
}
