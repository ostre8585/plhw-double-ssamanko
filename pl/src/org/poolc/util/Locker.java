package org.poolc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;

public class Locker {
	private File lockFile;
	private FileOutputStream outStream;
	private FileLock lock;
	
	public Locker(String appName, String name) {
		lockFile = new File(System.getenv("APPDATA") + File.separator + appName + File.separator + name);
		if (!lockFile.getParentFile().exists())
			lockFile.getParentFile().mkdirs();
	}
	
	public boolean lock() {
		try {
			outStream = new FileOutputStream(lockFile);
			lock = outStream.getChannel().tryLock();
			return lock != null;
		} catch (Exception e) {
			e.printStackTrace();
			outStream = null;
			lock = null;
		}
		return false;
	}
	public boolean unlock() {
		try {
			if (lock != null) {
				lock.release();
			}
			if (outStream != null) {
				outStream.close();
			}
			
			lock = null;
			outStream = null;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
