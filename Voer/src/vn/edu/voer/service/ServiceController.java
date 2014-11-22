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

import vn.edu.voer.BuildConfig;
import vn.edu.voer.object.Category;
import vn.edu.voer.object.Material;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.utility.Constant;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author sidd
 *
 * Nov 21, 2014
 */
public class ServiceController extends AsyncTask<String, Void, String> {

	private static final String TAG = ServiceController.class.getSimpleName();
	private static final int REQUEST_CATEGORIES = 0;
	private static final int REQUEST_MATERIALS = 1;
	
	private IServiceListener mListener;
	private int mRequest;

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
		if (BuildConfig.DEBUG) {
			Log.i(TAG, "URL request service: " + params[0]);
		}
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
		switch (mRequest) {
		case REQUEST_CATEGORIES:
			responseCategories(result);
			break;

		case REQUEST_MATERIALS:
			responseMaterials(result);
			break;
			
		default:
			break;
		}
	}

	/**
	 * Get categories
	 * 
	 * @param listener The listener event to handle when load data done
	 */
	public void getCategories(IServiceListener listener) {
		mListener = listener;
		mRequest = REQUEST_CATEGORIES;
		execute(Constant.URL_CATEGORY);
	}
	
	/**
	 * Get materials. If you want get materials by category, please append ?categories=<id> at end of url
	 * Example: Get all materials using url {@link #URLmaterials}
	 * 			Get materials by category using url {@link materials?categories=id}
	 * 
	 * @param url URL request materials. Default (page 1) is {@link #URL_MATERIAL} defined in Constant class.
	 * 			  To request next page, using getNextLink method in instance of MaterialList class
	 * @param listener The listener event to handle when load data done
	 */
	public void getMaterials(String url, IServiceListener listener) {
		mListener = listener;
		mRequest = REQUEST_MATERIALS;
		execute(url);
	}

	/**
	 * Parse Categories json content to list Category
	 * 
	 * @param result Categories json content from service
	 */
	private void responseCategories(String result) {
		ArrayList<Category> cats = new Gson().fromJson(result, new TypeToken<ArrayList<Category>>() {}.getType());
		if (BuildConfig.DEBUG) {
			for (Category cat: cats) {
				Log.i(TAG, "CatID: " + cat.getId() + ", name: " + cat.getName());
			}
		}
		mListener.onLoadCategoriesDone(cats);
	}
	
	/**
	 * Parse Materials json content to list Materials list
	 * 
	 * @param result Categories json content from service
	 */
	private void responseMaterials(String result) {
		MaterialList ml = new Gson().fromJson(result, MaterialList.class);
		if (BuildConfig.DEBUG) {
			for (Material m: ml.getMaterials()) {
				Log.d(TAG, m.getTitle());
			}
		}
		mListener.onLoadMaterialsDone(ml);
	}

	/**
	 * Interface listener service request
	 */
	public interface IServiceListener {
		public void onLoadCategoriesDone(ArrayList<Category> categories);
		public void onLoadMaterialsDone(MaterialList materialList);
	}
	
}
