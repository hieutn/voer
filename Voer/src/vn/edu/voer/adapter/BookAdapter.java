package vn.edu.voer.adapter;

import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.object.Book;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookAdapter extends BaseAdapter {
	private List<Book> listBooks;
	private LayoutInflater mInflater = null;

	public BookAdapter(Context context, List<Book> listBooks) {
		this.listBooks = listBooks;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return listBooks.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return listBooks.get(position);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_row_book, null);
			holder = new ViewHolder();
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Book course = listBooks.get(position);
		if (course != null) {
			holder.lblBookName = detail(convertView, R.id.lblBookName, course.getName());
		}

		return convertView;
	}

	private TextView detail(View v, int resId, String text) {
		TextView tv = (TextView) v.findViewById(resId);
		tv.setText(text);
		return tv;
	}

	static class ViewHolder {
		TextView lblBookName;
	}
}