/**
 * 
 */
package vn.edu.voer.database.dao;

import java.util.ArrayList;

import android.content.Context;
import vn.edu.voer.database.DbConnectionHelper;
import vn.edu.voer.database.schema.MaterialSchema;
import vn.edu.voer.object.Material;

/**
 * @author sidd
 *
 *         Nov 22, 2014
 */
public class MaterialDAO {

	private DbConnectionHelper materialDBHelper;

	public MaterialDAO(Context ctx) {
		materialDBHelper = DbConnectionHelper.getInstance(ctx);
	}
	
	/**
	 * Check material downloaded to local database
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
	 * @param material
	 * @return
	 */
	public boolean insertMaterial(Material material) {
		String[] columns = MaterialSchema.columns;
		Object[] values = { 
				material.getMaterialID(), 
				material.getTitle(), 
				material.getDescription(), 
				material.getText(), 
				material.getLanguage(), 
				material.getImage(), 
				material.getMaterialType(), 
				material.getModified(),
				material.getVersion(), 
				material.getEditor(),
				material.getDerivedFrom(), 
				material.getKeywords(), 
				material.getLicenseID(), 
				material.getAuthor(),
				material.getCategories() };
		long res = materialDBHelper.insert(MaterialSchema.TABLE_NAME, columns, values);
		if (res < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Get all materials
	 * @return
	 */
	public ArrayList<Material> getAllMaterial() {
		return materialDBHelper.getMaterials("");
	}
	
	/**
	 * Get material by id
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
		ArrayList<Material> materials = materialDBHelper.getMaterials(condition.toString());
		if (materials != null && materials.size() > 0) {
			return materials.get(0);
		} else {
			return null;
		}
	}
}