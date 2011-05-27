package com.poolc.pl.sensor.sensorControler;

import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.Vector;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.poolc.pl.sensor.dataType.AccelerometerDto;
import com.poolc.pl.sensorListeners.SensorListener;

public class sensorControlerIm extends SensorControler{
	private int delayRate;
	private SensorListener sensorListnerData;
	private Vector<AccelerometerDto> sdVector;
	
	public sensorControlerIm(SensorManager sm, long delayTime) {
		super(sm, delayTime);
		delayRate = sm.SENSOR_DELAY_UI;
		this.sm = sm;
	}

	public void startEmbedSensor(){
		Field[] fields = Sensor.class.getFields();
		String sensorName = "TYPE_ACCELEROMETER";
		for (Field field : fields) {
			if (field.getName().matches(sensorName)) {
				int sensorType = 0;
				try {
					sensorType = field.getInt(field);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SensorListener sensorListnerData = new SensorListener(sensorType);
				sm.registerListener(sensorListnerData,
						sm.getDefaultSensor(sensorType), delayRate);
			}
		}
		sdVector = new Vector<AccelerometerDto>();
	}	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(!Thread.currentThread().isInterrupted()){
				Vector<AccelerometerDto> tempVector = sensorListnerData.getAccelerometerVector();
				sdVector.addAll(tempVector);
				this.sleep(delayTime);
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			//종료시 처리.
		}
	}
	
	public Vector<AccelerometerDto> getSdVector(){
		return sdVector;
	}

}
