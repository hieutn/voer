package vn.edu.voer.activity;

import vn.edu.voer.R;
import vn.edu.voer.common.GlobalValue;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);

		GlobalValue.constructor(this);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(MainActivity.class);
				finish();
			}
		}, 2000);
	}
}
