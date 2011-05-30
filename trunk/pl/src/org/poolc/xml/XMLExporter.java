package org.poolc.xml;

import java.lang.reflect.Field;

import org.poolc.util.StringHelper;
import org.poolc.xml.bind.XMLAttribute;
import org.poolc.xml.bind.XMLModel;

public class XMLExporter {
	
	public static final XMLExporter defaultExporter = new XMLExporter();
	
	public static XMLElement doExport(Iterable<?> objects) throws IllegalArgumentException, IllegalAccessException {
		return defaultExporter.export(objects);
	}

	public static XMLElement doExport(Object object) throws IllegalArgumentException, IllegalAccessException {
		return defaultExporter.export(object);
	}
	
	public static interface ExportHandler {
		public Object handle(Field field, Object object, Object value);
	}
	
	private ExportHandler handler;
	
	public XMLExporter() {
		this(null);
	}
	
	public XMLExporter(ExportHandler handler) {
		super();
		this.handler = handler;
	}
	
	public XMLElement export(Iterable<?> objects) throws IllegalArgumentException, IllegalAccessException {
		XMLElement list = new XMLElement("list");
		String elementName = null;
		for (Object object: objects) {
			XMLElement element = export(object);
			elementName = element.getTagName();
			list.appendChild(element);
		}
		if (elementName != null)
			list.setTagName(elementName + "-list");
		return list;
	}
	
	public XMLElement export(Object object) throws IllegalArgumentException, IllegalAccessException {
		if (object == null)
			throw new IllegalArgumentException("Object cannot be null.");
		
		Class<?> clazz = object.getClass();
		for (; clazz != null && !clazz.equals(Object.class); clazz = clazz.getSuperclass())
			if (clazz.isAnnotationPresent(XMLModel.class))
				break;
		
		if (clazz == null || clazz.equals(Object.class))
			throw new IllegalArgumentException("Object should be annotated XMLModel.");
			
		String tagName = clazz.getAnnotation(XMLModel.class).tagName();
		if (tagName.equals("##default")) {	
			tagName = StringHelper.toXMLNodeName(clazz.getSimpleName());
		}
		
		XMLElement model = new XMLElement(tagName);
		for (; !clazz.equals(Object.class) && clazz.isAnnotationPresent(XMLModel.class); clazz = clazz.getSuperclass()) {
			for (Field field: clazz.getDeclaredFields()) {
				if (!field.isAnnotationPresent(XMLAttribute.class))
					continue;
				
				field.setAccessible(true);
				String nodeName = field.getAnnotation(XMLAttribute.class).nodeName();
				if (nodeName.equals("##default")) {
					nodeName = StringHelper.toXMLNodeName(field.getName());
				}
				Object value = field.get(object);
				if (handler != null) 
					value = handler.handle(field, object, value);
				
				XMLElement attribute = new XMLElement(model, nodeName);
				if (value != null) {
					if (field.getAnnotation(XMLAttribute.class).base64()) {
						attribute.setTextContentWithEncode(value.toString());
					} else {
						attribute.setTextContent(value.toString());
					}
				} else {
					attribute.setAttribute("null", "true");
				}
				model.appendChild(attribute);
			}
		}
		return model;
	}
}
