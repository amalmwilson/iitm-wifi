package com.iitm.wcn.wifi.mains;

import java.util.List;

import com.iitm.wcn.wifi.entities.AccessPoint;
import com.iitm.wcn.wifi.entities.UserEquipment;
import com.iitm.wcn.wifi.services.Services;

public class Simulator {
	private static List<AccessPoint> apList;
	private static List<UserEquipment> ueList;
	
	
	public static void main(String[] args) {
		Services services = new Services();
		
		/* Create entities */
		apList = services.createAP();
		services.printAPLocations(apList);
		
		ueList = services.createUsers();
	}

}
