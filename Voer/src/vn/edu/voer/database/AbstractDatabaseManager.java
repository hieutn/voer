package vn.edu.voer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public abstract class AbstractDatabaseManager {
	public static final String LOG_TAG = AbstractDatabaseManager.class.getSimpleName();
	public static final int INVALID = -1;
	private final String mSpase = " ";
	private final String mComma = ",";
	
	protected Context context;
	protected DatabaseHelper dbHelper;
	protected SQLiteDatabase db;

	private boolean allowWrite = true;

	/* ------------------------------------------------------ */
	/* ABSTRACT METHOD */
	/* ------------------------------------------------------ */
	public abstract String getDatabaseName();

	public abstract int getDatabaseVersion();

	protected abstract String[] getSQLCreateTables();

	protected abstract String[] getTableNames();
	
	protected AbstractDatabaseManager(Context ctx, boolean allowWrite) {
		this.allowWrite = allowWrite;
		if (allowWrite) {
			this.context = ctx.getApplicationContext();
		} else {
			this.context = ctx;
		}
		
	}

	public synchronized boolean open() throws SQLiteException{
		if (db != null && db.isOpen()) {
			return true;
		}
		
		if (db == null) {
			dbHelper = new DatabaseHelper(context, getDatabaseName(), null,
					getDatabaseVersion());
		}
		
		db = dbHelper.getWritableDatabase();
		
		return (db != null);
	}

	public synchronized void close() {
		if (dbHelper != null) {
			dbHelper.close();
		}
	}

	public boolean isWritable() {
		return allowWrite;
	}

	public String getSQLCreateTable(String tableName, SQLPair... columnPairs) {
		// TODO: Generate table sql creator
		StringBuffer buffer = new StringBuffer();
		buffer.append("CREATE TABLE IF NOT EXISTS ");
		buffer.append(tableName);
		buffer.append(" (");
		
		int lenght = columnPairs.length;
		for (int i = 0; i < lenght; i++) {
			SQLPair pair = columnPairs[i];
			buffer.append(pair.fieldName);
			buffer.append(mSpase);
			buffer.append(pair.fieldInitSQL.toUpperCase(context.getResources().getConfiguration().locale));
			buffer.append(mComma);
		}
		
		if (buffer.charAt(buffer.length() - 1) == ',') {
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append(");");
		}
		
		return buffer.toString();
	}

	protected synchronized void execSQL(String sql) throws SQLException {
		open();
		db.execSQL(sql);
		close();
	}

	public synchronized long insert(String table, ContentValues value) throws SQLException{
		if (!allowWrite) {
			logError("Cannot write in readable-only database instance");
			return INVALID;
		} else if (value == null) {
			return INVALID;
		} 
		return db.insert(table, null, value);
	}
	
	/**
	 * Insert list obj to db
	 * @param table
	 * @param values
	 * @return number of obj inserted
	 */
	public synchronized int insertListObject(String table, ContentValues[] values) throws SQLException{
		if (!allowWrite) {
			logError("Cannot write in readable-only database instance");
			return INVALID;
		} else if (values == null) {
			return INVALID;
		} 
		
		int count = 0;
		for (ContentValues content : values) {
			if (db.insert(table, null, content) != -1) {
				count++;
			}
		}

		return count;
	}
	
	public synchronized int update(String table,
			ContentValues values, String whereClause, String[] whereArgs) throws SQLException {
		
		if (!allowWrite) {
			logError("Cannot write in readable-only database instance");
			return INVALID;
		} else if (values == null) {
			return INVALID;
		} 
		
		open();
		return db.update(table, values, whereClause, whereArgs);
	}
	
	protected synchronized int delete(String tableName, String whereClause,
			String[] whereArgs) throws SQLException {
		if (!allowWrite) {
			logError("Cannot delete table in readable-only database instance");
			return INVALID;
		}
		
		open();
		return db.delete(tableName, whereClause, whereArgs);
	}
	
	public synchronized Cursor fetchFirst(String tableName) throws SQLException{
		Cursor cur = db.query(tableName, null, null, null, null, null, null, "0,1");
		return cur;
	}

	public synchronized Cursor fetchAll(String tableName) throws SQLException{
		Cursor cur = db.query(tableName, null, null, null, null, null, null);
		return cur;
	}
	
	protected synchronized Cursor fetch(String tableName, String whereClause) throws SQLException {
		return db.query(tableName, null, whereClause, null, null, null, null);
	}

	protected synchronized Cursor fetch(String tableName, String whereClause,
			String[] whereArgs) throws SQLException {
		open();
		return db.query(tableName, null, whereClause, whereArgs, null, null,
				null);
	}
	
	protected synchronized Cursor fetchAndSort(String tableName, String whereClause, String orderBy)
			throws SQLException {
		return db.query(tableName, null, whereClause, null, null, null, orderBy);
	}
	
	protected synchronized Cursor fetch(String sql)
			throws SQLException {
		return db.rawQuery(sql, null);
	}

	private void logError(String error) {
		// TODO: Print log message
		Log.e(LOG_TAG, error);
	}
	
	
	/*----------------------------------------------------------*/
	public static class SQLPair {
		String fieldName;
		String fieldInitSQL;

		public SQLPair(String fieldName, String fieldInitSQL) {
			this.fieldName = fieldName;
			this.fieldInitSQL = fieldInitSQL;
		}
	}

	
	/* ------------------------------------------------------ */
	/* DATABASE HELPER */
	/* ------------------------------------------------------ */
	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			for (String sql : getSQLCreateTables()) {
				db.execSQL(sql);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO: Upgrade database will delete all user data
			for (String tableName : getTableNames()) {
				db.execSQL("DROP TABLE IF EXISTS " + tableName);
			}
			onCreate(db);
		}
	}
}
