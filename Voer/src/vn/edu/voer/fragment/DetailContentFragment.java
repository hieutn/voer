package vn.edu.voer.fragment;

import vn.edu.voer.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class DetailContentFragment extends BaseFragment {
	private WebView webViewContent;

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
//		getMainActivity().currentFragment;
		webViewContent.loadUrl("file:///android_asset/TermsAndConditions.html");
	}
}
