package com.poolc.pl.sensor.dataType;

public class GyroscopeDto {
	private float xAxis;
	private float yAxis;
	private float zAxis;
	private String timestamp;
	
	public GyroscopeDto(){
		
	}
	
	public GyroscopeDto(float xAxis, float yAxis, float zAxis, String timestmap){
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.zAxis = zAxis;
		this.timestamp = timestmap;
	}

	public float getxAxis() {
		return xAxis;
	}

	public float getyAxis() {
		return yAxis;
	}

	public float getzAxis() {
		return zAxis;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	public GyroscopeDto clone() {
		return new GyroscopeDto(this.xAxis, this.yAxis, this.zAxis, this.timestamp);
	}

	
}
