package com.iitm.wcn.wifi.mains;

import java.util.Collections;
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
		
		/* Initialization of simulation environment */
		apList = services.createAPs();
		services.printAPLocations(apList);
		
		ueList = services.createUsers(apList);
		services.printUELocations(ueList);
		
		/* Find neighbours of APs */
		services.findNeighbours(apList);
		
		for( AccessPoint ap: apList ) {
			ap.printNeighbours();
		}
		
		/* Association of users to APs */
		services.associateUsersToAPs(ueList, apList);
	
		services.printUEAssociations(apList);
		
		/* end of initialization */
		
		/* simulation */
		
		/* simulation runs in steps of SIFS, because SIFS is the smallest unit */
		for( long time = 0; time < Params.SIM_DURATION; time += Params.SIFS ) {
			
			Collections.shuffle(apList);
			
			for(AccessPoint ap: apList ) {
				/* if this AP is scheduled to start at this time */
				if( ap.getTxStartTime() == time ) {
					// System.out.println(ap.getId() + " is scheduled at " + time);
					/* check whether the channel is busy */
					if( ap.isChannelBusy() ) {
						/* wait until the transmission finishes */
						ap.setTxStartTime( time + Params.SIFS );
						/* if AP is not getting the channel even after backing off, increase contention window and wait again */
						if( ap.isInBackoffMode() ) {
							ap.incCW();
							ap.setTxStartTime( time + Params.DIFS + ap.getBackoffTime());
						}
					}
					else if( ap.isInBackoffMode() == false ) {
						
						/* for the first time, if channel not busy wait for DIFS time */
						ap.setTxStartTime( time + Params.DIFS + ap.getBackoffTime());
						ap.putInBackoffMode();					
					}
					else {
						/* lock the channel */
						ap.setChannelAsBusy();
						ap.setAsScheduled(time);
						/* send data */
		            }
		        }
				/* otherwise check whether the station is in backoff mode */
				else if( ap.isInBackoffMode() ) {
					/* if the channel is busy then pause(increment) the backoff timer */
					if( ap.isChannelBusy() ) {
						ap.setTxStartTime( ap.getTxStartTime() + Params.SIFS );
						/* reset the channel idletimer */
						ap.resetIdleTimer();
					}
					else {
						/* if the channel is idle for DIFS then resume(stop incrementing) the backoff timer */
						ap.updateIdleTimer(Params.SIFS);
						if( ap.getIdleTimer() < Params.DIFS ) {
							ap.setTxStartTime( ap.getTxStartTime() + Params.SIFS );
						}
					}
				}

				
				/* decrement congestion window if channel is free */
				if( !ap.isChannelBusy() ) {
					ap.decCW();
					ap.updateIdleTimer(Params.SIFS);
				}
				else {
					ap.resetIdleTimer();
				}
								
				/* set the channel free after the data transmission is completed */
		        if( ap.getTxStartTime() + ap.getTxDuration() == time ) {
		        	ap.setAsCompleted(time);
		        	ap.setChannelAsFree();
		        }
	        }
		}
		
		/* Print average waiting time per AP */
		services.printAverageWaitingTimes(apList);

	}
}
