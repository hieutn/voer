package vn.edu.voer.fragment;

import vn.edu.voer.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category, container, false);

		initUI(view);
		return view;
	}

	private void initUI(View view) {

	}
}
