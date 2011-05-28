package com.poolc.pl.sensor.Listener;


import java.util.ArrayList;

import com.poolc.pl.sensor.dataType.LightDto;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class LightListener implements SensorEventListener {

	private static LightDto lightData;
	
	public LightListener() {
		
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
			return;
		}
		
		String timestamp = "" + event.timestamp;
		float[] eventValues = event.values;
		
		lightData = new LightDto(eventValues[0], timestamp);
		
		
		
	}
	
	public LightDto getLightData() {
		return lightData.clone();
	}
	

}
