package vn.edu.voer.fragment;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.adapter.SearchResultAdapter;
import vn.edu.voer.object.Category;
import vn.edu.voer.object.Material;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IServiceListener;
import vn.edu.voer.utility.Constant;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchResultFragment extends BaseFragment {
	private ListView mListView;
	private ArrayList<Material> mListMaterials;
	private SearchResultAdapter mAdapter;

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
			fillData();
		}
	}

	private void initUI(View view) {
		mListView = (ListView) view.findViewById(R.id.listView);
	}

	private void initControl() {
		mListMaterials = new ArrayList<Material>();
		mAdapter = new SearchResultAdapter(getActivity(), mListMaterials);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			}
		});
	}

	private void fillData() {
		ServiceController sc = new ServiceController();
		sc.getMaterials(Constant.URL_MATERIAL, getMainActivity().currentCategory.getId(), new IServiceListener() {
			@Override
			public void onLoadMaterialsDone(MaterialList materialList) {
				mListMaterials.clear();
				mListMaterials.addAll(materialList.getMaterials());
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onLoadCategoriesDone(List<Category> categories) {
				fillData();
			}

			@Override
			public void onDownloadMaterialDone(boolean isDownloaded) {
			}
		});
	}
}