package com.iitm.wcn.wifi.params;

public class Params { 
	public static int AP_SEED = 3000;			// random number generation
	public static int USER_SEED = 4000;	// random number generation

	public static int AREA = 500;				// units: side in meters, 500m x 500m area
	public static int NUM_APS = 25; 			// one AP per 100m x 100m meter area

	public static int USERS_PER_AP = 20; 		// 20 users per AP

	public static String USER_DISTRIBUTION = "Uniform";
	public static String AP_DISRIBUTION = "Uniform";
	
	public static int TX_POWER = 100; 			// milliWatt or 10*log(P/1mW)dBm
	public static int NOISE = -90; 				// dBm
	public static int CH_BANDWIDTH = 20;		// Mega Hertz
	public static int AP_RANGE = 50;			// radius in meters
	
	public static int SIM_DURATION = 24 * 60 * 60;	// seconds
	public static int T_SLOT = 20;				// 20 micro second
	public static int SIFS = 10;				// 10 micro second
	public static int SLOTS_PER_SEC = (int)(1 / (SIFS * Math.pow(10, -6)));
}
