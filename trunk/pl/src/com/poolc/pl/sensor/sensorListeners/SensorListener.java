package com.poolc.pl.sensor.sensorListeners;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.poolc.pl.sensor.dataType.AccelerometerDto;
import com.poolc.pl.sensor.dataType.GyroscopeDto;
import com.poolc.pl.sensor.dataType.LightDto;
import com.poolc.pl.sensor.dataType.MagneticFieldDto;
import com.poolc.pl.sensor.dataType.OrientationDto;
import com.poolc.pl.sensor.dataType.ProximityDto;

public class SensorListener implements SensorEventListener {
	private final float accelerometerAlpha = (float) 0.8;
	private static Vector<AccelerometerDto> accelerometerVector;
	private static Vector<MagneticFieldDto> magneticFieldVector;
	private static Vector<OrientationDto> orientationVector;
	private static Vector<GyroscopeDto> gyroscopeVecotr;
	private static Vector<LightDto> lightVector;
	private static Vector<ProximityDto> proximityVector;
	private int sensorType;

	public SensorListener(int sensorType) {
		this.sensorType = sensorType;
		if (accelerometerVector == null) {
			accelerometerVector = new Vector<AccelerometerDto>();
		}
		if (magneticFieldVector == null) {
			magneticFieldVector = new Vector<MagneticFieldDto>();
		}
		if (orientationVector == null) {
			orientationVector = new Vector<OrientationDto>();
		}
		if (gyroscopeVecotr == null) {
			gyroscopeVecotr = new Vector<GyroscopeDto>();
		}
		if (lightVector == null) {
			lightVector = new Vector<LightDto>();
		}
		if (proximityVector == null) {
			proximityVector = new Vector<ProximityDto>();
		}
	}

	public SensorListener() {
		accelerometerVector = new Vector<AccelerometerDto>();
		magneticFieldVector = new Vector<MagneticFieldDto>();
		orientationVector = new Vector<OrientationDto>();
		gyroscopeVecotr = new Vector<GyroscopeDto>();
		lightVector = new Vector<LightDto>();
		proximityVector = new Vector<ProximityDto>();

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
			return;
		}
		long tempTimeStamp = event.timestamp;
		Calendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
		String timestamp = "" + date.getTime();
		float[] eventValues = event.values;
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			AccelerometerDto fomerDto = accelerometerVector.size() == 0 ? new AccelerometerDto()
					: accelerometerVector.lastElement();

			float[] gravity = new float[3];
			float[] linearAcceleration = new float[3];

			gravity[0] = accelerometerAlpha * fomerDto.getxAxisGravity()
					+ (1 - accelerometerAlpha) * eventValues[0];
			gravity[1] = accelerometerAlpha * fomerDto.getyAxisGravity()
					+ (1 - accelerometerAlpha) * eventValues[1];
			gravity[2] = accelerometerAlpha * fomerDto.getzAxisGravity()
					+ (1 - accelerometerAlpha) * eventValues[2];

			linearAcceleration[0] = eventValues[0] - gravity[0];
			linearAcceleration[1] = eventValues[1] - gravity[1];
			linearAcceleration[2] = eventValues[2] - gravity[2];

			AccelerometerDto aDto = new AccelerometerDto(gravity[0],
					gravity[1], gravity[2], linearAcceleration[0],
					linearAcceleration[1], linearAcceleration[2], timestamp);
			accelerometerVector.add(aDto);
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			MagneticFieldDto mDto = new MagneticFieldDto(eventValues[0],
					eventValues[1], eventValues[2], timestamp);
			magneticFieldVector.add(mDto);
			break;
		case Sensor.TYPE_ORIENTATION:
			OrientationDto oDto = new OrientationDto(eventValues[0],
					eventValues[1], eventValues[2], timestamp);
			orientationVector.add(oDto);
			break;
		case Sensor.TYPE_GYROSCOPE:
			GyroscopeDto gyDto = new GyroscopeDto(eventValues[0],
					eventValues[1], eventValues[2], timestamp);
			gyroscopeVecotr.add(gyDto);
			break;
		case Sensor.TYPE_LIGHT:
			LightDto lDto = new LightDto(eventValues[0], timestamp);
			lightVector.add(lDto);
			break;
		case Sensor.TYPE_PROXIMITY:
			ProximityDto pDto = new ProximityDto(eventValues[0], timestamp);
			proximityVector.add(pDto);
			break;
		default:
			break;
		}
	}

	public static Vector<AccelerometerDto> getAccelerometerVector() {
		Vector<AccelerometerDto> tempVector;
		synchronized (accelerometerVector) {
			tempVector = (Vector<AccelerometerDto>) accelerometerVector.clone();
			accelerometerVector.removeAllElements();
		}
		return tempVector;
	}

	public static Vector<MagneticFieldDto> getMagneticFieldVector() {
		Vector<MagneticFieldDto> tempVector;
		synchronized (magneticFieldVector) {
			tempVector = (Vector<MagneticFieldDto>) magneticFieldVector.clone();
			magneticFieldVector.removeAllElements();
		}
		return tempVector;
	}

	public static Vector<OrientationDto> getOrientationVector() {
		Vector<OrientationDto> tempVector;
		synchronized (orientationVector) {
			tempVector = (Vector<OrientationDto>) orientationVector.clone();
			orientationVector.removeAllElements();
		}
		return tempVector;
	}

	public static Vector<GyroscopeDto> getGyroscopeVecotr() {
		Vector<GyroscopeDto> tempVector;
		synchronized (gyroscopeVecotr) {
			tempVector = (Vector<GyroscopeDto>) gyroscopeVecotr.clone();
			gyroscopeVecotr.removeAllElements();
		}
		return tempVector;
	}

	public static Vector<LightDto> getLightVector() {
		Vector<LightDto> tempVector;
		synchronized (lightVector) {
			tempVector = (Vector<LightDto>) lightVector.clone();
			lightVector.removeAllElements();
		}
		return tempVector;
	}

	public static Vector<ProximityDto> getProximityVector() {
		Vector<ProximityDto> tempVector;
		synchronized (proximityVector) {
			tempVector = (Vector<ProximityDto>) proximityVector.clone();
			proximityVector.removeAllElements();
		}
		return tempVector;
	}

	public int getSensorType() {
		return sensorType;
	}

}
