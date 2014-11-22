package vn.edu.voer.adapter;

import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.object.Material;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchResultAdapter extends BaseAdapter {
	private List<Material> listMaterials;
	private LayoutInflater mInflater = null;

	public SearchResultAdapter(Context context, List<Material> listMaterials) {
		this.listMaterials = listMaterials;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_item_search_result, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Material material = listMaterials.get(position);
		if (material != null) {
			holder.lblTitle = detail(convertView, R.id.lblTitle, material.getTitle());
			holder.lblAuthor = detail(convertView, R.id.lblAuthor, material.getAuthor());
		}

		return convertView;
	}

	private TextView detail(View v, int resId, String text) {
		TextView tv = (TextView) v.findViewById(resId);
		tv.setText(text);
		return tv;
	}

	static class ViewHolder {
		TextView lblTitle;
		TextView lblAuthor;
	}
}