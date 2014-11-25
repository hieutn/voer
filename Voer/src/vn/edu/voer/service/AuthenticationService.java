/**
 * 
 */
package vn.edu.voer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.voer.BuildConfig;
import vn.edu.voer.utility.EncryptHelper;
import vn.edu.voer.utility.MySharedPreferences;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author sidd
 *
 *         Nov 24, 2014
 */
public class AuthenticationService extends AsyncTask<String, Void, String> {

	private static final String SUGAR = "sugar";
	private static final String SUGAR_VALUE = "hello_im_sugar";
	private static final String SECRET = "UYEKQIBGbs9jTabD2bYg";
	private static final String COMB = "comb";
	private static final String TAG = AuthenticationService.class.getSimpleName();
	private Context mCtx;
	
	public AuthenticationService(Context ctx) {
		this.mCtx = ctx;
	}

	@Override
	protected String doInBackground(String... params) {
		if (BuildConfig.DEBUG) Log.i("SDD", params[0]);
		String comb = SECRET + SUGAR_VALUE;
		String combMD5 = EncryptHelper.md5(comb);
		
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair(SUGAR, SUGAR_VALUE));
		postParams.add(new BasicNameValuePair(COMB, combMD5));
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(params[0]);

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParams));
			HttpResponse response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			Log.i("SDD", "ClientProtocolException: " + e.toString());
		} catch (IOException e) {
			Log.i("SDD", "IOException: " + e.toString());
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		String token = "";
		try {
			JSONObject obj = new JSONObject(result);
			token = obj.getString("token");
		} catch (JSONException e) {
			Log.i(TAG, e.toString());
			token = "";
		} catch (NullPointerException e) {
			Log.i(TAG, e.toString());
			token = "";
		}
		if (BuildConfig.DEBUG) {
			Log.i(TAG, token);
		}
		new MySharedPreferences(mCtx).putStringValue(AuthUtil.TOKEN, token);
	}

}
