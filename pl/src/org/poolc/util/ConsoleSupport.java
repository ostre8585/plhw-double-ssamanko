package org.poolc.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleSupport {
	private static final Logger logger = Logger.getLogger(ConsoleSupport.class.getName());
	public static final Command DEFAULT_QUIT_COMMAND = new Command() {
		@Override
		public void action(String line) {
			ShutdownHooker.getInstance().execute();
			System.exit(0);
		}
	};

	static {
		logger.setLevel(Level.OFF);
	}
	
	public static void setLoggerLevel(Level newLevel) {
		logger.setLevel(newLevel);
	}
	
	public static interface Command {
		public void action(String line);
	}
	
	private Thread consoleThread;
	private Map<String, Command> commandMap = new HashMap<String, Command>();
	private Scanner reader;
	
	public ConsoleSupport() {
		super();
		this.consoleThread = new Thread() {
			@Override
			public void run() {
				reader = new Scanner(System.in);
				logger.info("Console-Support is starting.");
				try {
					while (reader.hasNextLine()) {
						String line = reader.nextLine().trim();
						logger.info("Console command(" + line + ") is received.");
						
						for (String prefix: commandMap.keySet()) {
							if (line.startsWith(prefix)) {
								commandMap.get(prefix).action(line);
							}
						}
					}
					if (reader != null)
						reader.close();
				} catch (Exception e) {
				}		
			}
		};
	}

	public void start() {
		this.consoleThread.start();
	}
	
	public void stop() {
		try {
			this.reader.close();
		} catch (Exception e) {
		}
		
		logger.info("Console-Support is stopped.");
	}
	
	public void addCommand(String prefix, Command command) {
		this.commandMap.put(prefix, command);
	}
	
	public void removeCommand(String prefix) {
		this.commandMap.remove(prefix);
	}
}
