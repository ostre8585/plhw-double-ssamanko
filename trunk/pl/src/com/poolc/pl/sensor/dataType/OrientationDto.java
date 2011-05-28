package com.poolc.pl.sensor.dataType;

public class OrientationDto {
	private float azimuth;
	private float pitch;
	private float roll;
	private String timestamp;
	
	public OrientationDto(){
		
	}
	
	public OrientationDto(float azimuth, float pitch, float roll, String timeStamp){
		this.azimuth = azimuth;
		this.pitch = pitch;
		this.roll = roll;
		this.timestamp = timeStamp;
	}

	public float getAzimuth() {
		return azimuth;
	}

	public float getPitch() {
		return pitch;
	}

	public float getRoll() {
		return roll;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	public OrientationDto clone() {
		return new OrientationDto(this.azimuth, this.pitch, this.roll, this.timestamp);
	}
	
}
