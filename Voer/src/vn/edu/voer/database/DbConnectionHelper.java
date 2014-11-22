package vn.edu.voer.database;

import java.util.ArrayList;

import vn.edu.voer.database.schema.MaterialDetailSchema;
import vn.edu.voer.object.MaterialDetail;
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

	public synchronized ArrayList<MaterialDetail> getAllMaterialDetail() {
		ArrayList<MaterialDetail> result = new ArrayList<MaterialDetail>();
		mDbManager.open();
		Cursor cur = mDbManager.fetchAll(MaterialDetailSchema.TABLE_NAME);
		if (cur == null) {
			return null;
		}
		
		while (cur.moveToNext()) {
			result.add(new MaterialDetail(
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.DESCRIPTION)),
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.LANGUAGE)), 
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.TITLE)), 
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.TEXT)),
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.IMAGE)), 
					cur.getInt(cur.getColumnIndex(MaterialDetailSchema.TYPE)),
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.MODIFIED)),
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.ID)), 
					cur.getInt(cur.getColumnIndex(MaterialDetailSchema.VERSION)),
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.EDITOR)),
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.DERIVED_FROM)),
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.KEYWORD)), 
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.LICENSE_ID)), 
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.AUTHOR)), 
					cur.getString(cur.getColumnIndex(MaterialDetailSchema.CATEGORIES))));
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
