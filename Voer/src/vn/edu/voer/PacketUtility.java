package vn.edu.voer;

public class PacketUtility {

	/**
	 * Get package name
	 * 
	 * @return
	 */
	public String getPacketName() {
		return this.getClass().getPackage().getName();
	}
}
