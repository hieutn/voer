package vn.edu.voer.database;

import java.util.ArrayList;

import vn.edu.voer.database.schema.MaterialSchema;
import vn.edu.voer.database.schema.PersonSchema;
import vn.edu.voer.object.Material;
import vn.edu.voer.object.Person;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DbConnectionHelper {
	private AbstractDatabaseManager mDbManager;
	private static DbConnectionHelper mInstance;

	public DbConnectionHelper(Context ctx) {
		mDbManager = new DBManager(ctx, true);
	}

	public static synchronized DbConnectionHelper getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new DbConnectionHelper(ctx);
		}

		return mInstance;
	}
	
	public synchronized ArrayList<Material> getMaterials(String condition, String orderBy) {
		ArrayList<Material> result = new ArrayList<Material>();
		mDbManager.open();
		Cursor cur = mDbManager.fetchAndSort(MaterialSchema.TABLE_NAME, condition, orderBy);
		if (cur == null) {
			return null;
		}
		
		while (cur.moveToNext()) {
			result.add(new Material(
					cur.getString(cur.getColumnIndex(MaterialSchema.DESCRIPTION)),
					cur.getString(cur.getColumnIndex(MaterialSchema.LANGUAGE)), 
					cur.getString(cur.getColumnIndex(MaterialSchema.TITLE)), 
					cur.getString(cur.getColumnIndex(MaterialSchema.TEXT)),
					cur.getString(cur.getColumnIndex(MaterialSchema.IMAGE)), 
					cur.getInt(cur.getColumnIndex(MaterialSchema.TYPE)),
					cur.getString(cur.getColumnIndex(MaterialSchema.MODIFIED)),
					cur.getString(cur.getColumnIndex(MaterialSchema.ID)), 
					cur.getInt(cur.getColumnIndex(MaterialSchema.VERSION)),
					cur.getString(cur.getColumnIndex(MaterialSchema.EDITOR)),
					cur.getString(cur.getColumnIndex(MaterialSchema.DERIVED_FROM)),
					cur.getString(cur.getColumnIndex(MaterialSchema.KEYWORD)), 
					cur.getString(cur.getColumnIndex(MaterialSchema.LICENSE_ID)), 
					cur.getString(cur.getColumnIndex(MaterialSchema.AUTHOR)), 
					cur.getString(cur.getColumnIndex(MaterialSchema.CATEGORIES))));
		}
		cur.close();
		mDbManager.close();
		return result;
	}
	
	
	public ArrayList<Person> getPersons(String condition, String orderBy) {
		ArrayList<Person> result = new ArrayList<Person>();
		mDbManager.open();
		Cursor cur = mDbManager.fetchAndSort(PersonSchema.TABLE_NAME, condition, orderBy);
		if (cur == null) {
			return null;
		}
		
		while (cur.moveToNext()) {
			result.add(new Person(
					cur.getInt(cur.getColumnIndex(PersonSchema.ID)),
					cur.getString(cur.getColumnIndex(PersonSchema.FIRST_NAME)),
					cur.getString(cur.getColumnIndex(PersonSchema.LAST_NAME)),
					cur.getString(cur.getColumnIndex(PersonSchema.USER_ID)),
					cur.getString(cur.getColumnIndex(PersonSchema.TITLE)),
					cur.getInt(cur.getColumnIndex(PersonSchema.CLIENT_ID)),
					cur.getString(cur.getColumnIndex(PersonSchema.FULLNAME)), 
					cur.getString(cur.getColumnIndex(PersonSchema.EMAIL))));
		}
		cur.close();
		mDbManager.close();
		return result;
	}

	public synchronized long insert(String table, String[] columns,
			Object[] values) {
		mDbManager.open();
		long res = mDbManager.insert(table, getContentValues(columns, values));
		mDbManager.close();
		return res;
	}

	public synchronized long update(String table, String[] columns,
			Object[] values, String whereClause) {
		mDbManager.open();
		long res = mDbManager.update(table, getContentValues(columns, values), whereClause, null);
		mDbManager.close();
		return res;
	}
	
	public synchronized long delete(String tableName, String whereClause) {
		mDbManager.open();
		long res = mDbManager.delete(tableName, whereClause, null);
		mDbManager.close();
		return res;
		
	}

	private ContentValues getContentValues(String[] columns, Object[] values) {
		ContentValues value = new ContentValues();
		for (int i = 0; i < columns.length; i++) {
			if (values[i] instanceof String)
				value.put(columns[i], (String) values[i]);
			else if (values[i] instanceof Byte)
				value.put(columns[i], (Byte) values[i]);
			else if (values[i] instanceof Short)
				value.put(columns[i], (Short) values[i]);
			else if (values[i] instanceof Integer)
				value.put(columns[i], (Integer) values[i]);
			else if (values[i] instanceof Long)
				value.put(columns[i], (Long) values[i]);
			else if (values[i] instanceof Float)
				value.put(columns[i], (Float) values[i]);
			else if (values[i] instanceof Double)
				value.put(columns[i], (Double) values[i]);
			else if (values[i] instanceof byte[])
				value.put(columns[i], (byte[]) values[i]);
		}

		return value;
	}

}
