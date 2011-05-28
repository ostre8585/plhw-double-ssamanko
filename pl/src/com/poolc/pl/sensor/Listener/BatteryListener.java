package com.poolc.pl.sensor.Listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.poolc.pl.sensor.dataType.BatteryDto;

public class BatteryListener extends BroadcastReceiver {
	private static BatteryDto batteryData;
	private static SimpleDateFormat dateFormat;
	
	public BatteryListener() {
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String timestamp = dateFormat.format(Calendar.getInstance().getTime());
		if(intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false) == false) {
			batteryData = new BatteryDto(-1, -1, -1, timestamp);
			return;
		}
		int plug, status, scale, level;
		plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN);
		scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE,100);
		level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		
		batteryData = new BatteryDto(plug, status, level*100/scale, timestamp);
		
		
	}
	
	public BatteryDto getBatteryData() {
		return batteryData.clone();
	}
	
	
	

	

}
