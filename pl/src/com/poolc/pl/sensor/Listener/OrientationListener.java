package com.poolc.pl.sensor.Listener;

import java.util.Vector;

import com.poolc.pl.sensor.dataType.OrientationDto;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationListener implements SensorEventListener {
	private static Vector<OrientationDto> orientationVector;

	public OrientationListener() {
		orientationVector = new Vector<OrientationDto>();
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
		
		OrientationDto oDto = new OrientationDto(eventValues[0], eventValues[1], eventValues[2], timestamp);
		orientationVector.add(oDto);
		
		
	}
	
	public Vector<OrientationDto> getOrientationVector() {
		Vector<OrientationDto> tempVector;
		synchronized (orientationVector) {
			tempVector = (Vector<OrientationDto>) orientationVector.clone();
			orientationVector.removeAllElements();
		}
		return tempVector;
	}

}
