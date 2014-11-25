package vn.edu.voer.activity;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.fragment.DetailContentFragment;
import vn.edu.voer.fragment.NavigationDrawerFragment;
import vn.edu.voer.fragment.NavigationDrawerFragment.NavigationDrawerCallbacks;
import vn.edu.voer.object.Category;
import vn.edu.voer.object.CollectionContent;
import vn.edu.voer.object.Material;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener, NavigationDrawerCallbacks {
	public static final int TAB_LIBRARY = 0;
	public static final int TAB_CATEGORY = 1;
	public static final int TAB_SEARCH = 2;
	public static final int DETAIL_CONTENT = 3;
	public static final int SEARCH_RESULT = 4;

	public static final int RESULT_TYPE_MATERIAL = 10;
	public static final int RESULT_TYPE_SEARCH = 11;

	protected static final String TAG = MainActivity.class.getSimpleName();

	private DrawerLayout mDrawerLayout;
	public NavigationDrawerFragment navigationDrawerFragment;

	private TextView lblHeader, lblTabLibrary, lblTabCategory, lblTabSearch;
	private View btnTableContent;

	private FragmentManager fm;
	public List<Fragment> listFragments;
	public int currentFragment;
	public int fragmentBeforeResult;
	public int fragmentBeforeDetail;

	public Material currentMaterial;
	public int currentModuleIndex = 0;
	public Category currentCategory;
	public ArrayList<CollectionContent> currentCollectionContent = null;
	public String currentSearchKeyword;
	public int currentResultType;
	public boolean isSearching;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(
				R.id.navigation_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

		// Set up the drawer.
		navigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);

		initUI();
		initControl();
		initFragment();
		setTabSelected(TAB_LIBRARY);
	}

	private void initUI() {
		lblHeader = (TextView) findViewById(R.id.lblHeader);
		lblTabLibrary = (TextView) findViewById(R.id.lblTabLibrary);
		lblTabCategory = (TextView) findViewById(R.id.lblTabCategory);
		lblTabSearch = (TextView) findViewById(R.id.lblTabSearch);
		btnTableContent = findViewById(R.id.btnTableContent);
	}

	private void initControl() {
		lblHeader.setSelected(true);
		lblTabLibrary.setOnClickListener(this);
		lblTabCategory.setOnClickListener(this);
		lblTabSearch.setOnClickListener(this);
		btnTableContent.setOnClickListener(this);
	}

	private void initFragment() {
		fm = getSupportFragmentManager();
		listFragments = new ArrayList<Fragment>();
		listFragments.add(fm.findFragmentById(R.id.fragmentLibrary));
		listFragments.add(fm.findFragmentById(R.id.fragmentCategory));
		listFragments.add(fm.findFragmentById(R.id.fragmentSearch));
		listFragments.add(fm.findFragmentById(R.id.fragmentDetailContent));
		listFragments.add(fm.findFragmentById(R.id.fragmentSearchResult));

		FragmentTransaction transaction = fm.beginTransaction();
		for (Fragment fragment : listFragments) {
			transaction.hide(fragment);
		}
		transaction.commit();
	}

	private void showFragment(int fragmentIndex) {
		currentFragment = fragmentIndex;
		FragmentTransaction transaction = fm.beginTransaction();
		for (Fragment fragment : listFragments) {
			transaction.hide(fragment);
		}
		transaction.show(listFragments.get(fragmentIndex));
		transaction.commit();
		onChangeFragment();
	}

	public void gotoFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(
		// R.anim.fragment_out_right,R.anim.fragment_in_left);
		transaction.show(listFragments.get(fragment));
		transaction.hide(listFragments.get(currentFragment));

		// transaction.setCustomAnimations(R.anim.slide_in_left,
		// R.anim.slide_out_right);
		// transaction.replace(R.id.layoutFragment,
		// listFragments.get(fragment));

		if (fragment == SEARCH_RESULT) {
			fragmentBeforeResult = currentFragment;
		}

		transaction.commit();
		currentFragment = fragment;
		onChangeFragment();
	}

	public void backFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(R.anim.fragment_in_left,
		// R.anim.fragment_out_left);
		transaction.show(listFragments.get(fragment));
		transaction.hide(listFragments.get(currentFragment));
		transaction.commit();
		currentFragment = fragment;
		onChangeFragment();
	}

	private void onChangeFragment() {
		if (currentFragment == DETAIL_CONTENT) {
			setButtonTableContent(true);
		} else {
			setButtonTableContent(false);
		}
	}

	public void setButtonTableContent(boolean isVisible) {
		if (isVisible) {
			btnTableContent.setVisibility(View.VISIBLE);
		} else {
			btnTableContent.setVisibility(View.GONE);
		}
	}

	public void setHeaderTitle(String title) {
		lblHeader.setText(title);
	}

	public void changeCurrentModelIndex(int currentModuleIndex) {
		this.currentModuleIndex = currentModuleIndex;
		((DetailContentFragment) listFragments.get(DETAIL_CONTENT)).setData();
	}

	public void displayDetailContent(Material material) {
		fragmentBeforeDetail = currentFragment;
		currentMaterial = material;
		gotoFragment(DETAIL_CONTENT);
		((DetailContentFragment) listFragments.get(MainActivity.DETAIL_CONTENT)).setData();
		if (currentMaterial.getMaterialType() == Material.TYPE_COLLECTION) {
			navigationDrawerFragment.setDataTableContent();
		}
	}

	public void setTabSelected(int tabSelected) {
		showFragment(tabSelected);
		switch (tabSelected) {
		case TAB_LIBRARY:
			lblTabLibrary.setTextColor(getResources().getColor(R.color.orange));
			lblTabCategory.setTextColor(Color.DKGRAY);
			lblTabSearch.setTextColor(Color.DKGRAY);
			lblTabLibrary.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_library_orange, 0, 0);
			lblTabCategory.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_category_gray, 0, 0);
			lblTabSearch.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_search_gray, 0, 0);
			lblHeader.setText(R.string.yourLibrary);
			break;

		case TAB_CATEGORY:
			lblTabLibrary.setTextColor(Color.DKGRAY);
			lblTabCategory.setTextColor(getResources().getColor(R.color.orange));
			lblTabSearch.setTextColor(Color.DKGRAY);
			lblTabLibrary.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_library_gray, 0, 0);
			lblTabCategory.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_category_orange, 0, 0);
			lblTabSearch.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_search_gray, 0, 0);
			lblHeader.setText(R.string.category);
			break;

		default:
			lblTabLibrary.setTextColor(Color.DKGRAY);
			lblTabCategory.setTextColor(Color.DKGRAY);
			lblTabSearch.setTextColor(getResources().getColor(R.color.orange));
			lblTabLibrary.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_library_gray, 0, 0);
			lblTabCategory.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_category_gray, 0, 0);
			lblTabSearch.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_search_orange, 0, 0);
			lblHeader.setText(R.string.search);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lblTabLibrary:
			onClickTabLibrary();
			break;

		case R.id.lblTabCategory:
			onClickTabCategory();
			break;

		case R.id.lblTabSearch:
			onClickTabSearch();
			break;

		case R.id.btnTableContent:
			onClickTableContent();
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

	@SuppressLint("RtlHardcoded")
	private void onClickTableContent() {
		if (currentFragment == DETAIL_CONTENT) {
			mDrawerLayout.openDrawer(Gravity.LEFT);
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {

	}

	@Override
	public void onBackPressed() {
		switch (currentFragment) {
		case DETAIL_CONTENT:
			backFragment(fragmentBeforeDetail);
			break;

		case SEARCH_RESULT:
			backFragment(fragmentBeforeResult);
			break;

		default:
			quitApp();
			break;
		}
	}

	@SuppressLint("InflateParams")
	private void quitApp() {
		new AlertDialog.Builder(this).setView(getLayoutInflater().inflate(R.layout.dialog_quit_app, null))
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						finish();
					}
				}).setNegativeButton(R.string.no, null).show();
	}
}