package com.poolc.pl.sensor.sensorControler;

import java.util.ArrayList;

import com.poolc.pl.sensor.Listener.MagneticFieldListener;
import com.poolc.pl.sensor.dataType.MagneticFieldDto;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class MagneticSensorControler extends SensorControler {

	private int delayRate;
	private SensorManager sm;
	private MagneticFieldListener magneticListenerData;
	private ArrayList<MagneticFieldDto> sdArrayList;
	
	public MagneticSensorControler(Context mContext, SensorManager sm, long delayTime) {
		super(mContext, delayTime);
		// TODO Auto-generated constructor stub
		delayRate = sm.SENSOR_DELAY_UI;
		this.sm = sm;
	}

	@Override
	public void startEmbedSensor() {
		// TODO Auto-generated method stub
		
		magneticListenerData = new MagneticFieldListener();
		sm.registerListener(magneticListenerData, sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), delayRate);
		
		sdArrayList = new ArrayList<MagneticFieldDto>();

	}
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {

				sdArrayList.add(magneticListenerData.getMagneticFieldData());
				this.sleep(delayTime);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	public ArrayList<MagneticFieldDto> getSdArrayList() {
		return sdArrayList;
		
	}
}
