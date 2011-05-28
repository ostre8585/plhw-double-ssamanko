package com.poolc.pl.sensor.sensorControler;

import java.util.Vector;

import android.content.Context;
import android.hardware.SensorManager;

import com.poolc.pl.sensor.dataType.AccelerometerDto;
import com.poolc.pl.sensor.dataType.*;

public abstract class SensorControler extends Thread implements Runnable{
	protected long delayTime = 0;
	protected Context mContext;
	public SensorControler(Context mContext, long delayTime) {
		this.delayTime = delayTime;
		this.mContext = mContext;
	}

	public abstract void startEmbedSensor();
}