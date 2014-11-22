package vn.edu.voer.activity;

import vn.edu.voer.R;
import vn.edu.voer.listener.DialogListener;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends FragmentActivity {
	private boolean isShowDialogNetwork = false;

	public void startActivity(Class<?> cls) {
		startActivity(new Intent(this, cls));
	}

	public void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(this, cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	protected void showToast(int stringId) {
		Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	// @SuppressLint("InflateParams")
	// public void showQuestionDialog(String message, final DialogListener
	// listener) {
	// LayoutInflater inflater = getLayoutInflater();
	// View dialogView = inflater.inflate(R.layout.dialog_alert, null);
	// TextView lblMessage = (TextView)
	// dialogView.findViewById(R.id.lblMessage);
	// lblMessage.setText(message);
	// new AlertDialog.Builder(this).setView(dialogView).setCancelable(false)
	// .setPositiveButton(android.R.string.ok, new OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// listener.onOk(null);
	// }
	// }).setNegativeButton(R.string.cancel, new OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog, int which) {
	// listener.onCancel(null);
	// }
	// }).show();
	// }

	// @SuppressLint("InflateParams")
	// public void showDialogNoNetwork() {
	// if (!isShowDialogNetwork) {
	// new
	// AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage(R.string.noNetwork)
	// .setCancelable(false).setPositiveButton(android.R.string.ok, new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// Intent settings = new
	// Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
	// settings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// startActivity(settings);
	// overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	// isShowDialogNetwork = false;
	// }
	// }).setNegativeButton(R.string.cancel, new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// isShowDialogNetwork = false;
	// }
	// }).show();
	// isShowDialogNetwork = true;
	// }
	// }
}
