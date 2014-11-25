/**
 * 
 */
package vn.edu.voer.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

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

	/**
	 * @param modified
	 * @return
	 */
	public static String getDateFromDateTime(String datetime) {
		
		String date;
		try {
			date = datetime.split("T")[0];
			date = date.split(" ")[0];
		} catch (NullPointerException e) {
			date = "";
		} catch (PatternSyntaxException e) {
			date = "";
		}
		return date;
	}
	
}
