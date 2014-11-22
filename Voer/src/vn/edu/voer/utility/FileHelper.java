package vn.edu.voer.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vn.edu.voer.BuildConfig;
import vn.edu.voer.file.AssetManagerHelper;
import vn.edu.voer.file.FileHandler;
import android.content.Context;
import android.util.Log;

/**
 * @author sidd
 *
 *         Nov 22, 2014
 */
public class FileHelper {

	private static final String TAG = FileHelper.class.getSimpleName();

	public static void copyAssetFileToInternalStorage(Context ctx, String fileName) throws IOException {
		File des = ctx.getDatabasePath(fileName);
		if (BuildConfig.DEBUG) {
			Log.i(TAG, des.getAbsolutePath());
		}
		if (!des.exists()) {
			if (!des.getParentFile().exists()) {
				des.getParentFile().mkdir();
			}
			InputStream in = new AssetManagerHelper(ctx).openFile(fileName);
			OutputStream out = new FileOutputStream(des.getAbsolutePath());
			FileHandler.copyFile(in, out);
		} else {
			if (BuildConfig.DEBUG) {
				Log.i(TAG, "Database exists in internal storage");
			}
		}

	}

}