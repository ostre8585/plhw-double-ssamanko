package com.poolc.pl.sensor.dataType;

public class MagneticFieldDto {
	private float xAxis;
	private float yAxis;
	private float zAxis;
	private String timestamp;
	
	public MagneticFieldDto(){
		
	}
	
	public MagneticFieldDto(float xAxis, float yAxis, float zAxis, String timestamp){
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.zAxis = zAxis;
		this.timestamp = timestamp;
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
	
	public MagneticFieldDto clone() {
		return new MagneticFieldDto(this.xAxis, this.yAxis, this.zAxis, this.timestamp);
	}
	
	
}
