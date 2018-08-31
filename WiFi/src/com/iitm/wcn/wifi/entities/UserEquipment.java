package com.iitm.wcn.wifi.entities;

import java.util.List;

import com.iitm.wcn.wifi.params.Params;

public class UserEquipment {
	private int id;
	private Location loc;
	private AccessPoint associatedAP;
	private Channel ch;
	
	/* Constructor */
	public UserEquipment(int id, Location loc) {
		this.loc = loc;
		this.id = id;
	}
	
	public AccessPoint searchAP(List<AccessPoint> apList) {
		AccessPoint selectedAP = null;
		double snr;
		double maxSNR = 0;
		double pathLoss;
		double distance;
		double powerRcvd;
		double powerTx;
		
		for(AccessPoint ap: apList) {
			distance = this.loc.distanceTo(ap.getLoc());
			pathLoss = 0;	// in dBm
			powerTx = 10 * Math.log10(ap.getTxPower());		// in dBm
			powerRcvd = powerTx - pathLoss;	// in dBm
			snr = powerRcvd - Params.NOISE; // in dBm
			
			if( selectedAP != null && snr > maxSNR ) {
				selectedAP = ap;
				maxSNR = snr;
			}
		}
		return selectedAP;
	}
	
	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public void associateAP(AccessPoint ap) {
		this.associatedAP = ap;
		//this.ch = ap.getChannel();
	}

	public void requestData() {
		
	}
	
	public void senseChannel() {
		
	}
	
	public void requestChannel() {
		
	}
	
	public double distanceTo(AccessPoint ap) {
		return this.loc.distanceTo(ap.getLoc());
	}
	
	public double distanceTo(UserEquipment ue) {
		return this.loc.distanceTo(ue.getLoc());
	}

}