/**
 * 
 */
package vn.edu.voer.file;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

/**
 * @author sidd
 *
 * Nov 22, 2014
 */
public class AssetManagerHelper {
	private AssetManager mAssetManager;
	
	public AssetManagerHelper(Context ctx) {
		mAssetManager = ctx.getResources().getAssets();
	}
	
	public InputStream openFile(String fileName) throws IOException {
		return mAssetManager.open(fileName);
	}
}
