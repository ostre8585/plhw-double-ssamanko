package com.poolc.pl.service;

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
		intent.getParcelableArrayListExtra("dataCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy(){
		
	}
	
	private void startAccelerometerSensorCrawler(long delayTime){
		 accelerometerSensorControler = new AccelerometerSensorControler((SensorManager)getSystemService(Context.SENSOR_SERVICE), delayTime*1000);
	}

}
