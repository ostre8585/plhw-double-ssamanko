package com.poolc.pl.sensor.sensorControler;

import java.util.ArrayList;

import com.poolc.pl.sensor.Listener.OrientationListener;
import com.poolc.pl.sensor.dataType.OrientationDto;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class OrientationSensorControler extends SensorControler {
	private int delayRate;
	private SensorManager sm;
	private OrientationListener orientationListenerData;
	private ArrayList<OrientationDto> sdArrayList;
	
	public OrientationSensorControler(Context mContext, SensorManager sm, long delayTime) {
		super(mContext, delayTime);
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
