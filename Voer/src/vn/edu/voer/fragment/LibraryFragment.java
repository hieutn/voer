package vn.edu.voer.fragment;

import java.util.ArrayList;
import java.util.List;

import vn.edu.voer.R;
import vn.edu.voer.activity.MainActivity;
import vn.edu.voer.adapter.BookAdapter;
import vn.edu.voer.object.Book;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LibraryFragment extends BaseFragment {
	private ListView lsvBook;
	private List<Book> listBooks;
	private BookAdapter adapter;

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
		if (!hidden) {
			if (listBooks.size() == 0) {
				initData();
			}
		}
	}

	private void initUI(View view) {
		lsvBook = (ListView) view.findViewById(R.id.lsvBook);
	}

	private void initControl() {
		listBooks = new ArrayList<Book>();
		adapter = new BookAdapter(getActivity(), listBooks);
		lsvBook.setAdapter(adapter);
		lsvBook.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				goToFragment(MainActivity.DETAIL_CONTENT);
			}
		});
	}

	private void initData() {
		listBooks.add(new Book(0, "Chi oi Anh yeu em"));
		listBooks.add(new Book(1, "Nhat ky chan rau"));
		listBooks.add(new Book(2, "Thep da toi the day"));
		listBooks.add(new Book(3, "Gio lanh dau mua"));
		listBooks.add(new Book(4, "Ngoi nha nho tren thao nguyen"));
		listBooks.add(new Book(4, "Tieng chim hot trong bui man gai"));
		adapter.notifyDataSetChanged();
	}
}