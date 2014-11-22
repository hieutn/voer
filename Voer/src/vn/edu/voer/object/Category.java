package vn.edu.voer.object;

public class Category {
	private int id;
	private String name;
	private int parent;
	private String description;

	public Category() {
	}

	public Category(int id, String name, int parent, String description) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the parent
	 */
	public int getParent() {
		return parent;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
