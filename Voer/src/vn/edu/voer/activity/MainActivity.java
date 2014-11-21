package vn.edu.voer.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import vn.edu.voer.R;
import vn.edu.voer.common.GlobalValue;
import vn.edu.voer.object.Category;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {
	public static final int TAB_LIBRARY = 0;
	public static final int TAB_BROWSER_BY_CATEGORY = 1;
	public static final int TAB_SEARCH = 2;
	
	
	private FragmentManager fm;
	public List<Fragment> arrayFragments;
	public int currentFragment;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	private void initFragment() {
		fm = getFragmentManager();
		arrayFragments = new ArrayList<Fragment>();
		arrayFragments.add(fm.findFragmentById(R.id.fragmentNotification));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearch));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentShare));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentUser));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearchCondition));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearchLocation));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearchHistory));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSearchFeature));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentSetting));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentHome));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentRestaurantDetail));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentRestaurantMap));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentGallery));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentViewImage));
		arrayFragments.add(fm.findFragmentById(R.id.fragmentNotificationDetail));

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

	public void goLastFragment() {
		showFragment(arrayFragments.size() - 1);
	}

 

 
	public void gotoFragment(int fragment) {
		Logger.e("", "arrayFragments size: " + arrayFragments.size());

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
}