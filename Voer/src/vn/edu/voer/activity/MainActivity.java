package vn.edu.voer.activity;

import vn.edu.voer.R;
import vn.edu.voer.service.ServiceController;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new ServiceController().getCategories();
	}
}
