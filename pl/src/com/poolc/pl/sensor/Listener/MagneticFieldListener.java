package com.poolc.pl.sensor.Listener;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.poolc.pl.sensor.dataType.MagneticFieldDto;



public class MagneticFieldListener implements SensorEventListener {

	private static MagneticFieldDto magneticFieldData;
	private static SimpleDateFormat dateFormat;
	public MagneticFieldListener() {
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
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
		
		String timestamp = dateFormat.format(Calendar.getInstance().getTime());
		float[] eventValues = event.values;
		
		magneticFieldData = new MagneticFieldDto(eventValues[0], eventValues[1], eventValues[2], timestamp);
		
		
	}
	
	public static MagneticFieldDto getMagneticFieldData() {
		return magneticFieldData.clone();
	}

}
