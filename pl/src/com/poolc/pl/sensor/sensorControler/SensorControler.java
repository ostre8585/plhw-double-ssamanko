package com.poolc.pl.sensor.sensorControler;

import java.util.Vector;

import android.hardware.SensorManager;

import com.poolc.pl.sensor.dataType.AccelerometerDto;

public abstract class SensorControler extends Thread implements Runnable{
	protected long delayTime = 0;
	public SensorControler( long delayTime) {
		this.delayTime = delayTime;
	}
	public abstract void startEmbedSensor();
}