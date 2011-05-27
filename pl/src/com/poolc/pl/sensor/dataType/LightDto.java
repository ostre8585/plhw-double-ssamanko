package com.poolc.pl.sensor.dataType;

public class LightDto {
	private float lux;
	private String timestamp;
	
	public LightDto(){
		
	}
	
	public LightDto(float lux, String timestamp){
		this.lux = lux;
		this.timestamp = timestamp;
	}

	public float getLux() {
		return lux;
	}

	public String getTimestamp() {
		return timestamp;
	}
}
