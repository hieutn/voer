/**
 * 
 */
package vn.edu.voer.adapter;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.object.Material;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author sidd
 *
 *         Nov 22, 2014
 */
public class LibraryAdapter extends ArrayAdapter<Material> {

	private Context mCtx;
	private int mLayoutId;
	private List<Material> mMaterials;
	MaterialDAO md;

	/**
	 * @param context
	 * @param resource
	 */
	public LibraryAdapter(Context context, int resource, ArrayList<Material> materials) {
		super(context, resource);
		mCtx = context;
		mLayoutId = resource;
		mMaterials = materials;
		md = new MaterialDAO(context);
	}

	public void setMaterials(ArrayList<Material> materials) throws NullPointerException {
		mMaterials = materials;
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return mMaterials.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mCtx).inflate(mLayoutId, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.frm_library_item_title);
			holder.btnRemove = (ImageView) convertView.findViewById(R.id.frm_library_item_btn_remove);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Material material = mMaterials.get(position);
		holder.title.setText(material.getTitle());

		if (material.getMaterialType() == Material.TYPE_MODULE) {
			holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_puzzle, 0, 0, 0);
		} else {
			holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_book, 0, 0, 0);
		}

		if (material.isRead()) {
			holder.title.setTypeface(null, Typeface.NORMAL);
		} else {
			holder.title.setTypeface(null, Typeface.BOLD);
		}

		holder.btnRemove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (md.deleteMaterial(material.getMaterialID())) {
					mMaterials.remove(position);
					notifyDataSetChanged();
				}
			}
		});

		return convertView;
	}

	/**
	 * ViewHolder
	 */
	private static class ViewHolder {
		TextView title;
		ImageView btnRemove;
		// TextView lblAuthorAndYear;
	}
}