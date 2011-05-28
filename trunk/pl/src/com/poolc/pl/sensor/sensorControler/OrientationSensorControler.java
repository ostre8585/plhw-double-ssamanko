package com.poolc.pl.sensor.sensorControler;

import java.util.ArrayList;

import com.poolc.pl.sensor.Listener.OrientationListener;
import com.poolc.pl.sensor.dataType.OrientationDto;

import android.hardware.Sensor;
import android.hardware.SensorManager;

public class OrientationSensorControler extends SensorControler {
	private int delayRate;
	private OrientationListener orientationListenerData;
	private ArrayList<OrientationDto> sdArrayList;
	
	public OrientationSensorControler(SensorManager sm, long delayTime) {
		super(sm, delayTime);
		// TODO Auto-generated constructor stub
		delayRate = sm.SENSOR_DELAY_UI;
		this.sm = sm;
	}

	@Override
	public void startEmbedSensor() {
		// TODO Auto-generated method stub
		
		orientationListenerData = new OrientationListener();
		sm.registerListener(orientationListenerData, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), delayRate);
		
		sdArrayList = new ArrayList<OrientationDto>();

	}
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				sdArrayList.add(orientationListenerData.getOrientationData());
				this.sleep(delayTime);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	public ArrayList<OrientationDto> getSdArrayList() {
		return sdArrayList;
		
	}

}
