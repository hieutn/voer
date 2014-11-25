package vn.edu.voer.adapter;

import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.object.Material;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IDownloadListener;
import vn.edu.voer.utility.DialogHelper;
import vn.edu.voer.utility.DialogHelper.IDialogListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
			holder.imgDownload = (ImageView) convertView.findViewById(R.id.search_item_btn_download);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Material material;
		try {
			material = listMaterials.get(position);
		} catch (IndexOutOfBoundsException e) {
			return convertView;
		}
		if (material != null) {
			holder.lblTitle.setText(material.getTitle());
			if (!mMaterialDAO.isDownloadedMaterial(material.getMaterialID())) {
				holder.imgDownload.setImageResource(R.drawable.download);
				holder.lblTitle.setTypeface(null, Typeface.NORMAL);
			} else {
				holder.imgDownload.setImageResource(R.drawable.downloaded);
				holder.lblTitle.setTypeface(null, Typeface.BOLD);
			}

			if (material.getMaterialType() == Material.TYPE_MODULE) {
				holder.lblTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_puzzle, 0, 0, 0);
			} else {
				holder.lblTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_book, 0, 0, 0);
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
		}

		return convertView;
	}

	static class ViewHolder {
		TextView lblTitle;
		ImageView imgDownload;
	}

	/**
	 * Download material with material id
	 * 
	 * @param id
	 */
	public void downloadMaterial(final String id) {
		if (!mMaterialDAO.isDownloadedMaterial(id)) {
			ServiceController sc = new ServiceController(mCtx);
			sc.downloadMaterial(id, new IDownloadListener() {
				@Override
				public void onDownloadMaterialDone(boolean isDownloaded, int code) {
					if (code == ServiceController.CODE_NO_INTERNET) {
						DialogHelper.showDialogMessage(mCtx, mCtx.getResources().getString(R.string.msg_no_internet));
					} else if (code == ServiceController.CODE_TOKEN_EXPIRE) {
						downloadMaterial(id);
					} else if (code == ServiceController.CODE_CONNECTION_TIMEOUT) {
						DialogHelper.showConfirmMessage(mCtx,
								mCtx.getString(R.string.msg_connection_timeout), new IDialogListener() {
									@Override
									public void onOKClick() {
										downloadMaterial(id);
									}
								});
					} else {
						notifyDataSetChanged();
					}
				}
			});
		}
	}
}