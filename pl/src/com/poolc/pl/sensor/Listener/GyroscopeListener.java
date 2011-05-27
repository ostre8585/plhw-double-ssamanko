package com.poolc.pl.sensor.Listener;

import java.util.EventListener;
import java.util.Vector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.poolc.pl.sensor.dataType.GyroscopeDto;;

public class GyroscopeListener implements SensorEventListener {

	private static Vector<GyroscopeDto> gyroscopeVector;
	
	public GyroscopeListener() {
		gyroscopeVector = new Vector<GyroscopeDto>();
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
		
		GyroscopeDto gDto = new GyroscopeDto(eventValues[0], eventValues[1], eventValues[2], timestamp);
		gyroscopeVector.add(gDto);
		
	}
	
	public static Vector<GyroscopeDto> getGyroscopeVector() {
		Vector<GyroscopeDto> tempVector;
		synchronized (gyroscopeVector) {
			tempVector = (Vector<GyroscopeDto>) gyroscopeVector.clone();
			gyroscopeVector.removeAllElements();
			
		}
		return tempVector;
	}
	
	

}