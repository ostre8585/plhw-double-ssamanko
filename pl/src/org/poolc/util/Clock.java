package org.poolc.util;

public class Clock {
	private long startTime;
	public Clock() {
		super();
		this.reset();
	}
	
	public long stop() {
		return System.currentTimeMillis() - this.startTime;
	}
	
	public void reset() {
		this.startTime = System.currentTimeMillis();
	}
}
