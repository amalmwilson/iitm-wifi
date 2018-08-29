package com.iitm.wcn.wifi.params;

public class Params { 
	public static int AREA = 100;				// units: side in meters
	public static int NUM_APS = 25; 			/* one AP per 20 x 20 meter area
													No. of AP's = Total area/400
												*/
	public static int NUM_USERS = 1250; 		// 50 users per AP
	public static int AP_SEED = 2000;	
	public static int WIFI_USER_SEED = 4000;
	public static String USER_DISRIBUTION = "Uniform";
	public static String AP_DISRIBUTION = "Uniform";
	public static int TX_POWER = 100; 			// units: Watt
	public static int NOISE = 84; 				// units: dB
	public static int CH_BANDWIDTH = 20;		// Mega Hertz
}
