/**
 * 
 */
package vn.edu.voer.database.schema;

import vn.edu.voer.database.AbstractDatabaseManager;
import vn.edu.voer.database.AbstractDatabaseManager.SQLPair;

/**
 * @author sidd
 *
 * Nov 23, 2014
 */
public class PersonSchema {

	public static final String TABLE_NAME = "Person";
	
	public static final String ID = "id";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	public static final String USER_ID = "user_id";
	public static final String TITLE = "title";
	public static final String CLIENT_ID = "client_id";
	public static final String FULLNAME = "fullname";
	public static final String EMAIL = "email";
	
	public static final String[] columns = {ID, FIRST_NAME, LAST_NAME, USER_ID, TITLE, CLIENT_ID, FULLNAME, EMAIL};
	
	public static final AbstractDatabaseManager.SQLPair[] params = new SQLPair[] {
		new SQLPair(ID, "INTEGER PRIMARY KEY NOT NULL"),
		new SQLPair(FIRST_NAME, "VARCHAR"),
		new SQLPair(LAST_NAME, "VARCHAR"),
		new SQLPair(USER_ID, "VARCHAR"),
		new SQLPair(TITLE, "VARCHAR"),
		new SQLPair(CLIENT_ID, "INTEGER"),
		new SQLPair(FULLNAME, "VARCHAR"),
		new SQLPair(EMAIL, "VARCHAR")	
	};
	
}