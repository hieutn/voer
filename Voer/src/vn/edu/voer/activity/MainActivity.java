package vn.edu.voer.activity;

import java.util.ArrayList;

import vn.edu.voer.R;
import vn.edu.voer.object.Category;
import vn.edu.voer.object.MaterialList;
import vn.edu.voer.service.ServiceController;
import vn.edu.voer.service.ServiceController.IServiceListener;
import vn.edu.voer.utility.Constant;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

	protected static final String TAG = MainActivity.class.getSimpleName();

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
		
	}
}
