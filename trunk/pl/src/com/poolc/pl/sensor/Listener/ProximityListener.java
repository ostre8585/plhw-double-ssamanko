package com.poolc.pl.sensor.Listener;

import java.util.ArrayList;

import com.poolc.pl.sensor.dataType.ProximityDto;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ProximityListener implements SensorEventListener {
	private static ArrayList<ProximityDto> proximityArrayList;

	public ProximityListener() {
		proximityArrayList = new ArrayList<ProximityDto>();
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
		
		ProximityDto pDto = new ProximityDto(eventValues[0], timestamp);
		proximityArrayList.add(pDto);
		
	}
	
	public ArrayList<ProximityDto> getProximityArrayList() {
		ArrayList<ProximityDto> tempArrayList;
		synchronized (proximityArrayList) {
			tempArrayList = (ArrayList<ProximityDto>) proximityArrayList.clone();
			proximityArrayList.clear();
		}
		return tempArrayList;
	}
	

}
