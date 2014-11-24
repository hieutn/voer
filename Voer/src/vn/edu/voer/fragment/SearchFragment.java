package vn.edu.voer.fragment;

import vn.edu.voer.R;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchFragment extends BaseFragment {
	private Button btnSearch, btnSearchLong;
	private EditText txtKeyword;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		initUI(view);
		initControl();
		return view;
	}

	private void initUI(View view) {
		btnSearch = (Button) view.findViewById(R.id.btnSearch);
		btnSearchLong = (Button) view.findViewById(R.id.btnSearchLong);
		txtKeyword = (EditText) view.findViewById(R.id.txtKeyword);
	}

	private void initControl() {
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				search();
			}
		});
		btnSearchLong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showToast("Search feature is coming soon.");
			}
		});

		txtKeyword.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					search();
				}
				return false;
			}
		});
	}

	private void search() {
		hideKeyBoard();
		// String keyword = txtKeyword.getText().toString();
		// goToFragment(MainActivity.SEARCH_RESULT);
		// Log.e("", "search: "+ keyword);
	}
}
