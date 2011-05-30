package org.poolc.xml;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.poolc.util.StringHelper;
import org.poolc.xml.bind.XMLAttribute;

public class XMLImporter {
	
	public static final XMLImporter defaultImporter = new XMLImporter();

	public static <T> T doImportOne(Class<T> clazz, XMLElement element) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchFieldException {
		return defaultImporter.importOne(clazz, element);
	}

	public static <T> List<T> doImportList(Class<T> clazz, XMLElement elements) throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
		return defaultImporter.importList(clazz, elements);
	}
	
	public static interface ImportHandler {
		public Object handle(Field field, Object value);
	}
	
	private ImportHandler handler;
	
	public XMLImporter() {
		this(null);
	}
	
	public XMLImporter(ImportHandler handler) {
		super();
		this.handler = handler;
	}

	public <T> T importOne(Class<T> clazz, XMLElement element) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchFieldException {
		T instance = clazz.newInstance();
		for (XMLElement attribute: element) {
			String fieldName = StringHelper.fromXMLNodeNameToCamelCase(attribute.getTagName());
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			if (attribute.hasAttribute("null") && attribute.getAttribute("null").equals("true")) {
				field.set(instance, null);
				
			} else if (field.getAnnotation(XMLAttribute.class).base64()) {
				Object value = XMLSerializer.getValue(field.getType(), attribute.getTextContentWithDecode());
				if (handler != null)
					value = handler.handle(field, value);
				field.set(instance, value);
				
			} else {
				Object value = XMLSerializer.getValue(field.getType(), attribute.getTextContent());
				if (handler != null)
					value = handler.handle(field, value);
				field.set(instance, value);
			}
		}
		
		return instance;
	}
	
	public <T> List<T> importList(Class<T> clazz, XMLElement elements) throws SecurityException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
		List<T> list = new ArrayList<T>();
		for (XMLElement element: elements)
			list.add(importOne(clazz, element));
		return list;
	}
}
