package com.iitm.wcn.wifi.entities;

public class Channel {
	private int id;
	private int busyCount;
	
	public Channel() {
		this.id = 1;
		this.busyCount = 0;
	}
	
	public Channel(int id) {
		this.id = id;
	}
	
	public boolean isBusy() {
		if( this.busyCount > 0 )
			return true;
		else
			return false;
	}
	
	public void setAsBusy() {
		this.busyCount++;
	}
	
	public void setAsFree() {
		this.busyCount--;
	}

}
