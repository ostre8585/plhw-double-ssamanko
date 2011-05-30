package org.poolc.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.poolc.util.Base64;
import org.poolc.util.Base64DecodingException;

public class XMLElement implements Iterable<XMLElement>, Serializable {
	private static final long serialVersionUID = -977703243939688264L;
	
	private XMLElement parent;
	private Map<String, String> attributes = new HashMap<String, String>();
	private List<XMLElement> children = new ArrayList<XMLElement>();
	private String tagName;
	private String textContent;


	public XMLElement(String tagName) {
		this((XMLElement) null, tagName);
	}
	
	public XMLElement(String tagName, String content) {
		this(tagName);
		setTextContent(content);
	}
	
	public XMLElement(XMLElement parent, String tagName) {
		super();
		this.parent = parent;
		this.tagName = tagName;
	}

	public XMLElement(XMLElement other) {
		this(null, other);
	}
	
	public XMLElement(XMLElement parent, XMLElement other) {
		super();
		this.parent = parent;
		this.tagName = other.tagName;
		this.textContent = other.textContent;
		this.attributes.putAll(other.attributes);
		
		for (XMLElement child: other.children) {
			this.children.add(new XMLElement(child));
		}
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	public XMLElement getParent() {
		return parent;
	}

	public void setParent(XMLElement parent) {
		this.parent = parent;
	}

	public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}
	
	public boolean hasAttribute(String key) {
		return attributes.containsKey(key);
	}

	public String getAttribute(String key) {
		return attributes.containsKey(key) ? attributes.get(key) : "";
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	
	public void setTextContentWithEncode(String content) {
		this.textContent = Base64.encode(content).replace('\n', '%');
	}
	
	public String getTextContentWithDecode() {
		if (textContent == null)
			return null;
		
		try {
			return new String(Base64.decode(textContent.replace('%', '\n').getBytes()));
		} catch (Base64DecodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTextContent() {
		return textContent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('<').append(tagName).append(' ');
		for (Entry<String, String> attribute : attributes.entrySet()) {
			builder.append(attribute.getKey()).append("=\"").append(
					attribute.getValue()).append("\" ");
		}
		if (textContent == null && children.size() == 0) {
			builder.append("/>");
			return builder.toString();
		}
		int lastIndex = builder.length() - 1;
		builder.deleteCharAt(lastIndex);
		builder.append('>');
		if (textContent != null) {
			builder.append(textContent).append("</").append(tagName)
					.append('>');
		} else if (children.size() != 0) {
			for (XMLElement child : children) {
				builder/*.append('\n')*/.append(child.toString());
			}
			builder/*.append('\n')*/.append("</").append(tagName).append('>');
		}
		return builder.toString();
	}

	public String toFormattedString() {
		return toFormattedString(0);
	}
	
	String toFormattedString(int level) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < level; i++)
			builder.append('\t');
		
		builder.append('<').append(tagName).append(' ');
		for (Entry<String, String> attribute : attributes.entrySet()) {
			builder.append(attribute.getKey()).append("=\"").append(
					attribute.getValue()).append("\" ");
		}
		if (textContent == null && children.size() == 0) {
			builder.append("/>");
			return builder.toString();
		}
		int lastIndex = builder.length() - 1;
		builder.deleteCharAt(lastIndex);
		builder.append('>');
		if (textContent != null) {
			builder.append(textContent).append("</").append(tagName)
					.append('>');
		} else if (children.size() != 0) {
			for (XMLElement child : children) {
				builder.append('\n').append(child.toFormattedString(level + 1));
			}
			builder.append('\n');
			for (int i = 0; i < level; i++)
				builder.append('\t');
			builder.append("</").append(tagName).append('>');
		}
		return builder.toString();
	}
	
	public void writeTo(OutputStream outStream) throws IOException {
		outStream.write(toString().getBytes());
	}
	
	public void writeToFile(String fileName) throws IOException {
		FileOutputStream outStream = new FileOutputStream(new File(fileName));
		writeTo(outStream);
		outStream.flush();
		outStream.close();
	}

	public void appendChild(XMLElement child) {
		children.add(child);
	}

	public void removeChild(XMLElement child) {
		children.remove(child);
	}
	
	public XMLElement getChildAt(int index) {
		return children.get(index);
	}

	@Override
	public Iterator<XMLElement> iterator() {
		return children.iterator();
	}
	
	public Iterable<Entry<String, String>> getAttributes() {
		return attributes.entrySet();
	}
	
	public XMLElement getChildByTagName(String tagName) {
		Iterator<XMLElement> iterator = new ChildIterator(tagName).iterator();
		return iterator.hasNext()? iterator.next(): null;
	}
	
	public Iterable<XMLElement> findByTagName(String tagName) {
		return new ChildIterator(tagName);
	}
	
	public int getChildCount() {
		return children.size();
	}

	class ChildIterator implements Iterable<XMLElement>, Iterator<XMLElement> {

		private XMLElement nextElement;
		private Iterator<XMLElement> iterator;
		private String tagName;
		public ChildIterator(String tagName) {
			super();
			this.tagName = tagName;
			this.iterator = children.iterator();
		}

		@Override
		public Iterator<XMLElement> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			while (iterator.hasNext()) {
				XMLElement child = iterator.next();
				if (child.tagName.equals(tagName)) {
					nextElement = child;
					return true;
				}
			}
			return false;
		}

		@Override
		public XMLElement next() {
			return nextElement;
		}

		@Override
		public void remove() {
			iterator.remove();
		}
		
	}
}
