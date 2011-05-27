package com.poolc.pl.sensor.dataType;

public class ProximityDto {
	private float proximity;
	private String timestamp;
	
	public ProximityDto(){
		
	}
	
	public ProximityDto(float proximity, String timestamp){
		this.proximity = proximity;
		this.timestamp = timestamp;
	}

	public float getProximity() {
		return proximity;
	}

	public String getTimestamp() {
		return timestamp;
	}
}
