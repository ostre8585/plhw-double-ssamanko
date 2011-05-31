package com.poolc.pl.service;

import com.poolc.pl.sensor.dataType.DataCommandDto;
import com.poolc.pl.sensor.sensorControler.*;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.IBinder;

public class SensorChecker extends Service{
	private AccelerometerSensorControler accelerometerSensorControler = null;
	private BatteryControler batteryControler = null;
	private GpsSensorControler gpsSensorControler = null;
	private GyroscopeSensorControler gyroscopeSensorControler = null;
	private LightSensorControler lightSensorControler = null;
	private MagneticSensorControler magneticSensorControler = null;
	private OrientationSensorControler orientationSensorControler = null;
	private ProximitySensorControler proximitySensorControler = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		startAccelerometerSensorCrawler(DataCommandDto.accelerometerDelayTime);
		startBatteryCrawler(DataCommandDto.batteryDelayTime);
		startGpsSensorCrawler(DataCommandDto.gpsDelayTime);
		startGyroscopeSensorCrawler(DataCommandDto.gyroscopeDelayTime);
		startLightSensorCrawler(DataCommandDto.lightDelayTime);
		startMagneticSensorCrawler(DataCommandDto.magneticFieldDelayTime);
		startOrientationSensorCrawler(DataCommandDto.orientationDelayTime);
		startProximitySensorCrawler(DataCommandDto.proximityDelayTime);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy(){
		
		endAccelerometerSensorCrawler();
		endBatteryCrawler();
		endGpsSensorCrawler();
		endGyroscopeSensorCrawler();
		endLightSensorCrawler();
		endMagneticSensorCrawler();
		endOrientationSensorCrawler();
		endProximitySensorCrawler();
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
	private void startBatteryCrawler(double batteryDelayTime) {
		if(batteryControler == null) {
			batteryControler = new BatteryControler(this, (long)(batteryDelayTime*1000));
		}
		batteryControler.start();
	}
	
	private void endBatteryCrawler() {
		if(batteryControler == null && !batteryControler.isAlive())
			return;
		batteryControler.interrupt();
	}
	private void startGpsSensorCrawler(double gpsDelayTime) {
		if(gpsSensorControler == null) {
			gpsSensorControler = new GpsSensorControler(this, (LocationManager)getSystemService(Context.LOCATION_SERVICE), (long) (gpsDelayTime*1000));
		}
		gpsSensorControler.start();
	}
	private void endGpsSensorCrawler() {
		if(gpsSensorControler == null && !gpsSensorControler.isAlive())
			return;
		gpsSensorControler.interrupt();
	}
	private void startGyroscopeSensorCrawler(double gyroscopeDelayTime) {
		if(gyroscopeSensorControler == null) {
			gyroscopeSensorControler = new GyroscopeSensorControler(this, (SensorManager)getSystemService(Context.SENSOR_SERVICE), (long)(gyroscopeDelayTime*1000));
		}
		gyroscopeSensorControler.start();
	}
	
	private void endGyroscopeSensorCrawler() {
		if(gyroscopeSensorControler == null && !gyroscopeSensorControler.isAlive())
			return;
		gyroscopeSensorControler.interrupt();
		
	}
	
	private void startLightSensorCrawler(double lightDelayTime) {
		if(lightSensorControler == null) {
			lightSensorControler = new LightSensorControler(this, (SensorManager)getSystemService(Context.SENSOR_SERVICE), (long)(lightDelayTime*1000));
		}
		lightSensorControler.start();
	}
	
	private void endLightSensorCrawler() {
		if(lightSensorControler == null && !lightSensorControler.isAlive())
			return;
		lightSensorControler.interrupt();
	}
	
	private void startMagneticSensorCrawler(double magneticDelayTime) {
		if(magneticSensorControler == null) {
			magneticSensorControler = new MagneticSensorControler(this, (SensorManager)getSystemService(Context.SENSOR_SERVICE), (long)(magneticDelayTime*1000));
		}
		magneticSensorControler.start();
	}
	
	private void endMagneticSensorCrawler() {
		if(magneticSensorControler == null && !magneticSensorControler.isAlive())
			return;
		magneticSensorControler.interrupt();
	}
	
	private void startOrientationSensorCrawler(double orientationDelayTime) {
		if(orientationSensorControler == null) {
			orientationSensorControler = new OrientationSensorControler(this, (SensorManager)getSystemService(Context.SENSOR_SERVICE), (long)(orientationDelayTime*1000));
		}
		orientationSensorControler.start();
	}
	
	private void endOrientationSensorCrawler() {
		if(orientationSensorControler == null && !orientationSensorControler.isAlive())
			return;
		orientationSensorControler.interrupt();
	}
	
	private void startProximitySensorCrawler(double proximityDelayTime) {
		if(proximitySensorControler == null) {
			proximitySensorControler = new ProximitySensorControler(this, (SensorManager)getSystemService(Context.SENSOR_SERVICE), (long)(proximityDelayTime*1000));
		}
		proximitySensorControler.start();
	}
	
	private void endProximitySensorCrawler() {
		if(proximitySensorControler == null && !proximitySensorControler.isAlive())
			return;
		proximitySensorControler.interrupt();
	}
	
}
