package com.iitm.wcn.wifi.entities;

import java.util.ArrayList;
import java.util.List;

import com.iitm.wcn.wifi.params.Params;

public class AccessPoint {
	private int id;
	public int getId() {
		return id;
	}

	private List<UserEquipment> associatedUEList;
	private List<AccessPoint> neighbours;
	private Channel ch;
	private Location loc;
	private int txPower;		// in mW
	private int transmitTime;
	private int busy;
	private int backoffExponent;
	private int transmitDuration;
	
	public int getBackoffExponent() {
		return backoffExponent;
	}

	public void setBackoffExponent() {
		this.backoffExponent *= 2;
	}

	public int getBusy() {
		return busy;
	}

	public void setBusy() {
		this.busy = 1;
	}
	public void free() {
		this.busy=0;
	}

	public List<AccessPoint> getNeighbours(){
		return this.neighbours;
	}
	
	public int getTransmitTime() {
		return transmitTime;
	}

	public void setTransmitTime(int transmitTime) {
		this.transmitTime = transmitTime;
	}

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
	
	public AccessPoint(int id, Location loc, int transmitTime) {
		associatedUEList = new ArrayList<UserEquipment>();
		neighbours = new ArrayList<AccessPoint>();
		this.loc = loc;
		this.id = id;
		this.txPower = Params.TX_POWER;
		this.transmitTime=transmitTime;
		this.busy=0;
		this.backoffExponent=1;
		this.setTransmitDuration(2);
	}

	/* add an accesspoint to the neighbours list */
	public void addToNeighbours(AccessPoint ap) {
		if( !this.neighbours.contains(ap) ) {
			this.neighbours.add(ap);
		}
	}
	
	/* set the channel of this accesspoint and it's neighbours as busy */
	public boolean setChannelAsBusy() {
		this.ch.setAsBusy();
		for(AccessPoint ap: neighbours) {
			ap.ch.setAsBusy();
		}
		return true;
	}

	/* set the channel of this accesspoint and it's neighbours as free */
	public boolean setChannelAsFree() {
		this.ch.setAsFree();
		for(AccessPoint ap: neighbours) {
			ap.ch.setAsFree();
		}
		return true;
	}

	/* list the id and location of neighbours 
	 * may be useful!
	 */
	public void printNeighbours() {
		System.out.println("Accesspoint " + this.id + " neighbour list");
		for(AccessPoint ap: neighbours) {
			System.out.println(ap.id + ":" + ap.getLoc());
		}
	}
	
	public void assoicateUE(UserEquipment ue) {
		this.associatedUEList.add(ue);
	}

	public double getTxPower() {
		return this.txPower;
	}

	public Channel getChannel() {
		return ch;
	}
	
	public boolean isChannelBusy() {
		return ch.isBusy();
	}

	public int getTransmitDuration() {
		return transmitDuration;
	}

	public void setTransmitDuration(int transmitDuration) {
		this.transmitDuration = transmitDuration;
	}

	
}
