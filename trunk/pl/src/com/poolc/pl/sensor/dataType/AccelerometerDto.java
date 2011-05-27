package com.poolc.pl.sensor.dataType;

public class AccelerometerDto {
	private float xAxisGravity = (float) 9.81;
	private float yAxisGravity = (float) 9.81;
	private float zAxisGravity = (float) 9.81;
	private float xAxisLinearAcceleration;
	private float yAxisLinearAcceleration;
	private float zAxisLinearAcceleration;
	private String timestamp;
	
	public AccelerometerDto(float xAxisGravity, float yAxisGravity, float zAxisGravity, float xAxisLinearAcceleration, float yAxisLinearAcceleration,
			float zAxisLinearAcceleration, String timestamp){
		this.xAxisGravity = xAxisGravity;
		this.yAxisGravity = yAxisGravity;
		this.zAxisGravity = zAxisGravity;
		this.xAxisLinearAcceleration = xAxisLinearAcceleration;
		this.yAxisLinearAcceleration = yAxisLinearAcceleration;
		this.zAxisLinearAcceleration = zAxisLinearAcceleration;
		this.timestamp = timestamp;
	}
	
	public AccelerometerDto() {
		// TODO Auto-generated constructor stub
	}

	public float getxAxisGravity() {
		return xAxisGravity;
	}

	public float getyAxisGravity() {
		return yAxisGravity;
	}

	public float getzAxisGravity() {
		return zAxisGravity;
	}

	public float getxAxisLinearAcceleration() {
		return xAxisLinearAcceleration;
	}

	public float getyAxisLinearAcceleration() {
		return yAxisLinearAcceleration;
	}

	public float getzAxisLinearAcceleration() {
		return zAxisLinearAcceleration;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	
}
