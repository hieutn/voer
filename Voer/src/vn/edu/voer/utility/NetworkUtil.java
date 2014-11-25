package vn.edu.voer.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author sidd
 *
 *         Nov 25, 2014
 */
public class NetworkUtil {

	/**
	 * Check device is connected to internet
	 * 
	 * @param ctx
	 * @return
	 */
	public static boolean isConnected(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}
