package com.poolc.pl.sensor.Listener;

import java.util.ArrayList;

import com.poolc.pl.sensor.dataType.ProximityDto;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ProximityListener implements SensorEventListener {
	private static ProximityDto proximityData;

	public ProximityListener() {
		
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
		
		proximityData = new ProximityDto(eventValues[0], timestamp);
		
	}
	
	public ProximityDto getProximityData() {
		return proximityData.clone();
	}
	

}
