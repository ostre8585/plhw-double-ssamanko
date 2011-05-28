package com.poolc.pl.sensor.sensorControler;

import java.util.ArrayList;

import com.poolc.pl.sensor.Listener.GpsListener;
import com.poolc.pl.sensor.dataType.GpsDto;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;

public class GpsSensorControler extends SensorControler {

	private int delayRate;
	private LocationManager lm;
	private GpsListener gpsListener;
	private ArrayList<GpsDto> sdArrayList;
	
	public GpsSensorControler(Context mContext, LocationManager lm, long delayTime) {
		super(mContext, delayTime);
		this.lm = lm;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startEmbedSensor() {
		// TODO Auto-generated method stub
		gpsListener = new GpsListener();
		lm.requestLocationUpdates(lm.GPS_PROVIDER, delayRate, 0, gpsListener);
		sdArrayList = new ArrayList<GpsDto>();
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				sdArrayList.add(gpsListener.getGpsData());
				this.sleep(delayTime);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		} finally {
			
		}
	}
	
	public ArrayList<GpsDto> getSdArrayList() {
		return sdArrayList;
	}
	

}
