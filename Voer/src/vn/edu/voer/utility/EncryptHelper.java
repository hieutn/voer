/**
 * 
 */
package vn.edu.voer.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author sidd
 *
 *         Nov 24, 2014
 */
public class EncryptHelper {
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";

	}
}