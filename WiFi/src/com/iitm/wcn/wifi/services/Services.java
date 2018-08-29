package com.iitm.wcn.wifi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.iitm.wcn.wifi.entities.AccessPoint;
import com.iitm.wcn.wifi.entities.Location;
import com.iitm.wcn.wifi.entities.UserEquipment;
import com.iitm.wcn.wifi.params.Params;

public class Services {

	public static List<AccessPoint> createAP() {
		List<AccessPoint> apList = new ArrayList<AccessPoint>();
		AccessPoint ap;
		Location loc;
		Random randX = new Random(Params.AP_SEED);
		Random randY = new Random(Params.AP_SEED+1);
		
		for( int i = 1; i < Params.NUM_APS; ++i) {
			loc = new Location( randX.nextInt(Params.AREA),
								randY.nextInt(Params.AREA));
			ap = new AccessPoint(loc);
			apList.add(ap);
		}
		return apList;
	}

	public static List<UserEquipment> createUsers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void printAPLocations(List<AccessPoint> apList) {
		System.out.println("Locatons of access points");
		for(AccessPoint ap : apList) {
			System.out.println(ap.getLoc());
		}
	}
	
	public void printUserLocations(List<UserEquipment> ueList) {
		System.out.println("Locatons of access points");
		for(UserEquipment ue : ueList) {
			System.out.println(ue.getLoc());
		}
	}
}
