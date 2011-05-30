package org.poolc.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParser {
	private static Logger logger = Logger.getLogger(XMLParser.class.getName());
	
	public static XMLElement parse(File file) throws FileNotFoundException, SAXException, IOException {
		return parse(new FileInputStream(file));
	}

	public static XMLElement parse(InputStream in) throws SAXException, IOException {
		XMLElement element = null;
		try {
			element = parse(new InputSource(in));
		} catch (SAXException e) {
			try {
				in.close();
			} catch (IOException ex) {
			}
			
			throw e;
		
		} catch (IOException e) {
			try {
				in.close();
			} catch (IOException ex) {
			}
		
			throw e;
		}
		try {
			in.close();
		} catch (IOException ex) {
		}
		return element;
	}
	
	public static XMLElement parse(Reader reader) throws SAXException, IOException {
		return parse(new InputSource(reader));
	}

	public static XMLElement parse(InputSource in) throws SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setNamespaceAware(true);
		try {
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document xmlDoc = parser.parse(in);
			return traversal(null, xmlDoc.getDocumentElement());
		} catch (ParserConfigurationException e) {
			logger.log(Level.SEVERE, "Cannot create XML Parser", e);
		}
		return new XMLElement("error");
	}

	public static XMLElement parse(URL url) throws SAXException, UnknownHostException, IOException {
		return parse(url.openStream());
	}

	private static XMLElement traversal(XMLElement parent, Element el) {
		XMLElement element = new XMLElement(parent, el.getTagName());
		if (parent != null)
			parent.appendChild(element);
		NamedNodeMap map = el.getAttributes();
		for (int i = 0; i < map.getLength(); i++) {
			Node node = map.item(i);
			if (node instanceof Attr) {
				Attr attr = (Attr) node;
				element.setAttribute(attr.getName(), attr.getValue());
			}
		}
		NodeList list = el.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof Element) {
				traversal(element, (Element) node);
			} else if (node instanceof Text) {
				Text t = (Text) node;
				if (!(t.getData().trim().length() == 0)) {
					element.setTextContent(t.getData());
				}
			}
		}
		return element;
	}
}
