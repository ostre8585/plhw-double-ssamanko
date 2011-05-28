package com.poolc.pl.sensor.sensorControler;

import java.util.ArrayList;

import com.poolc.pl.sensor.Listener.ProximityListener;
import com.poolc.pl.sensor.dataType.ProximityDto;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class ProximitySensorControler extends SensorControler {

	private int delayRate;
	private SensorManager sm;
	private ProximityListener proximityListenerData;
	private ArrayList<ProximityDto> sdArrayList;
	
	public ProximitySensorControler(Context mContext, SensorManager sm, long delayTime) {
		super(mContext, delayTime);
		// TODO Auto-generated constructor stub
		
		delayRate = sm.SENSOR_DELAY_UI;
		this.sm = sm;
	}

	@Override
	public void startEmbedSensor() {
		// TODO Auto-generated method stub
		proximityListenerData = new ProximityListener();
		sm.registerListener(proximityListenerData, sm.getDefaultSensor(Sensor.TYPE_PROXIMITY), delayRate);
		
		sdArrayList = new ArrayList<ProximityDto>();

	}
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				sdArrayList.add(proximityListenerData.getProximityData());
				this.sleep(delayTime);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	public ArrayList<ProximityDto> getSdArrayList() {
		return sdArrayList;
	}

}
