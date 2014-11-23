package vn.edu.voer.fragment;

import vn.edu.voer.R;
import vn.edu.voer.activity.MainActivity;
import vn.edu.voer.listener.DialogListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class BaseFragment extends Fragment {
	
	protected View mPrbLoading;
	
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}

	protected void startActivity(Class<?> cls) {
		startActivity(new Intent(getActivity(), cls));
	}

	protected void startActivity(Class<?> cls, Bundle bundle) {
		startActivity(new Intent(getActivity(), cls).putExtras(bundle));
	}

	public MainActivity getMainActivity() {
		return (MainActivity) getActivity();
	}

	public void showDialogNoNetwork() {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					Intent settings = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
					settings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(settings);
					dialog.dismiss();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					dialog.dismiss();
					break;
				}
			}
		};

		// AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// builder.setTitle(R.string.app_name).setMessage(R.string.noNetwork)
		// .setPositiveButton(R.string.yes, dialogClickListener)
		// .setNegativeButton(R.string.no, dialogClickListener).show();
	}

	protected void showDialog(int idString, final DialogListener listener) {
		new AlertDialog.Builder(getActivity()).setTitle(R.string.app_name).setMessage(idString)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						listener.onOk(null);
					}
				}).show();
	}

	protected void showToast(int idString) {
		Toast.makeText(getActivity(), idString, Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}

	protected String getText(EditText text) {
		try {
			return text.getText().toString().trim();
		} catch (Exception e) {
			return "";
		}
	}

	protected void goToFragment(int fragment) {
		getMainActivity().gotoFragment(fragment);
	}

	protected void hideKeyBoard() {
		try {
			InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(
					Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}
}
