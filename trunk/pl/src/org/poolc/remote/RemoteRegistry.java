package org.poolc.remote;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RemoteRegistry {
	static Map<String, Object> bindObjectMap = new ConcurrentHashMap<String, Object>();

	public synchronized static <T> String bind(Class<T> interfaceClass, T bindObject) {
		return bind(interfaceClass.getName(), bindObject);
	}
	
	public synchronized static String bind(String bindObjectName, Object bindObject) {
		bindObjectMap.put(bindObjectName, bindObject);
		return bindObjectName;
	}
	
	public synchronized static Object find(String bindObjectName) {
		return bindObjectMap.get(bindObjectName);
	}
	
	public synchronized static void remove(Class<?> interfaceClass) {
		remove(interfaceClass.getName());
	}
	
	public synchronized static void remove(String bindObjectName) {
		bindObjectMap.remove(bindObjectName);
	}
}
