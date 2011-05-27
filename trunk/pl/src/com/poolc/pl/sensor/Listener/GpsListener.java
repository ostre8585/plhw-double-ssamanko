package com.poolc.pl.sensor.Listener;

import java.util.Vector;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.poolc.pl.sensor.dataType.GpsDto;


public class GpsListener implements LocationListener {

	private static Vector<GpsDto> gpsVector;
	
	public GpsListener() {
		gpsVector = new Vector<GpsDto>();
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		String timestamp = "";
	
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
	
		GpsDto gDto = new GpsDto(latitude, longitude, timestamp);
		this.gpsVector.add(gDto);
			
	}
	
	public static Vector<GpsDto> getGpsVector() {
		Vector<GpsDto> tempVector;
		synchronized (gpsVector) {
			tempVector = (Vector<GpsDto>) gpsVector.clone();
			gpsVector.removeAllElements();
		}
		return tempVector;
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
