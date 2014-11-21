package vn.edu.voer.activity;

import java.util.ArrayList;

import vn.edu.voer.R;
import vn.edu.voer.object.Category;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IServiceListener;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends BaseActivity {

	protected static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new ServiceController().getCategories(new IServiceListener() {
			
			@Override
			public void onLoadCategoriesDone(ArrayList<Category> categories) {
				for (Category cat: categories) {
					Log.i(TAG, "ID: " + cat.getId() + ", name: " + cat.getName());
				}
			}
		});
	}
}
