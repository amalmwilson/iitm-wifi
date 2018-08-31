package com.iitm.wcn.wifi.mains;

import java.util.List;

import com.iitm.wcn.wifi.entities.AccessPoint;
import com.iitm.wcn.wifi.entities.Channel;
import com.iitm.wcn.wifi.entities.UserEquipment;
import com.iitm.wcn.wifi.params.Params;
import com.iitm.wcn.wifi.services.Services;

public class Simulator {
	private static List<AccessPoint> apList;
	private static List<UserEquipment> ueList;
	
	
	public static void main(String[] args) {
		Services services = new Services();
		Params.SIM_DURATION = 2000;
		
		/* Initialization of simulation environment */
		apList = services.createAPs();
		//services.printAPLocations(apList);
		
		ueList = services.createUsers(apList);
		//services.printUELocations(ueList);
		
		/* Find neighbours of APs */
		services.findNeighbours(apList);
		
		for(AccessPoint ap: apList ) {
			ap.printNeighbours();
		}
		
		services.printAPSchedule(apList);
		
		/* Association of users to APs */
		services.associateUsersToAPs(ueList, apList);
	
		/* end of initialization */
		
		/* simulation */
		/* simulation runs in steps of SIFS, because SIFS is the smallest unit */
		for( long time = 0; time < Params.SIM_DURATION; time += Params.SIFS ) {
			System.out.println("Timeslot " + time);
			
			for(AccessPoint ap: apList ) {
				/* if this AP is scheduled to start at this time */
				if(ap.getTxStartTime() == time) {
					/* check whether the channel is busy */
					if( ap.isChannelBusy() ) {
						/* if busy backoff for some time */
						ap.setTxStartTime( time + ap.getBackoffTime() );
		                ap.updateBackoffTime();
		                break;
					}
					else if( ap.waitedDIFS() == false ) {
						/* if channel not busy wait for DIFS time */
						ap.setTxStartTime( ap.getTxStartTime() + Params.DIFS);
						ap.waitForDIFS();						
					}
					else {
						/* lock the channel */
						System.out.println("AP " + ap.getId() + " got scheduled");
						ap.setChannelAsBusy();
						/* send data */
		            }
		        }
				/* 
				
				/* set the channel free after the data transmission is completed */
		        if( ap.getTxStartTime() + ap.getTxDuration() + Params.SIFS == time ) {
		        	ap.setAsCompleted(time);
		        	ap.setChannelAsFree();
		        }
	        }
		}    
	}

}
