package vn.edu.voer.fragment;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.activity.MainActivity;
import vn.edu.voer.adapter.CategoryAdapter;
import vn.edu.voer.object.Category;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.ICategoryListener;
import vn.edu.voer.utility.DialogHelper;
import vn.edu.voer.utility.DialogHelper.IDialogListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
				fillData();
			}
		}
	}

	private void initUI(View view) {
		lsvCategory = (ListView) view.findViewById(R.id.listView);
		mPrbLoading = (View) view.findViewById(R.id.frm_category_loading);
	}

	private void initControl() {
		listCategories = new ArrayList<Category>();
		adapter = new CategoryAdapter(getActivity(), listCategories);
		lsvCategory.setAdapter(adapter);
		lsvCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				getMainActivity().currentCategory = listCategories.get(position);
				getMainActivity().currentResultType = MainActivity.RESULT_TYPE_MATERIAL;
				goToFragment(MainActivity.SEARCH_RESULT);
				getMainActivity().setHeaderTitle(getMainActivity().currentCategory.getName());
			}
		});
	}

	private void fillData() {
		mPrbLoading.setVisibility(View.VISIBLE);
		ServiceController sc = new ServiceController(getMainActivity());
		sc.getCategories(new ICategoryListener() {
			@Override
			public void onLoadCategoryDone(ArrayList<Category> categories, int code) {
				if (code == ServiceController.CODE_NO_INTERNET) {
					DialogHelper.showDialogMessage(getMainActivity(),
							getMainActivity().getString(R.string.msg_no_internet));
				} else if (code == ServiceController.CODE_TOKEN_EXPIRE) {
					fillData();
				} else if (code == ServiceController.CODE_CONNECTION_TIMEOUT) {
					DialogHelper.showConfirmMessage(getMainActivity(),
							getMainActivity().getString(R.string.msg_connection_timeout), new IDialogListener() {
								@Override
								public void onOKClick() {
									fillData();
								}
							});
				} else if (categories != null) {
					try {
						listCategories.clear();
						listCategories.addAll(categories);
						adapter.notifyDataSetChanged();
					} catch (NullPointerException e) {
					}
				}
				mPrbLoading.setVisibility(View.GONE);
			}
		});
	}
}
