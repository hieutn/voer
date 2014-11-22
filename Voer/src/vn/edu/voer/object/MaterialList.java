/**
 * 
 */
package vn.edu.voer.object;

import java.util.ArrayList;

/**
 * @author sidd
 *
 * Nov 21, 2014
 */
public class MaterialList {

	private int count;
	private String next;
	private String previous;
	private ArrayList<Material> results;
	
	/**
	 * @return the count materials
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @return the next link
	 */
	public String getNextLink() {
		return next;
	}

	/**
	 * @return the previous link
	 */
	public String getPreviousLink() {
		return previous;
	}

	/**
	 * @return The materials requested
	 */
	public ArrayList<Material> getMaterials() {
		return results;
	}
}
