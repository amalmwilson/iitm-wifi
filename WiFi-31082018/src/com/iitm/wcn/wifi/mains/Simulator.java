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
		Channel channel = new Channel();
		
		/* Initialization of simulation environment */
		apList = services.createAPs();
		services.printAPLocations(apList);
		
		ueList = services.createUsers(apList);
		services.printUELocations(ueList);
		
		/* Find neighbours of APs */
		services.findNeighbours(apList);
		
		for(AccessPoint ap: apList ) {
			ap.printNeighbours();
		}
		
		services.printAPLocations(apList);
		
		/* Association of users to APs */
		services.associateUsersToAPs(ueList, apList);
	
		/* end of initialization */
		
		/* simulation */
		
		/*for( long i = 0; i < (long)Params.SIM_DURATION * (long)Params.SLOTS_PER_SEC ; ++i ) {
			System.out.println(i);
		}*/
		 for( int t = 0; t < 1; t++ ) {
		        //    System.out.println(i);
			 System.out.println("Transmit Time");
			 for(AccessPoint ap: apList ) {
				 System.out.println(ap.getTransmitTime());
		            if(ap.getTransmitTime() == t){
		                for(AccessPoint n : ap.getNeighbours()){
		                    if(n.getBusy()==1) {
		                    ap.setTransmitTime(t+ap.getBackoffExponent());
		                    ap.setBackoffExponent();
		                    break;
		                    }
		                }    
		                if(ap.getTransmitTime()==t){
		                	ap.setBusy();
		                    System.out.println("////////////////"+ap.getId());
		                }
		            }
		        }
			 	/* set the channel free after the transmission is completed */
		        for(AccessPoint ap : apList) {
		        	if(ap.getTransmitTime()+ap.getTransmitDuration()==t)
		        	ap.setTransmitTime(t+ap.getBackoffExponent());
		        	ap.free();
		        }
		        }
		        
	}

}
