/**
 * 
 */
package vn.edu.voer.adapter;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.object.CollectionContent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author sidd
 *
 * Nov 22, 2014
 */
public class MenuAdapter extends ArrayAdapter<CollectionContent> {

	private Context mCtx;
	private int mLayoutId;
	private List<CollectionContent> mCollectionContent;
	
	/**
	 * @param context
	 * @param resource
	 */
	public MenuAdapter(Context context, int resource, ArrayList<CollectionContent> cc) {
		super(context, resource);
		mCtx = context;
		mLayoutId = resource;
		mCollectionContent = cc;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return mCollectionContent.size();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mCtx).inflate(mLayoutId, parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.frm_drawer_item_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		CollectionContent cc = mCollectionContent.get(position);
		holder.title.setText(cc.getTitle());
		
		return convertView;
	}
	
	/**
	 * ViewHolder
	 */
	private static class ViewHolder {
		TextView title;
	}

}
