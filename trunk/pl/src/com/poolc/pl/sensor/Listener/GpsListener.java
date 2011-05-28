package com.poolc.pl.sensor.Listener;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.poolc.pl.sensor.dataType.GpsDto;


public class GpsListener implements LocationListener {

	private static GpsDto gpsData;
	private static SimpleDateFormat dateFormat;
	public GpsListener() {
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		String timestamp = dateFormat.format(Calendar.getInstance().getTime());
	
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
	
		gpsData = new GpsDto(latitude, longitude, timestamp);
			
	}
	
	public static GpsDto getGpsData() {
		return gpsData.clone();
	}
	

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
