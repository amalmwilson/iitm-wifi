package com.iitm.wcn.wifi.entities;

import java.util.List;

public class UserEquipment {
	private Location loc;
	private List<AccessPoint> availableAPList;
	private AccessPoint associatedAP;
	
	/* Constructor */
	public UserEquipment(Location loc) {
		this.loc = loc;
	}
	
	public void scanAP() {
		assoicateAP();
	}
	
	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	private void assoicateAP() {
		
	}

	public void requestData() {
		
	}
	
	public void senseChannel() {
		
	}
	
	public void requestChannel() {
		
	}

}