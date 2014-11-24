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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.voer.BuildConfig;
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.database.dao.PersonDAO;
import vn.edu.voer.object.Category;
import vn.edu.voer.object.Material;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.object.Person;
import vn.edu.voer.utility.Constant;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author sidd
 *
 *         Nov 21, 2014
 */
public class ServiceController extends AsyncTask<String, Void, String> {

	private static final String TAG = ServiceController.class.getSimpleName();
	private static final int REQUEST_CATEGORIES = 0;
	private static final int REQUEST_MATERIALS = 1;
	private static final int REQUEST_DOWNLOAD = 2;
	private static final int REQUEST_PERSON = 3;
	
	private ICategoryListener 	mCategoryListener;
	private IMaterialListener 	mMaterialListener;
	private IDownloadListener 	mDownloadListener;
	private IPersonListener 	mPersonListener;
	
	private int mRequest;
	private Context mCtx;
	
	private boolean mSubMaterial = false;
	

	public ServiceController(Context ctx) {
		this.mCtx = ctx;
	}
	
	@Override
	protected String doInBackground(String... params) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(params[0]);
		urlBuilder.append(params[0].indexOf("?") >= 0 ? "&" : "?");
		urlBuilder.append(AuthUtil.getAuthToken(mCtx));
		
		if (BuildConfig.DEBUG) {
			Log.i(TAG, urlBuilder.toString());
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlBuilder.toString());
		try {
			HttpResponse response = httpClient.execute(httpGet);
			return EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			return null;
		}
	}

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
			responseDownloadMaterial(result);
			break;
			
		case REQUEST_PERSON:
			responseDownloadPerson(result);
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
		mRequest = REQUEST_MATERIALS;
		StringBuilder url = new StringBuilder();
		url.append(Constant.URL_SEARCH);
		url.append("?kw=");
		url.append(keyword);
		execute(url.toString());
	}

	/**
	 * Download material with material id
	 * 
	 * @param ctx
	 * @param materialId
	 * @param listener
	 */
	public void downloadMaterial(String materialId, IDownloadListener listener) {
		mDownloadListener = listener;
		mRequest = REQUEST_DOWNLOAD;
		
		StringBuilder url = new StringBuilder();
		url.append(Constant.URL_MATERIAL);
		url.append("/");
		url.append(materialId);
		
		execute(url.toString());
	}
	
	/**
	 * Download sub material with module type
	 * @param materialId
	 * @param listener
	 */
	public void downloadSubMaterial(String materialId, IDownloadListener listener) {
		mSubMaterial = true;
		downloadMaterial(materialId, listener);
	}
	
	public void downloadPersonDetail(String id, IPersonListener listener) {
		mPersonListener = listener;
		mRequest = REQUEST_PERSON;
		StringBuilder url = new StringBuilder();
		url.append(Constant.URL_AUTHOR);
		url.append("/");
		url.append(id);
		execute(url.toString());
	}

	/**
	 * Parse Categories json content to list Category
	 * 
	 * @param result
	 *            Categories json content from service
	 */
	private void responseCategories(String result) {
		ArrayList<Category> cats = new ArrayList<Category>();
		try {
			JSONArray arr = new JSONArray(result);
			JSONObject obj = null;
			Category cat = null;
			for (int i = 0; i < arr.length(); i++) {
				obj = arr.getJSONObject(i);
				cat = new Category(
						obj.getInt("id"), 
						obj.getString("name"), 
						obj.getInt("parent"), 
						obj.getString("description"));
				cats.add(cat);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			cats = null;
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
		int count;
		String next;
		String previous;
		ArrayList<Material> results = new ArrayList<Material>();
		Material m;
		
		try {
			JSONObject obj = new JSONObject(result);
			count = obj.getInt("count");
			next = obj.getString("next");
			previous = obj.getString("previous");
			JSONArray arr = obj.getJSONArray("results");
			for (int i = 0; i < arr.length(); i++) {
				m = parseMaterial(arr.getString(i));
				results.add(m);
			}
			ml = new MaterialList(count, next, previous, results);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mMaterialListener.onLoadMaterialsDone(ml);
	}

	/**
	 * Parse Material detail json content and save to database local
	 * 
	 * @param result
	 *            Material detail json content from service
	 */
	private void responseDownloadMaterial(String result) {
		Material m = parseMaterial(result);
		boolean isDownloaded = false;
		MaterialDAO md = new MaterialDAO(mCtx);
		if (!mSubMaterial) {
			isDownloaded = md.insertMaterial(m);
		} else {
			isDownloaded = md.insertSubMaterial(m);
		}
		mDownloadListener.onDownloadMaterialDone(isDownloaded);
	}
	
	/**
	 * 
	 * @param result
	 */
	private void responseDownloadPerson(String result) {
		Person person = null;
		try {
			JSONObject obj = new JSONObject(result);
			person = new Person(
					obj.getInt("id"), 
					obj.getString("first_name"),
					obj.getString("last_name"),
					obj.getString("user_id"), 
					obj.getString("title"), 
					obj.getInt("client_id"),
					obj.getString("fullname"), 
					obj.getString("email"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		PersonDAO pd = new PersonDAO(mCtx);
		pd.insertPerson(person);
		
		mPersonListener.onLoadPersonDone(person);
	}
	
	/**
	 * Parse material object from json content
	 * @param jsonContent
	 * @return
	 */
	private Material parseMaterial(String jsonContent) {
		try {
			JSONObject obj = new JSONObject(jsonContent);
			String text;
			try {
				text = obj.getString("text");
			} catch (JSONException e){
				text = "";
			}
			Material m = new Material(
					obj.getString("description"), 
					"", 
					obj.getString("title"), 
					text, 
					"", 
					obj.getInt("material_type"), 
					obj.getString("modified"), 
					obj.getString("material_id"), 
					obj.getInt("version"),
					obj.getString("editor"),
					"", 
					"", 
					"", 
					obj.getString("author"), 
					obj.getString("categories"));
			return m;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
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
