package com.poolc.pl.sensor.Listener;

import java.util.ArrayList;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.poolc.pl.sensor.dataType.GpsDto;


public class GpsListener implements LocationListener {

	private static ArrayList<GpsDto> gpsArrayList;
	
	public GpsListener() {
		gpsArrayList = new ArrayList<GpsDto>();
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		String timestamp = "";
	
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
	
		GpsDto gDto = new GpsDto(latitude, longitude, timestamp);
		this.gpsArrayList.add(gDto);
			
	}
	
	public static ArrayList<GpsDto> getGpsArrayList() {
		ArrayList<GpsDto> tempArrayList;
		synchronized (gpsArrayList) {
			tempArrayList = (ArrayList<GpsDto>) gpsArrayList.clone();
			gpsArrayList.clear();
		}
		return tempArrayList;
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
