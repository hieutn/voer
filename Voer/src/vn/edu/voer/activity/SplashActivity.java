package vn.edu.voer.activity;

import java.io.IOException;

import vn.edu.voer.R;
import vn.edu.voer.common.GlobalValue;
import vn.edu.voer.service.AuthUtil;
import vn.edu.voer.utility.FileHelper;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends Activity {
	private static final String TAG = SplashActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);
		
		AuthUtil.authExecute(this);

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

		initData();
	}

	private void initData() {
		TextView lblVersion = (TextView) findViewById(R.id.lblVersion);
		PackageInfo pInfo;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			lblVersion.setText("Version: " + pInfo.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
