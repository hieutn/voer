package vn.edu.voer.util;

import android.util.Log;

public class Logger {

	private static final boolean DEBUG_MODE = true;

	public static void d(Object object) {
		if (DEBUG_MODE) {
			Log.e("Hakaru", "test: " + object);
		}
	}

	public static void e(String TAG, String msg) {
		if (DEBUG_MODE) {
			Log.e(TAG, msg);
		}
	}

	public static void d(String TAG, String msg) {
		if (DEBUG_MODE) {
			Log.d(TAG, msg);
		}
	}

	public static void i(String TAG, String msg) {
		if (DEBUG_MODE) {
			Log.i(TAG, msg);
		}
	}

	public static void w(String TAG, String msg) {
		if (DEBUG_MODE) {
			Log.w(TAG, msg);
		}
	}
}
