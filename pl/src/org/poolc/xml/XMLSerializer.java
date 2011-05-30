package org.poolc.xml;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class XMLSerializer {

	public static XMLElement serialize(Object obj) {
		Class<?> clazz = obj.getClass();
		
		if (Enum.class.isAssignableFrom(clazz))
			return toXML((Enum<?>) obj);

		if (List.class.isAssignableFrom(clazz))
			return toXML((List<?>) obj);

		if (Set.class.isAssignableFrom(clazz))
			return toXML((Set<?>) obj);
		
		if (Map.class.isAssignableFrom(clazz))
			return toXML((Map<?, ?>) obj);

		if (clazz.isArray()) 
			return toXML((Object[]) obj);

		XMLElement element = new XMLElement("object");
		element.setAttribute("type", clazz.getName());
		if (Date.class.isAssignableFrom(clazz)) {
			element.setTextContentWithEncode(Long.toString(((Date) obj).getTime()));
			
		} else if (clazz.isPrimitive() ||
				Number.class.isAssignableFrom(clazz) ||
				String.class.isAssignableFrom(clazz) ||
				Boolean.class.equals(clazz) ||
				Character.class.equals(clazz)) {
			element.setTextContentWithEncode(obj.toString());
			
		} else {
			while (!clazz.equals(Object.class)) {
				for (Field field: clazz.getDeclaredFields()) {
					try {
						if (Modifier.isFinal(field.getModifiers()) ||
							Modifier.isStrict(field.getModifiers()) ||
							Modifier.isTransient(field.getModifiers()))
							continue;
						
						field.setAccessible(true);
						Object fieldValue = field.get(obj);
						XMLElement fieldElement = null;
						if (fieldValue != null)
							fieldElement = serialize(field.get(obj));
						else {
							fieldElement = new XMLElement("object");
							fieldElement.setAttribute("type", field.getType().getName());
						}
						fieldElement.setAttribute("field", field.getName());
						fieldElement.setAttribute("base", clazz.getName());
						element.appendChild(fieldElement);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				clazz = clazz.getSuperclass();
			}
		}
		return element;
	}
	
	private static XMLElement toXML(Enum<?> en) {
		XMLElement element = new XMLElement("enum");
		element.setAttribute("type", en.getClass().getName());
		element.setTextContentWithEncode(en.toString());
		return element;
	}

	private static XMLElement toXML(Object[] objs) {
		XMLElement element = new XMLElement("array");
		element.setAttribute("type", objs.getClass().getComponentType().getName());
		element.setAttribute("length", Integer.toString(objs.length));
		for (Object obj: objs) {
			XMLElement child = serialize(obj);
			element.appendChild(child);
		}
		return element;
	}

	private static XMLElement toXML(List<?> list) {
		XMLElement element = new XMLElement("list");
		for (Object obj: list) {
			XMLElement child = serialize(obj);
			child.setAttribute("type", obj.getClass().getName());
			element.appendChild(child);
		}
		return element;
	}

	private static XMLElement toXML(Set<?> list) {
		XMLElement element = new XMLElement("set");
		for (Object obj: list) {
			XMLElement child = serialize(obj);
			child.setAttribute("type", obj.getClass().getName());
			element.appendChild(child);
		}
		return element;
	}
	
	private static XMLElement toXML(Map<?, ?> map) {
		XMLElement element = new XMLElement("map");
		for (Object key: map.keySet()) {
			Object value = map.get(key);
			XMLElement entry = new XMLElement("entry");
			XMLElement keyElement = serialize(key);
			XMLElement valueElement = serialize(value);
			keyElement.setAttribute("entry", "key");
			valueElement.setAttribute("entry", "value");
			
			entry.appendChild(keyElement);
			entry.appendChild(valueElement);
			element.appendChild(entry);
		}
		return element;
	}
	
	@SuppressWarnings("unchecked")
	public static Object deserialize(XMLElement element) {
		String tagName = element.getTagName();
		if (tagName.equals("enum")) {
			try {
				@SuppressWarnings("rawtypes")
				Class enumType = Class.forName(element.getAttribute("type"));
				return Enum.valueOf(enumType, element.getTextContentWithDecode().toUpperCase());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		} else if (tagName.equals("array")) {
			try {
				Class<?> componentType = Class.forName(element.getAttribute("type"));
				int length = Integer.parseInt(element.getAttribute("length"));
				Object array = Array.newInstance(componentType, length);
				for (int index = 0; index < element.getChildCount(); index++) {
					Array.set(array, index, deserialize(element.getChildAt(index)));
				}
				return array;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
			
		} else if (tagName.equals("list")) {
			List<Object> list = new ArrayList<Object>();
			for (XMLElement child: element) {
				list.add(deserialize(child));
			}
			return list;
			
		} else if (tagName.equals("set")) {
			Set<Object> set = new HashSet<Object>();
			for (XMLElement child: element) {
				set.add(deserialize(child));
			}
			return set;
			
		} else if (tagName.equals("map")) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			for (XMLElement entry: element) {
				XMLElement keyElement = entry.getChildAt(0);
				XMLElement valueElement = entry.getChildAt(1);
				map.put(deserialize(keyElement), deserialize(valueElement));
			}
			return map;
			
		} else if (tagName.equals("object")) {
			if (element.getChildCount() == 0) {
				// primitive object(number, string, date)
				try {
					String decode = element.getTextContentWithDecode();
					if (decode == null)
						return null;
					
					return getValue(Class.forName(element.getAttribute("type")), decode);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// member object
				try {
					Class<?> clazz = Class.forName(element.getAttribute("type"));
					Constructor<?> cons = clazz.getDeclaredConstructor();
					cons.setAccessible(true);
					
					Object object = cons.newInstance();
					for (XMLElement fieldElement: element) {
						Class<?> fieldClass = Class.forName(fieldElement.getAttribute("base"));
						Field field = fieldClass.getDeclaredField(fieldElement.getAttribute("field"));
						field.setAccessible(true);
						
						Object fieldValue = deserialize(fieldElement);
						field.set(object, fieldValue);
					}
					return object;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	static Object getValue(Class<?> clazz, String string) {
		Object value = null;
		if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
			value = Boolean.parseBoolean(string);
			
		} else if (clazz.equals(char.class) || clazz.equals(Character.class)) {
			value = string.charAt(0);
			
		} else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
			value = Byte.parseByte(string);
			
		} else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
			value = Short.parseShort(string);
			
		} else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			value = Integer.parseInt(string);
			
		} else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			value = Long.parseLong(string);
			
		} else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
			value = Float.parseFloat(string);
			
		} else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			value = Double.parseDouble(string);
			
		} else if (clazz.equals(String.class)) {
			value = string;
			
		} else if (Date.class.isAssignableFrom(clazz)) {
			try {
				value = clazz.getConstructor(long.class).newInstance(Long.parseLong(string));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return value;
	}
}