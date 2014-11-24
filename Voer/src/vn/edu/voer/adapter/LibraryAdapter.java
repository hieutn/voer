/**
 * 
 */
package vn.edu.voer.adapter;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.object.Material;
import vn.edu.voer.utility.StringUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

	/**
	 * @param context
	 * @param resource
	 */
	public LibraryAdapter(Context context, int resource, ArrayList<Material> materials) {
		super(context, resource);
		mCtx = context;
		mLayoutId = resource;
		mMaterials = materials;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mCtx).inflate(mLayoutId, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.frm_library_item_title);
			holder.lblAuthorAndYear = (TextView) convertView.findViewById(R.id.lblAuthorAndYear);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Material material = mMaterials.get(position);
		holder.title.setText(material.getTitle());
		if (StringUtil.isEmpty(material.getDerivedFrom())) {
			holder.lblAuthorAndYear.setText("Author " + material.getAuthor());
		} else {
			holder.lblAuthorAndYear.setText(material.getDerivedFrom() + " - by " + material.getAuthor());
		}

		if (material.getMaterialType() == Material.TYPE_MODULE) {
			holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_puzzle, 0, 0, 0);
		} else {
			holder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_book, 0, 0, 0);
		}

		return convertView;
	}

	/**
	 * ViewHolder
	 */
	private static class ViewHolder {
		TextView title;
		TextView lblAuthorAndYear;
	}
}