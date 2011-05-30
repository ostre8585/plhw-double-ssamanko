package com.poolc.pl;

import java.util.ArrayList;

import com.poolc.pl.sensor.dataType.DataCommandDto;



import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class framework extends TabActivity {
	private SensorManager sm = null;
	private TabHost mTab;
	private ArrayList<TabSpec> tabList = new ArrayList<TabSpec>();
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mTab = getTabHost();
        
        mTab.addTab(mTab.newTabSpec("tag").setIndicator("Senser").setContent(new Intent(this,ManageSensor.class)));
        mTab.addTab(mTab.newTabSpec("tag").setIndicator("Option").setContent(new Intent(this, TotalOption.class)));
        
    }
    
   
}