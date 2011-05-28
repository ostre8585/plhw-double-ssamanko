package com.poolc.pl.sensor.dataType;

import android.os.Parcel;
import android.os.Parcelable;

public class DataCommandDto implements Parcelable{
	private boolean accelerometerUsable;
	private boolean batteryUsable;
	private boolean gpsUsable;
	private boolean gyroscopeUsable;
	private boolean lightUsable;
	private boolean magneticFieldUsable;
	private boolean orientationUsable;
	private boolean proximityUsable;
	
	private boolean confirmSendServer;


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
		paramParcel.writeValue(new Boolean(batteryUsable));
		paramParcel.writeValue(new Boolean(gpsUsable));
		paramParcel.writeValue(new Boolean(gyroscopeUsable));
		paramParcel.writeValue(new Boolean(lightUsable));
		paramParcel.writeValue(new Boolean(magneticFieldUsable));
		paramParcel.writeValue(new Boolean(orientationUsable));
		paramParcel.writeValue(new Boolean(proximityUsable));
		
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
			DataCommandDto dcDto = new DataCommandDto();
			dcDto.accelerometerUsable = (Boolean) paramParcel.readValue(Boolean.class.getClassLoader());
			dcDto.confirmSendServer = (Boolean) paramParcel.readValue(Boolean.class.getClassLoader());
			dcDto.batteryUsable = (Boolean) paramParcel.readValue(Boolean.class.getClassLoader());
			dcDto.gpsUsable = (Boolean) paramParcel.readValue(Boolean.class.getClassLoader());
			dcDto.gyroscopeUsable = (Boolean) paramParcel.readValue(Boolean.class.getClassLoader());
			dcDto.lightUsable = (Boolean) paramParcel.readValue(Boolean.class.getClassLoader());
			dcDto.magneticFieldUsable = (Boolean) paramParcel.readValue(Boolean.class.getClassLoader());
			dcDto.orientationUsable = (Boolean) paramParcel.readValue(Boolean.class.getClassLoader());
			dcDto.proximityUsable = (Boolean) paramParcel.readValue(Boolean.class.getClassLoader());
			
			return dcDto;
		}
	};
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

	public boolean isBatteryUsable() {
		return batteryUsable;
	}

	public void setBatteryUsable(boolean batteryUsable) {
		this.batteryUsable = batteryUsable;
	}

	public boolean isGpsUsable() {
		return gpsUsable;
	}

	public void setGpsUsable(boolean gpsUsable) {
		this.gpsUsable = gpsUsable;
	}

	public boolean isGyroscopeUsable() {
		return gyroscopeUsable;
	}

	public void setGyroscopeUsable(boolean gyroscopeUsable) {
		this.gyroscopeUsable = gyroscopeUsable;
	}

	public boolean isMagneticFieldUsable() {
		return magneticFieldUsable;
	}

	public void setMagneticFieldUsable(boolean magneticFieldUsable) {
		this.magneticFieldUsable = magneticFieldUsable;
	}

	public boolean isOrientationUsable() {
		return orientationUsable;
	}

	public void setOrientationUsable(boolean orientationUsable) {
		this.orientationUsable = orientationUsable;
	}

	public boolean isProximityUsable() {
		return proximityUsable;
	}

	public void setProximityUsable(boolean proximityUsable) {
		this.proximityUsable = proximityUsable;
	}

	public boolean isLightUsable() {
		return lightUsable;
	}

	public void setLightUsable(boolean lightUsable) {
		this.lightUsable = lightUsable;
	}
	
}
