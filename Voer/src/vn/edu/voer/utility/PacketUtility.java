package vn.edu.voer.utility;

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
