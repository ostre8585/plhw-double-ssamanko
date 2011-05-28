package com.poolc.pl.sensor.sensorControler;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.poolc.pl.sensor.Listener.BatteryListener;
import com.poolc.pl.sensor.dataType.BatteryDto;

public class BatteryControler extends SensorControler {

	private BatteryListener batteryListenerData;
	private ArrayList<BatteryDto> sdArrayList;
	
	public BatteryControler(Context mContext, long delayTime) {
		super(mContext, delayTime);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void startEmbedSensor() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		filter.addAction(Intent.ACTION_BATTERY_LOW);
		filter.addAction(Intent.ACTION_BATTERY_OKAY);
		filter.addAction(Intent.ACTION_POWER_CONNECTED);
		filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
		batteryListenerData = new BatteryListener();
		mContext.registerReceiver(batteryListenerData, filter);
		
		
		sdArrayList = new ArrayList<BatteryDto>();

	}
	
	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				sdArrayList.add(batteryListenerData.getBatteryData());
				this.sleep(delayTime);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public ArrayList<BatteryDto> getSdArrayList() {
		return sdArrayList;
	}

}
