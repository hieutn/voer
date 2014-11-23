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
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.object.Category;
import vn.edu.voer.object.Material;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.object.Person;
import vn.edu.voer.utility.Constant;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * @author sidd
 *
 *         Nov 21, 2014
 */
public class ServiceController extends AsyncTask<String, Void, String> {

	private static final String TAG = ServiceController.class.getSimpleName();
	private static final int REQUEST_CATEGORIES = 0;
	private static final int REQUEST_MATERIALS = 1;
	private static final int REQUEST_SEARCH = 2;
	private static final int REQUEST_DOWNLOAD = 3;
	private static final int REQUEST_AUTHOR = 4;

	private ICategoryListener mCategoryListener;
	private IMaterialListener mMaterialListener;
	private IDownloadListener mDownloadListener;
	private IPersonListener mPersonListener;

	private int mRequest;
	private Context mCtx;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(String... params) {
		if (BuildConfig.DEBUG) {
			Log.i(TAG, params[0]);
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

	/*
	 * (non-Javadoc)
	 * 
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

		case REQUEST_DOWNLOAD:
			responseMaterialDetail(result);
			break;

		case REQUEST_AUTHOR:
			responseAuthor(result);
			break;

		default:
			break;
		}
	}

	/**
	 * Get categories
	 * 
	 * @param listener
	 *            The listener event to handle when load data done
	 */
	public void getCategories(ICategoryListener listener) {
		mCategoryListener = listener;
		mRequest = REQUEST_CATEGORIES;
		execute(Constant.URL_CATEGORY);
	}

	/**
	 * Get materials. If you want get materials by category, please append
	 * ?categories=<id> at end of url Example: Get all materials using url
	 * {@link #URLmaterials} Get materials by category using url
	 * {@link materials?categories=id}
	 * 
	 * @param url
	 *            URL request materials. Default (page 1) is
	 *            {@link #URL_MATERIAL} defined in Constant class. To request
	 *            next page, using getNextLink method in instance of
	 *            MaterialList class
	 * @param listener
	 *            The listener event to handle when load data done
	 */
	public void getMaterials(String url, IMaterialListener listener) {
		mMaterialListener = listener;
		mRequest = REQUEST_MATERIALS;
		execute(url);
	}

	/**
	 * Get materials with categories. Using for request first page, if request
	 * next/previous page using above method with
	 * getNextLink()/getPreviousLink() in instance of MaterialList class
	 * 
	 * @param url
	 * @param catId
	 * @param listener
	 */
	public void getMaterials(String url, int catId, IMaterialListener listener) {
		mMaterialListener = listener;
		String materialsUrl = url + "?categories=" + catId;
		getMaterials(materialsUrl, listener);
	}

	/**
	 * Search materials with keyword
	 * 
	 * @param keyword
	 *            The keyword to search Material
	 */
	public void searchMaterials(String keyword, IMaterialListener listener) {
		mMaterialListener = listener;
		mRequest = REQUEST_SEARCH;

	}

	/**
	 * Download material with material id
	 * 
	 * @param ctx
	 * @param materialId
	 * @param listener
	 */
	public void downloadMaterial(Context ctx, String materialId, IDownloadListener listener) {
		mCtx = ctx;
		mDownloadListener = listener;
		mRequest = REQUEST_DOWNLOAD;
		String materialsUrl = Constant.URL_MATERIAL + "/" + materialId;
		execute(materialsUrl);
	}

	/**
	 * Get all author from service
	 */
	public void getAuthors(String id, IPersonListener listener) {
		mRequest = REQUEST_AUTHOR;
		mPersonListener = listener;
		StringBuilder authorUrl = new StringBuilder();
		authorUrl.append(Constant.URL_AUTHOR);
		authorUrl.append("/");
		authorUrl.append(id);
		execute(authorUrl.toString());
	}

	/**
	 * Parse Categories json content to list Category
	 * 
	 * @param result
	 *            Categories json content from service
	 */
	private void responseCategories(String result) {
		ArrayList<Category> cats = null;
		try {
			cats = new Gson().fromJson(result, new TypeToken<ArrayList<Category>>() {
			}.getType());
			if (BuildConfig.DEBUG) {
				for (Category cat : cats) {
					Log.i(TAG, "CatID: " + cat.getId() + ", name: " + cat.getName());
				}
			}
		} catch (JsonSyntaxException e) {
			cats = null;
			if (BuildConfig.DEBUG)
				Log.e(TAG, e.toString());
		} catch (NullPointerException e) {
			if (BuildConfig.DEBUG)
				Log.e(TAG, e.toString());
		}

		mCategoryListener.onLoadCategoryDone(cats);
	}

	/**
	 * Parse Materials json content to list Materials list
	 * 
	 * @param result
	 *            Categories json content from service
	 */
	private void responseMaterials(String result) {
		MaterialList ml = null;
		try {
			ml = new Gson().fromJson(result, MaterialList.class);
			if (BuildConfig.DEBUG) {
				for (Material m : ml.getMaterials()) {
					Log.i(TAG, "MaterialsID: " + m.getMaterialID() + ", Title:" + m.getTitle());
				}
			}
		} catch (JsonSyntaxException e) {
			ml = null;
			if (BuildConfig.DEBUG)
				Log.e(TAG, e.toString());
		} catch (NullPointerException e) {
			if (BuildConfig.DEBUG)
				Log.e(TAG, e.toString());
		}

		mMaterialListener.onLoadMaterialsDone(ml);
	}

	/**
	 * Parse Material detail json content and save to database local
	 * 
	 * @param result
	 *            Material detail json content from service
	 */
	private void responseMaterialDetail(String result) {
		Material m = null;
		boolean isDownloaded = false;
		try {
			m = new Gson().fromJson(result, Material.class);
			if (BuildConfig.DEBUG) {
				Log.i(TAG, m.getTitle());
			}
			// Save material to local database
			MaterialDAO md = new MaterialDAO(mCtx);
			isDownloaded = md.insertMaterial(m);
		} catch (JsonSyntaxException e) {
			if (BuildConfig.DEBUG)
				Log.e(TAG, e.toString());
		} catch (NullPointerException e) {
			if (BuildConfig.DEBUG)
				Log.e(TAG, e.toString());
		}

		mDownloadListener.onDownloadMaterialDone(isDownloaded);
	}

	/**
	 * Parse Author json content and save to database local
	 * 
	 * @param result
	 */
	private void responseAuthor(String result) {
		Person person = null;
		try {
			person = new Gson().fromJson(result, Person.class);
			if (BuildConfig.DEBUG)
				Log.i(TAG, "Author name: " + person.getFullname());
		} catch (JsonSyntaxException e) {
			person = null;
			if (BuildConfig.DEBUG)
				Log.e(TAG, e.toString());
		} catch (NullPointerException e) {
			if (BuildConfig.DEBUG)
				Log.e(TAG, e.toString());
		}
		mPersonListener.onLoadPersonDone(person);
	}

	//
	// ===== Interface listener service request ====
	//
	public interface ICategoryListener {
		public void onLoadCategoryDone(ArrayList<Category> categories);
	}

	public interface IMaterialListener {
		public void onLoadMaterialsDone(MaterialList materialList);
	}

	public interface IDownloadListener {
		public void onDownloadMaterialDone(boolean isDownloaded);
	}

	public interface IPersonListener {
		public void onLoadPersonDone(Person person);
	}
	
	//
	// ====
	//

}
