package com.poolc.pl.service;

import com.poolc.pl.sensor.sensorControler.AccelerometerSensorControler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

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
//		startAccelerometerSensorCrawler(delayTime)
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy(){
		
	}
	
	private void startAccelerometerSensorCrawler(long delayTime){
		if(accelerometerSensorControler == null){
			accelerometerSensorControler = new AccelerometerSensorControler(this, (SensorManager)getSystemService(Context.SENSOR_SERVICE), delayTime*1000);
		}
		 accelerometerSensorControler.start();
	}
	
	private void endAccelerometerSensorCrawler(){
		if(accelerometerSensorControler == null && !accelerometerSensorControler.isAlive())
			return;
		accelerometerSensorControler.interrupt();
	}
	
	
}
