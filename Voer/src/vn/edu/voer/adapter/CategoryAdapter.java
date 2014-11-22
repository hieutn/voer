package vn.edu.voer.adapter;

import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.object.Category;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	private List<Category> listCategories;
	private LayoutInflater mInflater = null;

	public CategoryAdapter(Context context, List<Category> listCategories) {
		this.listCategories = listCategories;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return listCategories.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return listCategories.get(position);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_item_category, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Category category = listCategories.get(position);
		if (category != null) {
			holder.lblCategoryName = detail(convertView, R.id.lblCategoryName, category.getName());
		}

		return convertView;
	}

	private TextView detail(View v, int resId, String text) {
		TextView tv = (TextView) v.findViewById(resId);
		tv.setText(text);
		return tv;
	}

	static class ViewHolder {
		TextView lblCategoryName;
	}
}