package com.poolc.pl;


import java.util.Timer;

import com.poolc.pl.network.MobileClient;
import com.poolc.pl.sensor.dataType.DataCommandDto;

import android.app.Activity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class TotalOption extends Activity{
	CheckBox chkbServerSendable = null;
	EditText etServerSendable = null;
	MobileClient mobileClientJob = null;
	Timer networkJobScheduler = null;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.total_option);
		this.chkbServerSendable = (CheckBox)findViewById(R.id.is_send_server);
		this.chkbServerSendable.setOnCheckedChangeListener(CheckBoxValueChangeListener);
		this.etServerSendable = (EditText)findViewById(R.id.et_server_delay_period);
		this.etServerSendable.setOnKeyListener(txtKeyListener);
		networkJobScheduler = new Timer();
		
		if (DataCommandDto.confirmSendServer == true) {
			mobileClientJob = new MobileClient();
			
			networkJobScheduler.scheduleAtFixedRate(mobileClientJob, 0, (long) (DataCommandDto.confirmSendServerPeriod * 1000));
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		chkbServerSendable.setChecked(DataCommandDto.confirmSendServer);
		this.etServerSendable.setText(new StringBuffer().append(DataCommandDto.confirmSendServerPeriod));
	}
	
	private OnCheckedChangeListener CheckBoxValueChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton paramCompoundButton,
				boolean paramBoolean) {
			// TODO Auto-generated method stub
			DataCommandDto.confirmSendServer = chkbServerSendable.isChecked();
			
			if (DataCommandDto.confirmSendServer == true) {
				mobileClientJob = new MobileClient();
				
				networkJobScheduler.scheduleAtFixedRate(mobileClientJob, 0, (long) (DataCommandDto.confirmSendServerPeriod * 1000));
			} else {
				mobileClientJob.cancel();
				
			}
			
		}
	};
	
	private OnKeyListener txtKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(View paramView, int paramInt,
				KeyEvent paramKeyEvent) {
			// TODO Auto-generated method stub
			try{
				DataCommandDto.confirmSendServerPeriod = Double.parseDouble(etServerSendable.getText().toString());
				if (DataCommandDto.confirmSendServer == true) {
					mobileClientJob.cancel();
					mobileClientJob = new MobileClient();
					networkJobScheduler.scheduleAtFixedRate(mobileClientJob, 0, (long) (DataCommandDto.confirmSendServerPeriod * 1000));
				}
			}catch(NumberFormatException ex){
				etServerSendable.setText("");
				Toast.makeText(TotalOption.this, "숫자와 .만 입력가능합니다.", Toast.LENGTH_SHORT).show();
			}
			return false;
		}
	};
}
