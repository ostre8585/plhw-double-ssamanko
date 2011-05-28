package com.poolc.pl.sensor.dataType;

public class GpsDto {
	private double latitude;
	private double longitude;
	private String timestamp;
	
	public GpsDto() {
		
	}
	public GpsDto(double latitude, double longitude, String timestamp) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public String getTimestamp() {
		return timestamp;
	}

	public GpsDto clone() {
		return new GpsDto(this.latitude, this.longitude, this.timestamp);
	}
}
