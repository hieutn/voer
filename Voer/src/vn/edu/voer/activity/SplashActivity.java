package vn.edu.voer.activity;

import java.io.IOException;

import vn.edu.voer.R;
import vn.edu.voer.common.GlobalValue;
import vn.edu.voer.utility.FileHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends Activity {
	private static final String TAG = SplashActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);

		// Copy database from assets to device storage
		try {
			FileHelper.copyAssetFileToInternalStorage(this, getString(R.string.db_name));
		} catch (IOException e) {
			Log.i(TAG, e.toString());
		}

		GlobalValue.constructor(this);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(getBaseContext(), MainActivity.class));
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				finish();
			}
		}, 2000);
		
		
	}
}
