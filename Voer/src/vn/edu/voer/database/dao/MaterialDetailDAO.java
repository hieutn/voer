/**
 * 
 */
package vn.edu.voer.database.dao;

import java.util.ArrayList;

import android.content.Context;
import vn.edu.voer.database.DbConnectionHelper;
import vn.edu.voer.database.schema.MaterialDetailSchema;
import vn.edu.voer.object.MaterialDetail;

/**
 * @author sidd
 *
 *         Nov 22, 2014
 */
public class MaterialDetailDAO {

	private DbConnectionHelper mDBHelper;

	public MaterialDetailDAO(Context ctx) {
		mDBHelper = DbConnectionHelper.getInstance(ctx);
	}

	/**
	 * 
	 * @param md
	 * @return
	 */
	public boolean insertMaterialDetail(MaterialDetail md) {
		String[] columns = MaterialDetailSchema.columns;
		Object[] values = { md.getMaterialID(), md.getTitle(), md.getDescription(), md.getText(), md.getLanguage(), md.getImage(), md.getMaterialType(), md.getModified(), md.getVersion(), md.getEditor(), md.getDerivedFrom(), md.getKeywords(), md.getLicenseID(), md.getAuthor(), md.getCategories() };
		long res = mDBHelper.insert(MaterialDetailSchema.TABLE_NAME, columns, values);
		if (res < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public ArrayList<MaterialDetail> getAllMaterialDetail() {
		return mDBHelper.getAllMaterialDetail();
	}
}
