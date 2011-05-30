package org.poolc.util;

import java.util.ArrayList;
import java.util.List;

public class ShutdownHooker {
	private static ShutdownHooker instance;
	public static ShutdownHooker getInstance() {
		return (instance == null? (instance = new ShutdownHooker()): instance);
	}

	List<Runnable> runnables = new ArrayList<Runnable>();
	
	private ShutdownHooker() {
	}
	
	public void add(Runnable runnable) {
		runnables.add(runnable);
	}
	
	public void remove(Runnable runnable) {
		runnables.remove(runnable);
	}
	
	public void execute() {
		for (Runnable runnable: runnables) {
			try {
				runnable.run();
			} catch (Exception e) {
			}
		}
	}
	
	public void clear() {
		runnables.clear();
	}
}
