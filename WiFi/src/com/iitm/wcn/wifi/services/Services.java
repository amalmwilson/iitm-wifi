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
		
		for( int i = 0; i < Params.NUM_APS; ++i) {
			loc = new Location( randX.nextInt(Params.AREA),
								randY.nextInt(Params.AREA));
			ap = new AccessPoint(loc);
			apList.add(ap);
		}
		return apList;
	}

	public static List<UserEquipment> createUsers(List<AccessPoint> apList) {
		List<UserEquipment> ueList = new ArrayList<UserEquipment>();
		UserEquipment ue;
		Location loc;
		Location apLoc;
		Random randX = new Random(Params.AP_SEED);
		Random randY = new Random(Params.AP_SEED+1);
			
		for( int i = 0; i < Params.NUM_APS; ++i) {
			apLoc = apList.get(i).getLoc();
			
			for( int j = 0; j < Params.USERS_PER_AP; ++j) {
				loc = new Location( apLoc.getX() + Params.AP_RANGE - randX.nextInt(2 * Params.AP_RANGE),
								apLoc.getY() + Params.AP_RANGE - randY.nextInt(2 * Params.AP_RANGE));
				if( loc.getX() < 0 || loc.getX() > Params.AREA || loc.getY() < 0  || loc.getY() > Params.AREA) {
					j--;
					continue;
				}
				
				ue = new UserEquipment(loc);
				
				
				ueList.add(ue);
			}
		}
		return ueList;
	}
	
	public static void printAPLocations(List<AccessPoint> apList) {
		System.out.println("Locatons of access points");
		for(AccessPoint ap : apList) {
			System.out.println(ap.getLoc());
		}
	}
	
	public void printUELocations(List<UserEquipment> ueList) {
		System.out.println("Locatons of users");
		for(UserEquipment ue : ueList) {
			System.out.println(ue.getLoc());
		}
	}
}
