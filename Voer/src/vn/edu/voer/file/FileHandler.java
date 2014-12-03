/**
 * 
 */
package vn.edu.voer.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author sidd
 *
 *         Nov 22, 2014
 */
public class FileHandler {

	public static void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		out.flush();
		out.close();
		in.close();
	}

}
