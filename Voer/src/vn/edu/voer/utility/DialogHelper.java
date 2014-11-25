/**
 * 
 */
package vn.edu.voer.utility;

import vn.edu.voer.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * @author sidd
 *
 *         Nov 25, 2014
 */
public class DialogHelper {

	/*
	 * Show dialog message
	 */
	@SuppressLint("InflateParams")
	public static void showDialogMessage(Context ctx, String message) {
		AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
		View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_quit_app, null);
		TextView msg = (TextView) view.findViewById(R.id.dialog_tv_msg);
		msg.setText(message);
		alert.setView(view);
		alert.setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alert.show();
	}

}
