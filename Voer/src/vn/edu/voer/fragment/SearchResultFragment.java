package vn.edu.voer.fragment;

import java.util.ArrayList;

import vn.edu.voer.R;
import vn.edu.voer.activity.MainActivity;
import vn.edu.voer.adapter.SearchResultAdapter;
import vn.edu.voer.object.Material;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IMaterialListener;
import vn.edu.voer.utility.Constant;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchResultFragment extends BaseFragment {
	private ListView mListView;
	private ArrayList<Material> mListMaterials;
	private MaterialList mMaterialList;
	private SearchResultAdapter mAdapter;
	private boolean isLoading = true;

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
			if (getMainActivity().currentResultType == MainActivity.RESULT_TYPE_MATERIAL) {
				fillData();
			} else {
				searchMaterial();
			}

		}
	}

	private void initUI(View view) {
		mListView = (ListView) view.findViewById(R.id.listView);
		mPrbLoading = (View) view.findViewById(R.id.frm_category_loading);
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

		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount >= totalItemCount) {
					if (!isLoading) {
						isLoading = true;
						loadMore();
					}
				}
			}
		});
	}

	private void fillData() {
		mPrbLoading.setVisibility(View.VISIBLE);
		ServiceController sc = new ServiceController(getMainActivity());
		sc.getMaterials(Constant.URL_MATERIAL, getMainActivity().currentCategory.getId(), new IMaterialListener() {

			@Override
			public void onLoadMaterialsDone(MaterialList materialList) {
				try {
					isLoading = false;
					mMaterialList = materialList;
					mListMaterials.clear();
					mListMaterials.addAll(mMaterialList.getMaterials());
					mAdapter.notifyDataSetChanged();
				} catch (NullPointerException e) {
				}
				mPrbLoading.setVisibility(View.GONE);
			}
		});
	}

	private void searchMaterial() {
		mPrbLoading.setVisibility(View.VISIBLE);
		ServiceController sc = new ServiceController(getMainActivity());
		sc.searchMaterials(getMainActivity().currentSearchKeyword, new IMaterialListener() {

			@Override
			public void onLoadMaterialsDone(MaterialList materialList) {
				try {
					isLoading = false;
					mMaterialList = materialList;
					mListMaterials.clear();
					mListMaterials.addAll(mMaterialList.getMaterials());
					mAdapter.notifyDataSetChanged();
				} catch (NullPointerException e) {
				}
				mPrbLoading.setVisibility(View.GONE);
			}
		});
	}

	private void loadMore() {
		try {
			mPrbLoading.setVisibility(View.VISIBLE);
			ServiceController sc = new ServiceController(getMainActivity());
			sc.getMaterials(mMaterialList.getNextLink(), new IMaterialListener() {

				@Override
				public void onLoadMaterialsDone(MaterialList materialList) {
					try {
						isLoading = false;
						mMaterialList = materialList;
						mListMaterials.addAll(mMaterialList.getMaterials());
						mAdapter.notifyDataSetChanged();
					} catch (NullPointerException e) {
						mPrbLoading.setVisibility(View.GONE);
					}
					mPrbLoading.setVisibility(View.GONE);
				}
			});
		} catch (NullPointerException e) {
			mPrbLoading.setVisibility(View.GONE);
		}
	}
}