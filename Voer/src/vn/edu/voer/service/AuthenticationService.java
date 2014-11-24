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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import vn.edu.voer.utility.EncryptHelper;
import vn.edu.voer.utility.NetworkHelper;
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
	
	@Override
	protected String doInBackground(String... params) {
		Log.i("SDD", params[0]);
		HttpClient httpClient = NetworkHelper.getNewHttpClient();
		HttpPost httpPost = new HttpPost(params[0]);

		String comb = SECRET + SUGAR_VALUE;
		Log.i("SDD", comb);
		String combMD5 = EncryptHelper.md5(SECRET + SUGAR_VALUE);
		Log.i("SDD", "MD5: " + combMD5);
		
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair(SUGAR, SUGAR_VALUE));
		postParams.add(new BasicNameValuePair(COMB, combMD5));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParams));
			HttpResponse response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			Log.i("SDD", "Error: " + e.toString());
		} catch (IOException e) {
			Log.i("SDD", "Error: " + e.toString());
		}

		return null;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		Log.i("SDD", "Result: " + result);
	}

}
