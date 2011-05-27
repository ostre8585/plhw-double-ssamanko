package com.poolc.pl.sensor.Listener;

import java.util.Vector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.poolc.pl.sensor.dataType.AccelerometerDto;;

public class AccelerometerListener implements SensorEventListener{
	private static Vector<AccelerometerDto> accelerometerVector;

	public AccelerometerListener() {
		accelerometerVector = new Vector<AccelerometerDto> ();
	}
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
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
		
//		AccelerometerDto fomerDto = accelerometerVector.size() == 0?new AccelerometerDto():accelerometerVector.lastElement();
//		
//		float[] gravity = new float[3];
//		float[] linearAcceleration = new float[3]; 
//		
//		gravity[0] = accelerometerAlpha * fomerDto.getxAxisGravity() + (1 - accelerometerAlpha) * eventValues[0];
//		gravity[1] = accelerometerAlpha * fomerDto.getyAxisGravity() + (1 - accelerometerAlpha) * eventValues[1];
//		gravity[2] = accelerometerAlpha * fomerDto.getzAxisGravity() + (1 - accelerometerAlpha) * eventValues[2];
//		
//		linearAcceleration[0] = eventValues[0] - gravity[0];
//		linearAcceleration[1] = eventValues[1] - gravity[1];
//		linearAcceleration[2] = eventValues[2] - gravity[2];
//		
//		AccelerometerDto aDto = new AccelerometerDto(gravity[0], gravity[1], gravity[2], linearAcceleration[0], linearAcceleration[1], 
//				linearAcceleration[2], timestamp);
		
		AccelerometerDto aDto = new AccelerometerDto(eventValues[0], eventValues[1], eventValues[2], timestamp);
		accelerometerVector.add(aDto);
		
	}
	
	public static Vector<AccelerometerDto> getAccelerometerVector() {
		Vector<AccelerometerDto> tempVector;
		synchronized (accelerometerVector) {
			tempVector = (Vector<AccelerometerDto>) accelerometerVector.clone();
			accelerometerVector.removeAllElements();
			
		}
		return tempVector;
	}
	

}