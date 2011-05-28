package com.poolc.pl.sensor.Listener;

import java.util.ArrayList;

import com.poolc.pl.sensor.dataType.OrientationDto;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationListener implements SensorEventListener {
	private static OrientationDto orientationData;

	public OrientationListener() {

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
		
		orientationData = new OrientationDto(eventValues[0], eventValues[1], eventValues[2], timestamp);
		
		
		
	}
	
	public OrientationDto getOrientationData() {
		return orientationData.clone();
	}

}
