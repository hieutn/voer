package vn.edu.voer.database;

import vn.edu.voer.R;
import vn.edu.voer.database.schema.MaterialDetailSchema;
import android.content.Context;

public class DBManager extends AbstractDatabaseManager {

	private final int DB_VERSION = 1;
	private Context mContext;

	public DBManager(Context ctx, boolean allowWrite) {
		super(ctx, allowWrite);
		mContext = ctx;
	}

	@Override
	public String getDatabaseName() {
		StringBuilder builder = new StringBuilder();
		builder.append(mContext.getResources().getString(R.string.db_name));
		return builder.toString();
	}

	@Override
	public int getDatabaseVersion() {
		return DB_VERSION;
	}

	@Override
	protected String[] getSQLCreateTables() {

		return new String[] { getSQLCreateTable(MaterialDetailSchema.TABLE_NAME, MaterialDetailSchema.params) };
	}

	@Override
	protected String[] getTableNames() {

		return new String[] { MaterialDetailSchema.TABLE_NAME };
	}

}
