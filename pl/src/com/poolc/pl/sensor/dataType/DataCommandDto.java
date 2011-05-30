package com.poolc.pl.sensor.dataType;

import android.os.Parcel;
import android.os.Parcelable;

public class DataCommandDto{
	public  static boolean accelerometerUsable;
	public  static boolean batteryUsable;
	public static boolean gpsUsable;
	public  static boolean gyroscopeUsable;
	public static boolean lightUsable;
	public static boolean magneticFieldUsable;
	public  static boolean orientationUsable;
	public  static boolean proximityUsable;
	
	public static boolean confirmSendServer;
	
	public static double accelerometerDelayTime = 0;
	public static double batteryDelayTime = 0;
	public static double gpsDelayTime = 0;
	public static double gyroscopeDelayTime = 0;
	public static double lightDelayTime = 0;
	public static double magneticFieldDelayTime = 0;
	public static double orientationDelayTime = 0;
	public static double proximityDelayTime = 0;
	
	public static double confirmSendServerPeriod = 0;
	
//	private static DataCommandDto dcDto= null;
	
	
//	
//	private DataCommandDto(){
//		
//	}
//	
//	public static DataCommandDto getInstance(){
//		if(dcDto == null){
//			synchronized (DataCommandDto.class) {
//				if(dcDto == null){
//					dcDto = new DataCommandDto();
//				}
//			}
//		}
//		return dcDto;
//	}
//	
//	public boolean isAccelerometerUsable() {
//		return accelerometerUsable;
//	}
}
