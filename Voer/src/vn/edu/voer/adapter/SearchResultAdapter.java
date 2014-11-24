package vn.edu.voer.adapter;

import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.object.Material;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IDownloadListener;
import android.annotation.SuppressLint;
import android.content.Context;
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

	public SearchResultAdapter(Context context, List<Material> listMaterials) {
		mCtx = context;
		this.listMaterials = listMaterials;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMaterialDAO = new MaterialDAO(context);
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
			holder.lblAuthor.setText("Authors");
			if (!mMaterialDAO.isDownloadedMaterial(material.getMaterialID())) {
				holder.imgDownload.setImageResource(R.drawable.download);
			} else {
				holder.imgDownload.setImageResource(R.drawable.downloaded);
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