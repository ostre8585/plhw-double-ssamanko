package com.poolc.pl.sensor.Listener;

import java.util.Vector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.poolc.pl.sensor.dataType.MagneticFieldDto;



public class MagneticFieldListener implements SensorEventListener {

	private static Vector<MagneticFieldDto> magneticFieldVector;
	
	public MagneticFieldListener() {
		magneticFieldVector = new Vector<MagneticFieldDto>();
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
		magneticFieldVector.add(mDto);
		
	}
	
	public static Vector<MagneticFieldDto> getMagneticFieldVector() {
		Vector<MagneticFieldDto> tempVector;
		synchronized (magneticFieldVector) {
			tempVector = (Vector<MagneticFieldDto>) magneticFieldVector.clone();
			magneticFieldVector.removeAllElements();
			
		}
		return tempVector;
	}

}
