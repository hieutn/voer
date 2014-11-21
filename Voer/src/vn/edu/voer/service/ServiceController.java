/**
 * 
 */
package vn.edu.voer.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import vn.edu.voer.object.Category;
import vn.edu.voer.utility.Constant;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author sidd
 *
 * Nov 21, 2014
 */
public class ServiceController extends AsyncTask<String, Void, String> {

	private static final String TAG = ServiceController.class.getSimpleName();
	private IServiceListener mListener;

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(String... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(params[0]);
		
		try {
			HttpResponse response = httpClient.execute(httpGet);
			return EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		ArrayList<Category> cats = new Gson().fromJson(result, new TypeToken<ArrayList<Category>>() {}.getType());
		mListener.onLoadCategoriesDone(cats);
	}

	/**
	 * 
	 */
	public void getCategories(IServiceListener listener) {
		mListener = listener;
		execute(Constant.URL_CATEGORY);
	}
	
	
	/**
	 * 
	 */
	public interface IServiceListener {
		public void onLoadCategoriesDone(ArrayList<Category> categories);
	}
	
}
