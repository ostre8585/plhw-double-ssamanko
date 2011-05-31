package com.poolc.pl;

import com.poolc.pl.sensor.dataType.DataCommandDto;
import com.poolc.pl.sensor.dataType.SensorType;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ManageSensor extends Activity {
	private Context mContext = null;
	private Button btnSelectSensor = null;
	private static int sensorType = -1;
	private TextView tvSensorName;
	private CheckBox chkSenserUsable;
	private EditText etSensorDelayPeriod;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_sensor);
		mContext = this;
		btnSelectSensor = (Button) findViewById(R.id.btn_sensor_select);
		btnSelectSensor.setOnClickListener(btnClickListener);
		tvSensorName = (TextView) findViewById(R.id.sensor_name);
		chkSenserUsable = (CheckBox) findViewById(R.id.check_useable);
		chkSenserUsable.setOnCheckedChangeListener(chkChangeListner);
		etSensorDelayPeriod = (EditText)findViewById(R.id.et_sensor_delay_period);
		etSensorDelayPeriod.setOnKeyListener(txtKeyListener);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		String[] sensor = getResources().getStringArray(R.array.sensor_type_select);
		Toast.makeText(this, new StringBuffer().append(sensorType).toString(), Toast.LENGTH_SHORT).show();
		if(sensorType == -1)
			return;
		tvSensorName.setText(sensor[sensorType]);
		
		if(sensorType == SensorType.ACCELEROMETERSENSOR){
			chkSenserUsable.setChecked(DataCommandDto.accelerometerUsable);
			etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.accelerometerDelayTime).toString());
		} else if (sensorType == SensorType.BATTERY) {
			chkSenserUsable.setChecked(DataCommandDto.batteryUsable);
			etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.batteryDelayTime).toString());
		} else if (sensorType == SensorType.GPSSENSOR) {
			chkSenserUsable.setChecked(DataCommandDto.gpsUsable);
			etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.gpsDelayTime).toString());
		} else if (sensorType == SensorType.GYROSCOPESENSOR) {
			chkSenserUsable.setChecked(DataCommandDto.gyroscopeUsable);
			etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.gyroscopeDelayTime).toString());
		} else if (sensorType == SensorType.LIGHTSENSOR) {
			chkSenserUsable.setChecked(DataCommandDto.lightUsable);
			etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.lightDelayTime).toString());
		} else if (sensorType == SensorType.MAGNETICFIELDSENSOR) {
			chkSenserUsable.setChecked(DataCommandDto.magneticFieldUsable);
			etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.magneticFieldDelayTime).toString());
		} else if (sensorType == SensorType.ORIENTATIONSENSOR) {
			chkSenserUsable.setChecked(DataCommandDto.orientationUsable);
			etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.orientationDelayTime).toString());
		} else if (sensorType == SensorType.PROXIMITYSENSOR) {
			chkSenserUsable.setChecked(DataCommandDto.proximityUsable);
			etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.proximityDelayTime).toString());
		}
		
	}

	private OnClickListener btnClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View paramView) {
			// TODO Auto-generated method stub
			new AlertDialog.Builder(ManageSensor.this)
					.setTitle("센서선택")
					.setItems(
							R.array.sensor_type_select,
							(android.content.DialogInterface.OnClickListener) dialogClickListener)
					.setNegativeButton("취소", null).show();
		}
	};

	private android.content.DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface paramDialogInterface, int paramInt) {
			// TODO Auto-generated method stub
			String[] sensor = getResources().getStringArray(
					R.array.sensor_type_select);
			tvSensorName.setText(sensor[paramInt]);
			sensorType = paramInt;
			Toast.makeText(ManageSensor.this, new StringBuffer().append(sensorType).toString(), Toast.LENGTH_SHORT).show();
			if(sensorType == SensorType.ACCELEROMETERSENSOR){
				chkSenserUsable.setChecked(DataCommandDto.accelerometerUsable);
				etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.accelerometerDelayTime).toString());
			} else if (sensorType == SensorType.BATTERY) {
				chkSenserUsable.setChecked(DataCommandDto.batteryUsable);
				etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.batteryDelayTime).toString());
			} else if (sensorType == SensorType.GPSSENSOR) {
				chkSenserUsable.setChecked(DataCommandDto.gpsUsable);
				etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.gpsDelayTime).toString());
			} else if (sensorType == SensorType.GYROSCOPESENSOR) {
				chkSenserUsable.setChecked(DataCommandDto.gyroscopeUsable);
				etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.gyroscopeDelayTime).toString());
			} else if (sensorType == SensorType.LIGHTSENSOR) {
				chkSenserUsable.setChecked(DataCommandDto.lightUsable);
				etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.lightDelayTime).toString());
			} else if (sensorType == SensorType.MAGNETICFIELDSENSOR) {
				chkSenserUsable.setChecked(DataCommandDto.magneticFieldUsable);
				etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.magneticFieldDelayTime).toString());
			} else if (sensorType == SensorType.ORIENTATIONSENSOR) {
				chkSenserUsable.setChecked(DataCommandDto.orientationUsable);
				etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.orientationDelayTime).toString());
			} else if (sensorType == SensorType.PROXIMITYSENSOR) {
				chkSenserUsable.setChecked(DataCommandDto.proximityUsable);
				etSensorDelayPeriod.setText(new StringBuffer().append(DataCommandDto.proximityDelayTime).toString());
			}
		}
	};

	private OnCheckedChangeListener chkChangeListner = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton paramCompoundButton,
				boolean paramBoolean) {
			// TODO Auto-generated method stub
			if (sensorType == SensorType.ACCELEROMETERSENSOR) {
				DataCommandDto.accelerometerUsable = chkSenserUsable.isChecked();
			} else if(sensorType == SensorType.BATTERY) {
				DataCommandDto.batteryUsable = chkSenserUsable.isChecked();
			} else if (sensorType == SensorType.GPSSENSOR) {
				DataCommandDto.gpsUsable = chkSenserUsable.isChecked();
			} else if (sensorType == SensorType.GYROSCOPESENSOR) {
				DataCommandDto.gyroscopeUsable = chkSenserUsable.isChecked();
			} else if (sensorType == SensorType.LIGHTSENSOR) {
				DataCommandDto.lightUsable = chkSenserUsable.isChecked();
			} else if (sensorType == SensorType.MAGNETICFIELDSENSOR) {
				DataCommandDto.magneticFieldUsable = chkSenserUsable.isChecked();
			} else if (sensorType == SensorType.ORIENTATIONSENSOR) {
				DataCommandDto.orientationUsable = chkSenserUsable.isChecked();
			} else if (sensorType == SensorType.PROXIMITYSENSOR) {
				DataCommandDto.proximityUsable = chkSenserUsable.isChecked();
			}
			
		}
	};
	
	private OnKeyListener txtKeyListener = new OnKeyListener() {
		
		@Override
		public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent) {
			// TODO Auto-generated method stub
			try{
				if(sensorType == SensorType.ACCELEROMETERSENSOR){
					DataCommandDto.accelerometerDelayTime = Double.parseDouble(etSensorDelayPeriod.getText().toString());
				} else if (sensorType == SensorType.BATTERY){
					DataCommandDto.batteryDelayTime = Double.parseDouble(etSensorDelayPeriod.getText().toString());
				} else if (sensorType == SensorType.GPSSENSOR){
					DataCommandDto.gpsDelayTime = Double.parseDouble(etSensorDelayPeriod.getText().toString());
				} else if (sensorType == SensorType.GYROSCOPESENSOR){
					DataCommandDto.gyroscopeDelayTime = Double.parseDouble(etSensorDelayPeriod.getText().toString());
				} else if (sensorType == SensorType.LIGHTSENSOR){
					DataCommandDto.lightDelayTime = Double.parseDouble(etSensorDelayPeriod.getText().toString());
				} else if (sensorType == SensorType.MAGNETICFIELDSENSOR){
					DataCommandDto.magneticFieldDelayTime = Double.parseDouble(etSensorDelayPeriod.getText().toString());
				} else if (sensorType == SensorType.ORIENTATIONSENSOR){
					DataCommandDto.orientationDelayTime = Double.parseDouble(etSensorDelayPeriod.getText().toString());
				} else if (sensorType == SensorType.PROXIMITYSENSOR){
					DataCommandDto.proximityDelayTime = Double.parseDouble(etSensorDelayPeriod.getText().toString());
				}
				
			}catch(NumberFormatException ex){
				etSensorDelayPeriod.setText("");
				Toast.makeText(ManageSensor.this, "숫자와 .만 입력가능합니다.", Toast.LENGTH_SHORT).show();
			}finally{
				return true;
			}
		}
	};
}
