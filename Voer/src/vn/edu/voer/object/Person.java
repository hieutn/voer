/**
 * 
 */
package vn.edu.voer.object;

/**
 * @author sidd
 *
 *         Nov 23, 2014
 */
public class Person {

	private int id;
	private String first_name;
	private String last_name;
	private String user_id;
	private String title;
	private int client_id;
	private String fullname;
	private String email;

	public Person(int id, String firstName, String lastName, String userId, String title, int clientId, String fullName, String email) {
		this.id = id;
		this.first_name = firstName;
		this.last_name = lastName;
		this.user_id = userId;
		this.title = title;
		this.client_id = clientId;
		this.fullname = fullName;
		this.email = email;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the first_name
	 */
	public String getFirstName() {
		return first_name;
	}

	/**
	 * @return the last_name
	 */
	public String getLastName() {
		return last_name;
	}

	/**
	 * @return the user_id
	 */
	public String getUserID() {
		return user_id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the client_id
	 */
	public int getClientID() {
		return client_id;
	}

	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

}
