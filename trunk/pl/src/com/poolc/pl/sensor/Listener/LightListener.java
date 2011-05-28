package com.poolc.pl.sensor.Listener;


import java.util.ArrayList;

import com.poolc.pl.sensor.dataType.LightDto;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class LightListener implements SensorEventListener {

	private static ArrayList<LightDto> lightArrayList;
	
	public LightListener() {
		lightArrayList = new ArrayList<LightDto>();
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
		
		LightDto lDto = new LightDto(eventValues[0], timestamp);
		lightArrayList.add(lDto);
		
		
	}
	
	public ArrayList<LightDto> getLightArrayList() {
		ArrayList<LightDto> tempArrayList;
		synchronized (lightArrayList) {
			tempArrayList = (ArrayList<LightDto>) lightArrayList.clone();
			lightArrayList.clear();
		}
		return tempArrayList;
	}
	

}
