/**
 * 
 */
package vn.edu.voer.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vn.edu.voer.BuildConfig;
import android.util.Log;

/**
 * @author sidd
 *
 *         Nov 22, 2014
 */
public class FileHandler {

	private static final String TAG = FileHandler.class.getSimpleName();

	public static void copyFile(InputStream in, OutputStream out) throws IOException {
		Log.i(TAG, "Running here");
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		out.flush();
		out.close();
		in.close();
		if (BuildConfig.DEBUG) {
			Log.i(TAG, "Database copied to internal storage");
		}
	}

}
