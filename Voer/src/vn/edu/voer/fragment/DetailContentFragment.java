package vn.edu.voer.fragment;

import java.util.ArrayList;

import vn.edu.voer.R;
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.database.dao.PersonDAO;
import vn.edu.voer.object.CollectionContent;
import vn.edu.voer.object.Material;
import vn.edu.voer.object.Person;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IDownloadListener;
import vn.edu.voer.service.ServiceController.IPersonListener;
import vn.edu.voer.utility.DateTimeHelper;
import vn.edu.voer.utility.DialogHelper;
import vn.edu.voer.utility.DialogHelper.IDialogListener;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class DetailContentFragment extends BaseFragment {
	private WebView mWebViewContent;
	private View progressBar;
	private TextView lblAuthor, lblPublishDate;

	private Material mMaterial;
	private ArrayList<CollectionContent> mCollectionContents;
	MaterialDAO md = new MaterialDAO(getMainActivity());
	PersonDAO pd = new PersonDAO(getMainActivity());

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_detail_content, container, false);
		initUI(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (hidden) {
			clearData();
		}
	}

	private void initUI(View view) {
		mWebViewContent = (WebView) view.findViewById(R.id.webViewContent);
		progressBar = view.findViewById(R.id.progressBar);
		lblAuthor = (TextView) view.findViewById(R.id.lblAuthor);
		lblPublishDate = (TextView) view.findViewById(R.id.lblPublishDate);
		mWebViewContent.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progressBar.setVisibility(View.GONE);
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void clearData() {
		if (Build.VERSION.SDK_INT < 18) {
			mWebViewContent.clearView();
		} else {
			mWebViewContent.loadUrl("about:blank");
		}
	}

	public void setData() {
		mMaterial = getMainActivity().currentMaterial;
		if (mMaterial.getMaterialType() == Material.TYPE_MODULE) {
			getMainActivity().setButtonTableContent(false);
			fillContentWebview();
		} else {
			getMainActivity().setButtonTableContent(true);
			mCollectionContents = mMaterial.getCollectionContent();
			if (mCollectionContents != null) {
				getMainActivity().currentCollectionContent = mCollectionContents;
				final String id = mCollectionContents.get(getMainActivity().currentModuleIndex).getId();
				if (md.isDownloadedMaterial(id)) {
					fillContentWebview(id);
				} else {
					ServiceController sc = new ServiceController(getMainActivity());
					sc.downloadSubMaterial(id, new IDownloadListener() {
						@Override
						public void onDownloadMaterialDone(boolean isDownloaded, int code) {
							if (code == ServiceController.CODE_NO_INTERNET) {
								DialogHelper.showDialogMessage(getMainActivity(), getMainActivity().getResources()
										.getString(R.string.msg_no_internet));
							} else if (code == ServiceController.CODE_TOKEN_EXPIRE) {
								setData();
							} else if (code == ServiceController.CODE_CONNECTION_TIMEOUT) {
								DialogHelper.showConfirmMessage(getMainActivity(),
										getMainActivity().getString(R.string.msg_connection_timeout),
										new IDialogListener() {
											@Override
											public void onOKClick() {
												setData();
											}
										});
							} else if (isDownloaded) {
								fillContentWebview(id);
							}
						}
					});
				}
			}
		}
	}

	private void fillContentWebview(String materialId) {
		mMaterial = md.getMaterialById(materialId);
		fillContentWebview();
	}

	private void fillContentWebview() {
		getMainActivity().setHeaderTitle(mMaterial.getTitle());
		lblPublishDate.setText(getActivity().getString(R.string.publishOn) + ": "
				+ DateTimeHelper.getDateFromDateTime(mMaterial.getModified()));
		if (pd.isDownloadedPerson(mMaterial.getAuthor())) {
			Person per = pd.getPersonById(mMaterial.getAuthor());
			lblAuthor.setText(getActivity().getString(R.string.by) + ": " + per.getFullname());
		} else {
			ServiceController sc = new ServiceController(getMainActivity());
			sc.downloadPersonDetail(mMaterial.getAuthor(), new IPersonListener() {
				@Override
				public void onLoadPersonDone(Person person, int code) {
					try {
						lblAuthor.setText(getActivity().getString(R.string.by) + ": " + person.getFullname());
					} catch (NullPointerException e) {
						lblAuthor.setText("");
					}
				}
			});
		}

		mWebViewContent.loadData(mMaterial.getText(), "text/html", "UTF-8");
		mWebViewContent.reload();
	}
}