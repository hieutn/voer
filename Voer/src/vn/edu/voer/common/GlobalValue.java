package vn.edu.voer.common;

import vn.edu.voer.utility.MySharedPreferences;
import android.app.Activity;

public class GlobalValue {
	public static MySharedPreferences preferences;

	public static void constructor(Activity activity) {
		preferences = new MySharedPreferences(activity);
	}
}