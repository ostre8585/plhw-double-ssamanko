package com.poolc.pl.sensor.dataType;

import android.os.Parcel;
import android.os.Parcelable;

public class DataCommandDto implements Parcelable{
	private boolean accelerometerUsable;
	
	private boolean confirmSendServer;

	public boolean isAccelerometerUsable() {
		return accelerometerUsable;
	}

	public void setAccelerometerUsable(boolean accelerometerUsable) {
		this.accelerometerUsable = accelerometerUsable;
	}

	public boolean isConfirmSendServer() {
		return confirmSendServer;
	}

	public void setConfirmSendServer(boolean confirmSendServer) {
		this.confirmSendServer = confirmSendServer;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel paramParcel, int paramInt) {
		// TODO Auto-generated method stub
		paramParcel.writeValue(new Boolean(accelerometerUsable));
		paramParcel.writeValue(new Boolean(confirmSendServer));
	}
	
	public static final Parcelable.Creator<DataCommandDto> CREATOR = new Creator<DataCommandDto>() {
		
		@Override
		public DataCommandDto[] newArray(int paramInt) {
			// TODO Auto-generated method stub
			return new DataCommandDto[paramInt];
		}
		
		@Override
		public DataCommandDto createFromParcel(Parcel paramParcel) {
			// TODO Auto-generated method stub
			
			return null;
		}
	};
	
}
