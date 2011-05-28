package com.poolc.pl.sensor.sensorControler;

import java.util.ArrayList;

import com.poolc.pl.sensor.Listener.GyroscopeListener;
import com.poolc.pl.sensor.dataType.GyroscopeDto;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class GyroscopeSensorControler extends SensorControler {

	private int delayRate;
	private SensorManager sm;
	private GyroscopeListener gyroscopeListenerData;
	private ArrayList<GyroscopeDto> sdArrayList;
	
	public GyroscopeSensorControler(Context mContext, SensorManager sm, long delayTime) {
		super(mContext, delayTime);
		// TODO Auto-generated constructor stub
		delayRate = sm.SENSOR_DELAY_UI;
		this.sm = sm;
	}

	@Override
	public void startEmbedSensor() {
		// TODO Auto-generated method stub
		
		gyroscopeListenerData = new GyroscopeListener();
		sm.registerListener(gyroscopeListenerData, sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE), delayRate);
		
		sdArrayList = new ArrayList<GyroscopeDto>();

	}
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				sdArrayList.add(gyroscopeListenerData.getGyroscopeData());
				this.sleep(delayTime);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	public ArrayList<GyroscopeDto> getSdArrayList() {
		return sdArrayList;
		
	}


}
