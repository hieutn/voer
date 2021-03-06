package vn.edu.voer.fragment;

import java.util.ArrayList;

import vn.edu.voer.R;
import vn.edu.voer.activity.MainActivity;
import vn.edu.voer.adapter.SearchResultAdapter;
import vn.edu.voer.adapter.SearchResultAdapter.IDownloadMaterialListener;
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.object.Material;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IMaterialListener;
import vn.edu.voer.utility.Constant;
import vn.edu.voer.utility.DialogHelper;
import vn.edu.voer.utility.DialogHelper.IDialogListener;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultFragment extends BaseFragment {
	private ListView mListView;
	private ArrayList<Material> mListMaterials;
	private MaterialList mMaterialList;
	private SearchResultAdapter mAdapter;
	private boolean isLoading = false;
	private boolean isDownloading = false;
	private MaterialDAO mMaterialDAO;
	private TextView mTvProgress;

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
		mTvProgress = (TextView) mPrbLoading.findViewById(R.id.tv_loading);
		mMaterialDAO = new MaterialDAO(getActivity());
	}

	private void initControl() {
		mListMaterials = new ArrayList<Material>();
		mAdapter = new SearchResultAdapter(getActivity(), mListMaterials);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (!isDownloading) {
					Material material = mListMaterials.get(position);
					if (material != null) {
						if (mMaterialDAO.isDownloadedMaterial(material.getMaterialID())) {
							getMainActivity().isReplaceImageLink = false;
							getMainActivity().displayDetailContent(
									mMaterialDAO.getMaterialById(material.getMaterialID()));
						} else {
							isDownloading = true;
							mTvProgress.setText(getMainActivity().getString(R.string.download));
							mPrbLoading.setVisibility(View.VISIBLE);
							mAdapter.downloadMaterial(material.getMaterialID(), new IDownloadMaterialListener() {
								@Override
								public void downloadMaterialDone() {
									mPrbLoading.setVisibility(View.GONE);
									mTvProgress.setText(getMainActivity().getString(R.string.loading));
									isDownloading = false;
									getMainActivity().refreshNumberNotRead();
								}
							});
						}
					}
				}

				getMainActivity().refreshNumberNotRead();
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
			public void onLoadMaterialsDone(MaterialList materialList, int code) {
				if (code == ServiceController.CODE_NO_INTERNET) {
					DialogHelper.showDialogMessage(getMainActivity(),
							getMainActivity().getResources().getString(R.string.msg_no_internet));
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
				} else if (materialList != null) {
					try {
						isLoading = false;
						mMaterialList = materialList;
						mListMaterials.clear();
						mListMaterials.addAll(mMaterialList.getMaterials());
						mAdapter.notifyDataSetChanged();
					} catch (NullPointerException e) {
					}
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
			public void onLoadMaterialsDone(MaterialList materialList, int code) {
				if (code == ServiceController.CODE_NO_INTERNET) {
					DialogHelper.showDialogMessage(getMainActivity(),
							getMainActivity().getResources().getString(R.string.msg_no_internet));
				} else if (code == ServiceController.CODE_TOKEN_EXPIRE) {
					searchMaterial();
				} else if (code == ServiceController.CODE_CONNECTION_TIMEOUT) {
					DialogHelper.showConfirmMessage(getMainActivity(),
							getMainActivity().getString(R.string.msg_connection_timeout), new IDialogListener() {
								@Override
								public void onOKClick() {
									searchMaterial();
								}
							});
				} else if (materialList != null) {
					try {
						isLoading = false;
						mMaterialList = materialList;
						mListMaterials.clear();
						mListMaterials.addAll(mMaterialList.getMaterials());
						mAdapter.notifyDataSetChanged();
					} catch (NullPointerException e) {
					}
				}
				mPrbLoading.setVisibility(View.GONE);
				getMainActivity().isSearching = false;
			}
		});
	}

	@SuppressLint("InflateParams")
	private void loadMore() {
		final View loading = LayoutInflater.from(getMainActivity()).inflate(R.layout.progressbar, null);
		mListView.addFooterView(loading);
		try {
			ServiceController sc = new ServiceController(getMainActivity());
			sc.getMaterials(mMaterialList.getNextLink(), new IMaterialListener() {

				@Override
				public void onLoadMaterialsDone(MaterialList materialList, int code) {
					if (code == ServiceController.CODE_NO_INTERNET) {
						DialogHelper.showDialogMessage(getMainActivity(),
								getMainActivity().getResources().getString(R.string.msg_no_internet));
					}
					if (code == ServiceController.CODE_TOKEN_EXPIRE) {
						loadMore();
					} else if (code == ServiceController.CODE_CONNECTION_TIMEOUT) {
						DialogHelper.showConfirmMessage(getMainActivity(),
								getMainActivity().getString(R.string.msg_connection_timeout), new IDialogListener() {
									@Override
									public void onOKClick() {
										loadMore();
									}
								});
					} else if (materialList != null) {
						try {
							mMaterialList = materialList;
							mListMaterials.addAll(mMaterialList.getMaterials());
							mAdapter.notifyDataSetChanged();
							isLoading = false;
						} catch (NullPointerException e) {
						}
					}
					try {
						mListView.removeFooterView(loading);
					} catch (Exception ex) {
					}
				}
			});
		} catch (NullPointerException e) {
			try {
				mListView.removeFooterView(loading);
			} catch (Exception ex) {
			}
		}
	}
}