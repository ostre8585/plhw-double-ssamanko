package org.poolc.util;

import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class SeparateHandler extends StreamHandler {

	private static class StandardHandler extends StreamHandler {

		public StandardHandler(OutputStream out) {
			super(out, new SimpleFormatter());
			this.setLevel(Level.ALL);
			try {
				this.setEncoding("UTF8");
			} catch (Exception e) {
				try {
					setEncoding(null);
				} catch (Exception ex) {
				}
			}
		}

		public void publish(LogRecord record) {
			super.publish(record);

			flush();
		}

		public void close() {
			flush();
		}
	}

	private static class StdoutHandler extends StandardHandler {

		public StdoutHandler() {
			super(System.out);
		}
	}

	private static class StderrHandler extends StandardHandler {
		public StderrHandler() {
			super(System.err);
		}
	}
	
	private static final StdoutHandler stdout = new StdoutHandler();
	private static final StderrHandler stderr = new StderrHandler();
	
	public SeparateHandler() {
		super();
	}
	

	public void publish(LogRecord record) {
		Level level = record.getLevel();
		if (level.equals(Level.SEVERE) || level.equals(Level.WARNING)) {
			stderr.publish(record);
			stderr.flush();
			
		} else {
			stdout.publish(record);
			stdout.flush();
		}
	}

	public void close() {
		stderr.flush();
		stdout.flush();
	}
}
