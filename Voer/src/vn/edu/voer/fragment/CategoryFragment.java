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
import android.widget.ListView;

public class CategoryFragment extends BaseFragment {
	private ListView lsvCategory;
	private List<Category> listCategories;
	private CategoryAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_view, container, false);
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
		lsvCategory = (ListView) view.findViewById(R.id.listView);
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
		lsvCategory.setAdapter(adapter);
		lsvCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});
	}
}