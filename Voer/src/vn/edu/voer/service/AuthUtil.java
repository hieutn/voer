/**
 * 
 */
package vn.edu.voer.service;

import vn.edu.voer.BuildConfig;
import vn.edu.voer.utility.Constant;
import vn.edu.voer.utility.MySharedPreferences;
import android.content.Context;
import android.util.Log;

/**
 * @author sidd
 *
 *         Nov 24, 2014
 */
public class AuthUtil {

	private static final String TAG = AuthUtil.class.getSimpleName();
	public static final String TOKEN = "vpr_token";
	public static final String CLIENT = "vpr_client";
	public static final String CLIENT_ID = "mobile01";

	/**
	 * 
	 * @param ctx
	 */
	public static void authExecute(Context ctx) {
		String token = getToken(ctx);
		if (token == null || token == "") {
			new AuthenticationService(ctx).execute(Constant.URL_AUTHEN);
		} else {
			if (BuildConfig.DEBUG)
				Log.i(TAG, "Token saved: " + token);
		}
	}
	
	/**
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getToken(Context ctx) {
		return new MySharedPreferences(ctx).getStringValue(TOKEN);
	}

	public static String getAuthToken(Context ctx) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(TOKEN);
		urlBuilder.append("=");
		urlBuilder.append(AuthUtil.getToken(ctx));
		urlBuilder.append("&");
		urlBuilder.append(CLIENT);
		urlBuilder.append("=");
		urlBuilder.append(CLIENT_ID);
		return urlBuilder.toString();
	}
}
