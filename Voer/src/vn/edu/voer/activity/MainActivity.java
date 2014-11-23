package vn.edu.voer.activity;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.fragment.NavigationDrawerFragment;
import vn.edu.voer.fragment.NavigationDrawerFragment.NavigationDrawerCallbacks;
import vn.edu.voer.object.Category;
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

	protected static final String TAG = MainActivity.class.getSimpleName();

	private DrawerLayout mDrawerLayout;
	private NavigationDrawerFragment mNavigationDrawerFragment;

	private TextView lblHeader, lblTabLibrary, lblTabCategory, lblTabSearch;
	private View btnTableContent;

	private FragmentManager fm;
	public List<Fragment> arrayFragments;
	public int currentFragment;

	public Material currentMaterial;
	public Category currentCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(
				R.id.navigation_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		// mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);
		mDrawerLayout.closeDrawers();

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
		lblTabLibrary.setOnClickListener(this);
		lblTabCategory.setOnClickListener(this);
		lblTabSearch.setOnClickListener(this);
		btnTableContent.setOnClickListener(this);
	}

	private void initFragment() {
		fm = getSupportFragmentManager();
		arrayFragments = new ArrayList<Fragment>();
		arrayFragments.add(fm.findFragmentById(R.id.fragmentLibrary));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentCategory));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearch));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentDetailContent));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearchResult));

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

	public void gotoFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(
		// R.anim.fragment_out_right,R.anim.fragment_in_left);
		transaction.show(arrayFragments.get(fragment));
		transaction.hide(arrayFragments.get(currentFragment));

		// transaction.setCustomAnimations(R.anim.slide_in_left,
		// R.anim.slide_out_right);
		// transaction.replace(R.id.layoutFragment,
		// arrayFragments.get(fragment));

		transaction.commit();
		currentFragment = fragment;

		if (currentFragment == DETAIL_CONTENT) {
			btnTableContent.setVisibility(View.VISIBLE);
		} else {
			btnTableContent.setVisibility(View.GONE);
		}
	}

	public void backFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		// transaction.setCustomAnimations(R.anim.fragment_in_left,
		// R.anim.fragment_out_left);
		transaction.show(arrayFragments.get(fragment));
		transaction.hide(arrayFragments.get(currentFragment));
		transaction.commit();
		currentFragment = fragment;
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
			lblHeader.setText(R.string.library);
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
			backFragment(TAB_LIBRARY);
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