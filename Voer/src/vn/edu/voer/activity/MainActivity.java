package vn.edu.voer.activity;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.object.Category;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IServiceListener;
import vn.edu.voer.utility.Constant;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener {
	public static final int TAB_LIBRARY = 0;
	public static final int TAB_CATEGORY = 1;
	public static final int TAB_SEARCH = 2;

	protected static final String TAG = MainActivity.class.getSimpleName();

	private TextView lblHeader;
	private View layoutTabLibrary, layoutTabCategory, layoutTabSearch;

	private FragmentManager fm;
	public List<Fragment> arrayFragments;
	public int currentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Example request api get materials
		ServiceController sc = new ServiceController();
		sc.getMaterials(Constant.URL_MATERIAL, new IServiceListener() {

			@Override
			public void onLoadMaterialsDone(MaterialList materialList) {

			}

			@Override
			public void onLoadCategoriesDone(ArrayList<Category> categories) {

			}
		});
		initUI();
		initControl();
		initFragment();
	}

	private void initUI() {
		lblHeader = (TextView) findViewById(R.id.lblHeader);
		layoutTabLibrary = findViewById(R.id.layoutTabLibrary);
		layoutTabCategory = findViewById(R.id.layoutTabCategory);
		layoutTabSearch = findViewById(R.id.layoutTabSearch);
	}

	private void initControl() {
		layoutTabLibrary.setOnClickListener(this);
		layoutTabCategory.setOnClickListener(this);
		layoutTabSearch.setOnClickListener(this);
	}

	private void initFragment() {
		fm = getSupportFragmentManager();
		arrayFragments = new ArrayList<Fragment>();
		arrayFragments.add(fm.findFragmentById(R.id.fragmentLibrary));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentCategory));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearch));

		FragmentTransaction transaction = fm.beginTransaction();
		for (Fragment fragment : arrayFragments) {
			transaction.hide(fragment);
		}
		transaction.commit();
	}

	private void showFragment(int fragmentIndex) {
		currentFragment = fragmentIndex;
		FragmentTransaction transaction = fm.beginTransaction();
		for (Fragment fragment : arrayFragments) {
			transaction.hide(fragment);
		}
		transaction.show(arrayFragments.get(fragmentIndex));
		transaction.commit();
	}

	public void setTabSelected(int tabSelected) {
		showFragment(tabSelected);
		switch (tabSelected) {
		case TAB_LIBRARY:
			layoutTabLibrary.setBackgroundColor(Color.MAGENTA);
			layoutTabCategory.setBackgroundColor(Color.TRANSPARENT);
			layoutTabSearch.setBackgroundColor(Color.TRANSPARENT);
			lblHeader.setText(R.string.library);
			break;

		case TAB_CATEGORY:
			layoutTabLibrary.setBackgroundColor(Color.TRANSPARENT);
			layoutTabCategory.setBackgroundColor(Color.MAGENTA);
			layoutTabSearch.setBackgroundColor(Color.TRANSPARENT);
			lblHeader.setText(R.string.category);
			break;

		default:
			layoutTabLibrary.setBackgroundColor(Color.TRANSPARENT);
			layoutTabCategory.setBackgroundColor(Color.TRANSPARENT);
			layoutTabSearch.setBackgroundColor(Color.MAGENTA);
			lblHeader.setText(R.string.search);
			break;
		}
	}

	// private void quitApp() {
	// showQuestionDialog(getString(R.string.quitApp), new DialogListener() {
	// @Override
	// public void onOk(Object object) {
	// finish();
	// }
	//
	// @Override
	// public void onCancel(Object object) {
	// }
	// });
	// }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layoutTabLibrary:
			onClickTabLibrary();
			break;

		case R.id.layoutTabCategory:
			onClickTabCategory();
			break;

		case R.id.layoutTabSearch:
			onClickTabSearch();
			break;

		}
	}

	private void onClickTabLibrary() {
		setTabSelected(TAB_LIBRARY);
	}

	private void onClickTabCategory() {
		setTabSelected(TAB_CATEGORY);
	}

	private void onClickTabSearch() {
		setTabSelected(TAB_SEARCH);
	}

}
