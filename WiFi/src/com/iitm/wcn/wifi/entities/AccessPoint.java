package com.iitm.wcn.wifi.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.iitm.wcn.wifi.params.Params;

public class AccessPoint {
	private int id;
	Random randTime;
	Random randDur;

	private List<UserEquipment> associatedUEList;
	private List<AccessPoint> neighbours;
	private Channel ch;
	private Location loc;
	private int txPower;		// in mW
	private long txStartTime;	// in 10 microsecond slots
	private int backoffTime;
	private boolean backoffStatus;
	private int txDuration;
	private double waitingTime;
	private int scheduledCount;
	private long scheduledTime;
	private int cwSize;
	
	public int getCwSize() {
		return cwSize;
	}

	public int getId() {
		return id;
	}

	public int getBackoffTime() {
		this.backoffTime = Params.SIFS + (int)(Math.random() * (this.cwSize - Params.SIFS) / Params.SIFS) * Params.SIFS ;
		return this.backoffTime;
	}
	
	public void incCW() {
		if( this.cwSize * 2 > Params.CW_MAX)
			this.cwSize = Params.CW_MAX;
		else
			this.cwSize *= 2;
	}
	
	public void decCW() {
		if( (this.cwSize - Params.SIFS) < Params.CW_MIN )
			this.cwSize = Params.CW_MIN;
		else
			this.cwSize -= Params.SIFS;
	}
	
	public List<AccessPoint> getNeighbours(){
		return this.neighbours;
	}
	
	public long getTxStartTime() {
		return txStartTime;
	}

	public void setTxStartTime(long time) {
		this.txStartTime = time;
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

	
	public AccessPoint(int id, Location loc, long txStartTime, int txDuration, long seed) {
		associatedUEList = new ArrayList<UserEquipment>();
		neighbours = new ArrayList<AccessPoint>();
		this.loc = loc;
		this.id = id;
		this.txPower = Params.TX_POWER;
		this.txStartTime = txStartTime;
		this.scheduledTime = txStartTime;
		this.txDuration = txDuration;
		this.backoffTime = 0;
		this.ch = new Channel();
		this.backoffStatus = false;
		this.randTime = new Random();
		this.randDur = new Random();
		this.scheduledCount = 0;
		this.waitingTime = 0;
		this.cwSize = Params.CW_MIN;
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

	/* list the id and location of neighbors 
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

	public int getTxDuration() {
		return txDuration;
	}

	public void setTxDuration(int txDuration) {
		this.txDuration = txDuration;
	}

	/* Methods to find distances to other equipments */
	public double distanceTo(AccessPoint ap) {
		return this.loc.distanceTo(ap.getLoc());
	}
	
	public double distanceTo(UserEquipment ue) {
		return this.loc.distanceTo(ue.getLoc());
	}

	public void setAsScheduled( long time ) {
		this.scheduledCount++;
		this.waitingTime += time - this.scheduledTime;
		this.backoffStatus = false;
	}
	
	/* reset all parameters and find a new schedule */
	public void setAsCompleted(long time) {
		this.txStartTime = time + Params.SIFS + (long)(randTime.nextDouble() * Params.AP_SCHEDULE_TIMEFRAME / Params.SIFS) * Params.SIFS;
		do{
			this.txDuration = Params.MIN_TX_SLOTS + (int)(randTime.nextDouble() * (Params.MAX_TX_SLOTS - Params.MIN_TX_SLOTS) ) * Params.SIFS;
		} while(this.txDuration == 0);
		
		this.backoffStatus = false;
		this.scheduledTime = this.txStartTime;
	}

	public boolean isInBackoffMode() {
		return backoffStatus;
	}

	public void putInBackoffMode() {
		this.backoffStatus = true;
	}
	
	public void printAssoicatedUEs() {
		System.out.println("Location of AP = " + this.loc);
		System.out.println("No. or UEs = " + this.associatedUEList.size());
		for(UserEquipment ue: this.associatedUEList) {
			System.out.println( ue.getId() + ": " + ue.getLoc());
		}
	}
	
	public double getAverageWaitTime() {
		if( this.scheduledCount > 0 )
			return (this.waitingTime/this.scheduledCount);
		else if( this.scheduledTime > Params.SIM_DURATION )
			return 0;
		else
			return Params.SIM_DURATION - this.scheduledTime;
	}

	public void resetIdleTimer() {
		this.ch.resetIdleTimer();
		for( AccessPoint ap: this.neighbours ) {
			ap.getChannel().resetIdleTimer();
		}
		
	}

	public void updateIdleTimer(int time) {
		this.ch.updateIdleTimer(time);
		for( AccessPoint ap: this.neighbours ) {
			ap.getChannel().updateIdleTimer(time);
		}
	}

	public int getIdleTimer() {
		return this.ch.getIdleTimer();
	}
}
