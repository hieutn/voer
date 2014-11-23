/**
 * 
 */
package vn.edu.voer.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * @author sidd
 *
 *         Nov 23, 2014
 */
public class DateTimeHelper {

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDateTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

}
