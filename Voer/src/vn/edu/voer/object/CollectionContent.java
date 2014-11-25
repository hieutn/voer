/**
 * 
 */
package vn.edu.voer.object;

import java.util.ArrayList;

/**
 * @author sidd
 *
 *         Nov 22, 2014
 */
public class CollectionContent {

	private String id;
	private ArrayList<String> authors;
	private String title;
	private String type;
	private String license;
	private String url;
	private int version;

	public CollectionContent(String id, ArrayList<String> authors, String title, String type, String license,
			String url, int version) {
		this.id = id;
		this.authors = authors;
		this.title = title;
		this.type = type;
		this.license = license;
		this.url = url;
		this.version = version;
	}

	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the authors
	 */
	public ArrayList<String> getAuthors() {
		return authors;
	}

}
