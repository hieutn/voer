/**
 * 
 */
package vn.edu.voer.database.dao;

import java.util.ArrayList;

import vn.edu.voer.database.DbConnectionHelper;
import vn.edu.voer.database.schema.MaterialSchema;
import vn.edu.voer.object.Material;
import vn.edu.voer.utility.DateTimeHelper;
import android.content.Context;

/**
 * @author sidd
 *
 *         Nov 22, 2014
 */
public class MaterialDAO {

	private DbConnectionHelper mDBHelper;

	public MaterialDAO(Context ctx) {
		mDBHelper = DbConnectionHelper.getInstance(ctx);
	}
	
	public boolean updateReaded(String id) {
		StringBuilder where = new StringBuilder();
		where.append(MaterialSchema.ID);
		where.append("='");
		where.append(id);
		where.append("'");
		String columns[] = { MaterialSchema.IS_READ };
		String values[] = { "1" };
		long res = mDBHelper.update(MaterialSchema.TABLE_NAME, columns, values, where.toString());
		if (res < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean updateAttachFile(String id, String attach) {
		StringBuilder where = new StringBuilder();
		where.append(MaterialSchema.ID);
		where.append("='");
		where.append(id);
		where.append("'");
		String columns[] = { MaterialSchema.ATTACH_FILE };
		String values[] = { attach };
		long res = mDBHelper.update(MaterialSchema.TABLE_NAME, columns, values, where.toString());
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
	public boolean deleteMaterial(String id) {
		StringBuilder where = new StringBuilder();
		where.append(MaterialSchema.ID);
		where.append("='");
		where.append(id);
		where.append("'");
		long res = mDBHelper.delete(MaterialSchema.TABLE_NAME, where.toString());
		if (res < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check material downloaded to local database
	 * 
	 * @param id
	 * @return
	 */
	public boolean isDownloadedMaterial(String id) {
		if (getMaterialById(id) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Insert material from service to database local
	 * 
	 * @param material
	 * @return
	 */
	public boolean insertMaterial(Material material) {
		return insertMaterial(material, false);
	}

	/**
	 * 
	 * @param material
	 * @return
	 */
	public boolean insertSubMaterial(Material material) {
		return insertMaterial(material, true);
	}

	/**
	 * 
	 * @param material
	 * @param isSubMaterial
	 * @return
	 */
	private boolean insertMaterial(Material material, boolean isSubMaterial) {
		try {
			String[] columns = MaterialSchema.columns;
			Object[] values = { material.getMaterialID(), material.getTitle(), material.getDescription(),
					material.getText(), material.getLanguage(), material.getImage(), material.getMaterialType(),
					material.getModified(), material.getVersion(), material.getEditor(), material.getDerivedFrom(),
					material.getKeywords(), material.getLicenseID(), material.getAuthor(), material.getCategories(),
					DateTimeHelper.getCurrentDateTime(), isSubMaterial ? 1 : 0, "", 0 };
			long res = mDBHelper.insert(MaterialSchema.TABLE_NAME, columns, values);
			if (res < 0) {
				return false;
			} else {
				return true;
			}
		} catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * Get all materials
	 * 
	 * @return
	 */
	public ArrayList<Material> getAllMaterial() {
		StringBuilder condition = new StringBuilder();
		condition.append(MaterialSchema.SUB_MATERIAL);
		condition.append("=");
		condition.append("0");

		StringBuilder orderBy = new StringBuilder();
		orderBy.append(MaterialSchema.DATE_CREATED);
		orderBy.append(" ");
		orderBy.append("DESC");
		return mDBHelper.getMaterials(condition.toString(), orderBy.toString());
	}

	/**
	 * Get material by id
	 * 
	 * @param id
	 * @return
	 */
	public Material getMaterialById(String id) {
		StringBuilder condition = new StringBuilder();
		condition.append(MaterialSchema.ID);
		condition.append("=");
		condition.append("'");
		condition.append(id);
		condition.append("'");
		ArrayList<Material> materials = mDBHelper.getMaterials(condition.toString(), "");
		if (materials != null && materials.size() > 0) {
			return materials.get(0);
		} else {
			return null;
		}
	}
}
