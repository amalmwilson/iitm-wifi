package com.iitm.wcn.wifi.entities;

import java.util.ArrayList;
import java.util.List;

public class AccessPoint {
	private List<UserEquipment> associatedUEList;
	private Location loc;
	
	public void associateUE(UserEquipment ue) {
		associatedUEList.add(ue);
	}

	public List<UserEquipment> getAssociatedUEList() {
		return associatedUEList;
	}
	
	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public AccessPoint(Location loc) {
		associatedUEList = new ArrayList<UserEquipment>();
		this.loc = loc;
	}
	
	
}
