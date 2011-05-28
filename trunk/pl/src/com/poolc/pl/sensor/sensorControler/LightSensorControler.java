package com.poolc.pl.sensor.sensorControler;

import java.util.ArrayList;

import com.poolc.pl.sensor.Listener.LightListener;
import com.poolc.pl.sensor.dataType.LightDto;

import android.hardware.Sensor;
import android.hardware.SensorManager;

public class LightSensorControler extends SensorControler {


	private int delayRate;
	private SensorManager sm;
	private LightListener lightListenerData;
	private ArrayList<LightDto> sdArrayList;
	
	public LightSensorControler(SensorManager sm, long delayTime) {
		super(delayTime);
		// TODO Auto-generated constructor stub
		delayRate = sm.SENSOR_DELAY_UI;
		this.sm = sm;
	}

	@Override
	public void startEmbedSensor() {
		// TODO Auto-generated method stub
		
		lightListenerData = new LightListener();
		sm.registerListener(lightListenerData, sm.getDefaultSensor(Sensor.TYPE_LIGHT), delayRate);
		
		sdArrayList = new ArrayList<LightDto>();

	}
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				
				sdArrayList.add(lightListenerData.getLightData());
				this.sleep(delayTime);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	public ArrayList<LightDto> getSdArrayList() {
		return sdArrayList;
		
	}


}
