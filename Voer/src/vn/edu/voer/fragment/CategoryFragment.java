package vn.edu.voer.fragment;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.adapter.CategoryAdapter;
import vn.edu.voer.object.Category;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IServiceListener;
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
//				initData();
			}
		}
	}

	private void initUI(View view) {
		grvCategory = (GridView) view.findViewById(R.id.grvCategory);
	}

	private void initControl() {
		
		ServiceController sc = new ServiceController();
		sc.getCategories(new IServiceListener() {
			
			@Override
			public void onLoadMaterialsDone(MaterialList materialList) {}
			
			@Override
			public void onLoadCategoriesDone(ArrayList<Category> categories) {
				listCategories = categories;
				fillData();
			}
			
			@Override
			public void onDownloadMaterialDone(boolean isDownloaded) {}
		});
		
		
	}
	
	private void fillData() {
		adapter = new CategoryAdapter(getActivity(), listCategories);
		grvCategory.setAdapter(adapter);
		grvCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});
	}
}