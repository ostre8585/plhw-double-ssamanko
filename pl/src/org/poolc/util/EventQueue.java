package org.poolc.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventQueue {
	private static final Logger logger = Logger.getLogger(EventQueue.class.getName());

	private static EventQueue instance;

	public static EventQueue sharedQueue() {
		return instance == null ? (instance = new EventQueue()) : instance;
	}

	private Map<Integer, List<EventListener>> listenerMap = new ConcurrentHashMap<Integer, List<EventListener>>();
	private BlockingQueue<Event> queue = new LinkedBlockingQueue<Event>();
	private List<Runnable> laterWorks = new CopyOnWriteArrayList<Runnable>();

	private EventDispatcher dispatcher;

	public EventQueue() {
		super();
		dispatcher = new EventDispatcher();
		dispatcher.start();
	}

	public void addEventListener(int eventId, EventListener listener) {
		if (!listenerMap.containsKey(eventId)) {
			listenerMap.put(eventId, new ArrayList<EventListener>());
		}
		listenerMap.get(eventId).add(listener);
	}

	public void invoke(Event event) {
		try {
			queue.put(event);
			Thread.yield();
		} catch (InterruptedException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public void invokeLater(Runnable runnable) {
		laterWorks.add(runnable);
		Thread.yield();
	}

	private class EventDispatcher extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					Event event = queue.take();

					if (listenerMap.containsKey(event.getId())) {
						for (EventListener listener : listenerMap.get(event.getId())) {
							try {
								listener.dispatch(event);
							} catch (Exception e) {
								logger.log(Level.WARNING, e.getMessage(), e);
							}
						}
					}
					
					if (laterWorks.isEmpty())
						continue;

					Iterator<Runnable> iterator = laterWorks.iterator();
					while (iterator.hasNext()) {
						Runnable runnable = iterator.next();
						try {
							runnable.run();
						} catch (Exception e) {
							logger.log(Level.WARNING, e.getMessage(), e);
						}
						iterator.remove();
					}
				}
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);

				ShutdownHooker.getInstance().execute();
				System.exit(0);
			}
		}
	}


}
