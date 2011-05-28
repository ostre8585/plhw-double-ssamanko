package com.poolc.pl.sensor.Listener;

import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.poolc.pl.sensor.dataType.MagneticFieldDto;



public class MagneticFieldListener implements SensorEventListener {

	private static ArrayList<MagneticFieldDto> magneticFieldArrayList;
	
	public MagneticFieldListener() {
		magneticFieldArrayList = new ArrayList<MagneticFieldDto>();
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
		
		MagneticFieldDto mDto = new MagneticFieldDto(eventValues[0], eventValues[1], eventValues[2], timestamp);
		magneticFieldArrayList.add(mDto);
		
	}
	
	public static ArrayList<MagneticFieldDto> getMagneticFieldArrayList() {
		ArrayList<MagneticFieldDto> tempArrayList;
		synchronized (magneticFieldArrayList) {
			tempArrayList = (ArrayList<MagneticFieldDto>) magneticFieldArrayList.clone();
			magneticFieldArrayList.clear();
			
		}
		return tempArrayList;
	}

}
