/**
 * 
 */
package vn.edu.voer.object;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author sidd
 *
 *         Nov 22, 2014
 */
public class Material {
	public static final int TYPE_MODULE = 1;
	public static final int TYPE_COLLECTION = 2;

	private String description;
	private String language;
	private String title;
	private String text;
	private String image;
	private int material_type;
	private String modified;
	private String material_id;
	private int version;
	private String editor;
	private String derived_from;
	private String keywords;
	private String license_id;
	private String author;
	private String categories;

	public Material(String des, String lan, String title, String text, String image, int type, String modified,
			String id, int version, String editor, String derived, String keyword, String license, String author,
			String cat) {
		this.description = des;
		this.language = lan;
		this.title = title;
		this.text = text;
		this.image = image;
		this.material_type = type;
		this.modified = modified;
		this.material_id = id;
		this.version = version;
		this.editor = editor;
		this.derived_from = derived;
		this.keywords = keyword;
		this.license_id = license;
		this.author = author;
		this.categories = cat;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @return the material_type
	 */
	public int getMaterialType() {
		return material_type;
	}

	/**
	 * @return the modified
	 */
	public String getModified() {
		return modified;
	}

	/**
	 * @return the material_id
	 */
	public String getMaterialID() {
		return material_id;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @return the editor
	 */
	public String getEditor() {
		return editor;
	}

	/**
	 * @return the derived_from
	 */
	public String getDerivedFrom() {
		return derived_from;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @return the license_id
	 */
	public String getLicenseID() {
		return license_id;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the categories
	 */
	public String getCategories() {
		return categories;
	}

	/**
	 * Get list modules of Collection Type
	 */
	public ArrayList<CollectionContent> getCollectionContent() {
		ArrayList<CollectionContent> cc = null;
		if (material_type == TYPE_COLLECTION && text != null) {
			cc = new ArrayList<CollectionContent>();
			try {
				JSONObject obj = new JSONObject(text);
				JSONArray arr = obj.getJSONArray("content");
				for (int i = 0; i < arr.length(); i++) {
					obj = arr.getJSONObject(i);
					CollectionContent collectionContent = new CollectionContent(
							obj.getString("id"), 
							null,
							obj.getString("title"),
							obj.getString("type"),
							obj.getString("license"),
							obj.getString("url"), 
							obj.getInt("version"));
					cc.add(collectionContent);
				}
			} catch (JSONException e) {
				cc = null;
			}
		}
		
		return cc;
	}

}
