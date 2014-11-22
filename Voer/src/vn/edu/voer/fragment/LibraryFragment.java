package vn.edu.voer.fragment;

import java.util.ArrayList;

import vn.edu.voer.R;
import vn.edu.voer.activity.MainActivity;
import vn.edu.voer.adapter.LibraryAdapter;
import vn.edu.voer.database.dao.MaterialDAO;
import vn.edu.voer.object.Material;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LibraryFragment extends BaseFragment {
	private ListView lsvBook;
	private ArrayList<Material> mMaterials;
	private LibraryAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_library, container, false);

		initUI(view);
		initControl();
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
//		if (!hidden) {
//			if (mMaterials.size() == 0) {
//				initData();
//			}
//		}
	}

	private void initUI(View view) {
		lsvBook = (ListView) view.findViewById(R.id.lsvBook);
	}

	private void initControl() {
		MaterialDAO md = new MaterialDAO(getActivity());
		mMaterials = md.getAllMaterial();
		mAdapter = new LibraryAdapter(getActivity(), R.layout.library_item, mMaterials);
		lsvBook.setAdapter(mAdapter);
		lsvBook.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				goToFragment(MainActivity.DETAIL_CONTENT);
			}
		});
	}
}