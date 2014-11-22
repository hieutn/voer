package vn.edu.voer.activity;

import java.util.ArrayList;

import vn.edu.voer.object.Category;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IServiceListener;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

	protected static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		
		new ServiceController().downloadMaterial("65db7ac1", new IServiceListener() {
			
			@Override
			public void onLoadMaterialsDone(MaterialList materialList) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadCategoriesDone(ArrayList<Category> categories) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
