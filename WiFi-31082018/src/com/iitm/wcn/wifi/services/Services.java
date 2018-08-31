package com.iitm.wcn.wifi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.iitm.wcn.wifi.entities.AccessPoint;
import com.iitm.wcn.wifi.entities.Location;
import com.iitm.wcn.wifi.entities.UserEquipment;
import com.iitm.wcn.wifi.params.Params;

public class Services {

	public static List<AccessPoint> createAPs() {
		List<AccessPoint> apList = new ArrayList<AccessPoint>();
		AccessPoint ap;
		Location loc;
		Random randX = new Random(Params.AP_SEED);
		Random randY = new Random(Params.AP_SEED+1);
		Random randT = new Random(5000);
		for( int i = 0; i < Params.NUM_APS; ++i) {
			int transmitTime = randT.nextInt(10);
			loc = new Location( randX.nextInt(Params.AREA),	randY.nextInt(Params.AREA));
			ap = new AccessPoint(i, loc,transmitTime);
			apList.add(ap);
		}
		return apList;
	}

	public static List<UserEquipment> createUsers(List<AccessPoint> apList) {
		List<UserEquipment> ueList = new ArrayList<UserEquipment>();
		UserEquipment ue;
		Location loc;
		Location apLoc;
		Random randR = new Random(Params.USER_SEED);
		Random randTheta = new Random(Params.USER_SEED + 1);
		double theta;
		int r;
			
		for( int i = 0; i < Params.NUM_APS; ++i) {
			apLoc = apList.get(i).getLoc();
			
			for( int j = 0; j < Params.USERS_PER_AP; ++j) {
				theta = (randTheta.nextInt(360)) * Math.PI / 180;
				r = randR.nextInt(Params.AP_RANGE);
				/* x = r * cos(theta)
				 * y = r * sin(theta)
				 */
				loc = new Location( apLoc.getX() + (int)Math.floor(r * Math.cos(theta)), apLoc.getY() + (int)Math.floor(r * Math.sin(theta)));
				if( loc.getX() < 0 || loc.getX() > Params.AREA || loc.getY() < 0  || loc.getY() > Params.AREA) {
					j--;
					continue;
				}
				
				ue = new UserEquipment(j, loc);
				
				
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

	/*
	 * find neighbours for all APs in the given list 
	*/
	public void findNeighbours(List<AccessPoint> apList) {
		for( AccessPoint apSrc: apList ) {
			for( AccessPoint apDst: apList ) {
				/* if within transmission range, add ap to neighbours list */
				if(!apSrc.equals(apDst)) {
					if(apSrc.getLoc().distanceTo(apDst.getLoc()) < Params.AP_RANGE) {
						apSrc.addToNeighbours(apDst);
						apDst.addToNeighbours(apSrc);
					}
				}
			}	
		}
	}

	
	public void associateUsersToAPs(List<UserEquipment> ueList, List<AccessPoint> apList) {
		AccessPoint ap;
		for( UserEquipment ue: ueList ) {
			ap = ue.searchAP(apList);
			ue.associateAP(ap);
			//ap.associateUE(ue);
		}
		
	}
}
