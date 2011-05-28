package com.poolc.pl.sensor.dataType;

public class BatteryDto {
	private int plug;
	private int status;
	private int ratio;
	private String timestamp;
	
	public BatteryDto(int plug, int status, int ratio, String timestamp) {
		this.plug = plug;
		this.status = status;
		this.ratio = ratio;
		this.timestamp = timestamp;
	}

	public int getPlug() {
		return plug;
	}

	public int getStatus() {
		return status;
	}

	public int getRatio() {
		return ratio;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public BatteryDto clone() {
		return new BatteryDto(this.plug, this.status, this.ratio, this.timestamp);
	}
}
