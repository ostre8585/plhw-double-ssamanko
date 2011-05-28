package com.poolc.pl.sensor.Listener;

import java.util.ArrayList;

import com.poolc.pl.sensor.dataType.OrientationDto;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationListener implements SensorEventListener {
	private static ArrayList<OrientationDto> orientationArrayList;

	public OrientationListener() {
		orientationArrayList = new ArrayList<OrientationDto>();
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
		orientationArrayList.add(oDto);
		
		
	}
	
	public ArrayList<OrientationDto> getOrientationArrayList() {
		ArrayList<OrientationDto> tempArrayList;
		synchronized (orientationArrayList) {
			tempArrayList = (ArrayList<OrientationDto>) orientationArrayList.clone();
			orientationArrayList.clear();
		}
		return tempArrayList;
	}

}
