package com.poolc.pl;

import java.util.List;
import java.util.Vector;

import com.poolc.pl.sensor.sensorControler.sensorControlerIm;
import com.poolc.pl.sensorListeners.SensorListener;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;

import android.hardware.SensorManager;
import android.os.Bundle;


public class framework extends Activity {
	private SensorManager sm = null;
	private Vector<Sensor> sensorVector = null;
	private Vector<SensorListener> slVector = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> arSensor = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        
        sensorControlerIm sc = new sensorControlerIm(sm, 1000);
        sc.startEmbedSensor();
        sc.start();

//        sensorVector = new Vector<Sensor>();
//        for(Sensor sensorType:arSensor){
//        	sensorVector.add(sm.getDefaultSensor(sensorType.getType()));
//        }
//        slVector = new Vector<sensorListner>();

//        sensorVector = new Vector<Sensor>();
//        for(Sensor sensorType:arSensor){
//        	sensorVector.add(sm.getDefaultSensor(sensorType.getType()));
//        }
//        String result = "size = " + arSensor.size() + "\n\n";
//        for (Sensor s : arSensor) {
//            result += ("name = " + s.getName() + " ,type = " + s.getType()+"\n");
//        }
//       
//        TextView test = (TextView)findViewById(R.id.test);
//        test.setText(result);

    }
    
//    private class TotalSensorObserver implements Observer{
//    	
//		@Override
//		public void update(Observable arg0, Object arg1) {
//			// TODO Auto-generated method stub
//			
//		}
//    
//    }
//    
//    private class SensorAdministrator implements Observer{
//    	private int delayRate;
//    	
//    	public SensorAdministrator(){
//    		delayRate = sm.SENSOR_DELAY_UI; 
//    	}
//    	
//    	private void startEmbedSensor(String sensorName) throws IllegalArgumentException, IllegalAccessException{
//        	Field[] fields = Sensor.class.getFields();
//        	for(Field field : fields){
//        		if(field.getName().matches(sensorName)){
//        			int sensorType = field.getInt(field);
//        			sensorListner sensorListnerData= new sensorListner(sensorType);
//        			slVector.add(sensorListnerData);
//        			sm.registerListener(sensorListnerData, sm.getDefaultSensor(sensorType), delayRate);
//        		}
//        	}
//        	
//        }
//
//		@Override
//		public void update(Observable arg0, Object arg1) {
//			// TODO Auto-generated method stub
//			if(arg1.toString().matches("sensor close:[a-zA-Z]*")){
//				String matchString = arg1.toString().replace("sensor close:", "");
//				for(sensorListner sl : slVector){
//					if(sl.getSensorType()==Integer.parseInt(matchString)){
//						
//					}
//				}
//			}
//		}
//    	
//    }
    
   
}