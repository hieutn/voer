package vn.edu.voer.fragment;

import java.util.ArrayList;

import vn.edu.voer.R;
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.object.CollectionContent;
import vn.edu.voer.object.Material;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IDownloadListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class DetailContentFragment extends BaseFragment {
	
	private WebView webViewContent;
	private Material mMaterial;
	private ArrayList<CollectionContent> mCollectionContents;
	MaterialDAO md = new MaterialDAO(getMainActivity());
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_detail_content, container, false);
		initUI(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			initData();
		}
	}

	private void initUI(View view) {
		webViewContent = (WebView) view.findViewById(R.id.webViewContent);
	}

	private void initData() {
		getMainActivity().test = 10;
		mMaterial = getMainActivity().currentMaterial;
		if (mMaterial.getMaterialType() == Material.TYPE_MODULE) {
			webViewContent.loadData(mMaterial.getText(), "text/html", "UTF-8");
		} else {
			mCollectionContents = mMaterial.getCollectionContent();
			getMainActivity().currentCollectionContent = mCollectionContents;
			final String id = mCollectionContents.get(0).getId();
			
			if (md.isDownloadedMaterial(id)) {
				fillContentWebview(id);
			} else {
				ServiceController sc = new ServiceController();
				sc.downloadMaterial(getMainActivity(), id, new IDownloadListener() {
					
					@Override
					public void onDownloadMaterialDone(boolean isDownloaded) {
						if (isDownloaded) {
							fillContentWebview(id);
						}
					}
				});
			}
		}
	}
	
	private void fillContentWebview(String materialId) {
		mMaterial = md.getMaterialById(materialId);
		webViewContent.loadData(mMaterial.getText(), "text/html", "UTF-8");
	}
}
