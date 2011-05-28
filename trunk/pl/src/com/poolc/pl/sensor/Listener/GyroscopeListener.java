package com.poolc.pl.sensor.Listener;

import java.util.EventListener;
import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.poolc.pl.sensor.dataType.GyroscopeDto;;

public class GyroscopeListener implements SensorEventListener {

	private static GyroscopeDto gyroscopeData;
	
	public GyroscopeListener() {
		
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
			return;
		}
		
		String timestamp = "" + event.timestamp;
		float[] eventValues = event.values;
		
		gyroscopeData = new GyroscopeDto(eventValues[0], eventValues[1], eventValues[2], timestamp);
		
		
	}
	
	public static GyroscopeDto getGyroscopeData() {
		return gyroscopeData.clone();
	}
	
	

}
