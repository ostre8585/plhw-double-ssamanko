package com.poolc.pl.sensor.sensorControler;


import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.poolc.pl.sensor.Listener.AccelerometerListener;
import com.poolc.pl.sensor.dataType.AccelerometerDto;
import com.poolc.pl.sensor.sensorControler.SensorControler;

public class AccelerometerSensorControler extends SensorControler {

	private int delayRate;
	private AccelerometerListener accelerometerListenerData;
	private ArrayList<AccelerometerDto> sdArrayList;
	
	public AccelerometerSensorControler(SensorManager sm, long delayTime) {
		super(sm, delayTime);
		delayRate = sm.SENSOR_DELAY_UI;
		this.sm = sm;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startEmbedSensor() {
		// TODO Auto-generated method stub
		
		accelerometerListenerData = new AccelerometerListener();
		sm.registerListener(accelerometerListenerData, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), delayRate);
		
		sdArrayList = new ArrayList<AccelerometerDto>();
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				ArrayList<AccelerometerDto> tempArrayList = accelerometerListenerData.getAccelerometerArrayList();
				sdArrayList.addAll(tempArrayList);
				this.sleep(delayTime);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public ArrayList<AccelerometerDto> getSdArrayList() {
		return sdArrayList;
	}
	
}


