package vn.edu.voer.fragment;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.adapter.CategoryAdapter;
import vn.edu.voer.object.Category;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class CategoryFragment extends BaseFragment {
	private GridView grvCategory;
	private List<Category> listCategories;
	private CategoryAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, container, false);
		initUI(view);
		initControl();
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			if (listCategories.size() == 0) {
				initData();
			}
		}
	}

	private void initUI(View view) {
		grvCategory = (GridView) view.findViewById(R.id.grvCategory);
	}

	private void initControl() {
		listCategories = new ArrayList<Category>();
		adapter = new CategoryAdapter(getActivity(), listCategories);
		grvCategory.setAdapter(adapter);
		grvCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});
	}

	private void initData() {
		listCategories.clear();
		listCategories.add(new Category(0, "Bussiness", 0, "description"));
		listCategories.add(new Category(1, "Story", 0, "description"));
		listCategories.add(new Category(2, "Music", 0, "description"));
		listCategories.add(new Category(3, "JAV", 0, "description"));
		listCategories.add(new Category(4, "Model", 0, "description"));
		listCategories.add(new Category(5, "Fashion", 0, "description"));
		adapter.notifyDataSetChanged();
	}
}