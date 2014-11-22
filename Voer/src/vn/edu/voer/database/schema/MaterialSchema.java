/**
 * 
 */
package vn.edu.voer.database.schema;

import vn.edu.voer.database.AbstractDatabaseManager;
import vn.edu.voer.database.AbstractDatabaseManager.SQLPair;;

/**
 * @author sidd
 *
 * Nov 22, 2014
 */
public class MaterialSchema {
	
	public static final String TABLE_NAME = "Material";
	
	public static final String ID = "material_id";
	public static final String DESCRIPTION = "description";
	public static final String LANGUAGE = "language";
	public static final String TITLE = "title";
	public static final String TEXT = "text";
	public static final String IMAGE = "image";
	public static final String TYPE = "material_type";
	public static final String MODIFIED = "modified";
	public static final String VERSION = "version";
	public static final String EDITOR = "editor";
	public static final String DERIVED_FROM = "derived_from";
	public static final String KEYWORD = "keywords";
	public static final String LICENSE_ID = "license_id";
	public static final String AUTHOR = "author";
	public static final String CATEGORIES = "categories";
	
	public static final String[] columns = {ID, TITLE, DESCRIPTION, TEXT, LANGUAGE, IMAGE, TYPE, MODIFIED, VERSION, EDITOR, DERIVED_FROM, KEYWORD, LICENSE_ID, AUTHOR, CATEGORIES};
	
	public static final AbstractDatabaseManager.SQLPair[] params = new SQLPair[] {
		new SQLPair(ID, "VARCHAR PRIMARY KEY NOT NULL"),
		new SQLPair(DESCRIPTION, "TEXT"),
		new SQLPair(LANGUAGE, "VARCHAR"),
		new SQLPair(TITLE, "VARCHAR"),
		new SQLPair(TEXT, "TEXT"),
		new SQLPair(IMAGE, "VARCHAR"),
		new SQLPair(TYPE, "VARCHAR"),
		new SQLPair(MODIFIED, "VARCHAR"),
		new SQLPair(VERSION, "INTEGER"),
		new SQLPair(EDITOR, "VARCHAR"),
		new SQLPair(DERIVED_FROM, "VARCHAR"),
		new SQLPair(KEYWORD, "VARCHAR"),
		new SQLPair(LICENSE_ID, "VARCHAR"),
		new SQLPair(AUTHOR, "VARCHAR"),
		new SQLPair(CATEGORIES, "VARCHAR")
	};
}