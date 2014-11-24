/**
 * 
 */
package vn.edu.voer.object;

/**
 * @author sidd
 *
 *         Nov 24, 2014
 */
public class Authentication {
	
	public static final String TOKEN = "token";
	
	private String token;
	private String expire;
	private String result;
	private String client_id;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @return the expire
	 */
	public String getExpire() {
		return expire;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @return the client_id
	 */
	public String getClientID() {
		return client_id;
	}
}
