package com.poolc.pl.service;

import com.poolc.pl.sensor.dataType.DataCommandDto;
import com.poolc.pl.sensor.sensorControler.AccelerometerSensorControler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.IBinder;

public class SensorChecker extends Service{
	private AccelerometerSensorControler accelerometerSensorControler = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		startAccelerometerSensorCrawler(DataCommandDto.accelerometerDelayTime);
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy(){
		
		endAccelerometerSensorCrawler();
	}
	
	private void startAccelerometerSensorCrawler(double accelerometerDelayTime){
		if(accelerometerSensorControler == null){
			accelerometerSensorControler = new AccelerometerSensorControler(this, (SensorManager)getSystemService(Context.SENSOR_SERVICE), (long)(accelerometerDelayTime*1000));
		}
		 accelerometerSensorControler.start();
	}
	
	private void endAccelerometerSensorCrawler(){
		if(accelerometerSensorControler == null && !accelerometerSensorControler.isAlive())
			return;
		accelerometerSensorControler.interrupt();
	}
}
