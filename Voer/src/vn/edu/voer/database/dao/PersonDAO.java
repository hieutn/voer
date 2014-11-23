/**
 * 
 */
package vn.edu.voer.database.dao;

import java.util.ArrayList;

import vn.edu.voer.database.DbConnectionHelper;
import vn.edu.voer.database.schema.PersonSchema;
import vn.edu.voer.object.Person;
import android.content.Context;

/**
 * @author sidd
 *
 *         Nov 23, 2014
 */
public class PersonDAO {

	private DbConnectionHelper mDBHelper;

	/**
	 * 
	 * @param ctx
	 */
	public PersonDAO(Context ctx) {
		mDBHelper = DbConnectionHelper.getInstance(ctx);
	}
	
	/**
	 * 
	 * @param person
	 * @return
	 */
	public boolean insertPerson(Person person) {
		
		String[] columns = PersonSchema.columns;
		Object[] values = {
				person.getId(),
				person.getFirstName(),
				person.getLastName(),
				person.getUserID(),
				person.getTitle(),
				person.getClientID(),
				person.getFullname(),
				person.getEmail()
		};

		long res = mDBHelper.insert(PersonSchema.TABLE_NAME, columns, values);
		if (res < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Person getPersonById(String id) {
		StringBuilder condition = new StringBuilder();
		condition.append(PersonSchema.ID);
		condition.append("=");
		condition.append("'");
		condition.append(id);
		condition.append("'");
		ArrayList<Person> persons = mDBHelper.getPersons(condition.toString(), "");
		if (persons != null && persons.size() > 0) {
			return persons.get(0);
		} else {
			return null;
		}
	}
	
}
