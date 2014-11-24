package vn.edu.voer.utility;

public class StringUtil {
	public static boolean isEmpty(String string) {
		return (string == null || "".equals(string.trim()));
	}
}