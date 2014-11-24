package vn.edu.voer.adapter;

import java.util.List;

import vn.edu.voer.BuildConfig;
import vn.edu.voer.R;
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.database.dao.PersonDAO;
import vn.edu.voer.object.Material;
import vn.edu.voer.object.Person;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IDownloadListener;
import vn.edu.voer.service.ServiceController.IPersonListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class SearchResultAdapter extends BaseAdapter {

	protected static final String TAG = SearchResultAdapter.class.getSimpleName();

	private List<Material> listMaterials;
	private LayoutInflater mInflater = null;
	private Context mCtx;
	private MaterialDAO mMaterialDAO;
	private PersonDAO mPersonDAO;

	public SearchResultAdapter(Context context, List<Material> listMaterials) {
		mCtx = context;
		this.listMaterials = listMaterials;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMaterialDAO = new MaterialDAO(context);
		mPersonDAO = new PersonDAO(context);
	}

	@Override
	public int getCount() {
		return listMaterials.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return listMaterials.get(position);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_item_search_result, null);
			holder = new ViewHolder();
			holder.lblTitle = (TextView) convertView.findViewById(R.id.lblTitle);
			holder.lblAuthor = (TextView) convertView.findViewById(R.id.lblAuthor);
			holder.imgDownload = (ImageButton) convertView.findViewById(R.id.search_item_btn_download);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Material material = listMaterials.get(position);
		if (material != null) {
			holder.lblTitle.setText(material.getTitle());
			if (!mMaterialDAO.isDownloadedMaterial(material.getMaterialID())) {
				holder.imgDownload.setImageResource(R.drawable.download);
			} else {
				holder.imgDownload.setImageResource(R.drawable.downloaded);
			}

			if (mPersonDAO.isDownloadedPerson(material.getAuthor())) {
				holder.lblAuthor.setText(mPersonDAO.getPersonById(material.getAuthor()).getFullname());
			} else {
				// Get author from service
				new ServiceController(mCtx).getAuthors(material.getAuthor(), new IPersonListener() {
					@Override
					public void onLoadPersonDone(Person person) {
						if (person != null) {
							if (!mPersonDAO.isDownloadedPerson(String.valueOf(person.getId())) && mPersonDAO.insertPerson(person)) {
								if (BuildConfig.DEBUG) Log.i(TAG, "Insert " + person.getFullname() + " to db local success");
							}
							holder.lblAuthor.setText(person.getFullname());
						}
					}
				});
			}
		}

		if (mMaterialDAO.isDownloadedMaterial(material.getMaterialID())) {
			holder.imgDownload.setClickable(false);
		} else {
			holder.imgDownload.setClickable(true);
			holder.imgDownload.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					downloadMaterial(material.getMaterialID());
				}
			});
		}

		return convertView;
	}

	static class ViewHolder {
		TextView lblTitle;
		TextView lblAuthor;
		ImageButton imgDownload;
	}

	/**
	 * Download material with material id
	 * 
	 * @param id
	 */
	private void downloadMaterial(String id) {
		if (!mMaterialDAO.isDownloadedMaterial(id)) {
			ServiceController sc = new ServiceController(mCtx);
			sc.downloadMaterial(id, new IDownloadListener() {
				@Override
				public void onDownloadMaterialDone(boolean isDownloaded) {
					notifyDataSetChanged();
				}
			});
		}
	}
}