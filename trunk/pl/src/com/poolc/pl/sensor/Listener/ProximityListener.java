package com.poolc.pl.sensor.Listener;

import java.util.Vector;

import com.poolc.pl.sensor.dataType.ProximityDto;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ProximityListener implements SensorEventListener {
	private static Vector<ProximityDto> proximityVector;

	public ProximityListener() {
		proximityVector = new Vector<ProximityDto>();
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
		proximityVector.add(pDto);
		
	}
	
	public Vector<ProximityDto> getProximityVector() {
		Vector<ProximityDto> tempVector;
		synchronized (proximityVector) {
			tempVector = (Vector<ProximityDto>) proximityVector.clone();
			proximityVector.removeAllElements();
		}
		return tempVector;
	}
	

}
