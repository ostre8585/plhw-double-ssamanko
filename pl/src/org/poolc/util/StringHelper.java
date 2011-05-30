package org.poolc.util;


public class StringHelper {
	public static String toUnderscored(String camelcased) {
		StringBuilder builder = new StringBuilder();
		for (char ch: camelcased.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				builder.append('_');
				builder.append(Character.toLowerCase(ch));
			} else {
				builder.append(ch);
			}
		}
		return builder.toString();
	}
	
	public static String toCamelcase(String underscored) {
		return toCamelcase(underscored, false);
	}
	
	public static String toCamelcase(String underscored, boolean upperFirstChar) {
		StringBuilder builder = new StringBuilder();
		boolean upperFlag = false;
		for (char ch: underscored.toCharArray()) {
			if (upperFlag) {
				builder.append(Character.toUpperCase(ch));
				upperFlag = false;
			} else if (ch == '_') {
				upperFlag = true;
			} else {
				builder.append(ch);
			}
		}
		if (upperFirstChar)
			builder.replace(0, 1, Character.toString(Character.toUpperCase(builder.charAt(0)))); 
		return builder.toString();
	}
	
	public static String extract(String subject, String start, String end) {
		if (subject == null)
			return null;
		
		int startPosition = subject.indexOf(start);
		if (startPosition < 0) 
			return null;
		
		startPosition += start.length();
		int endPosition = subject.indexOf(end, startPosition);
		if (endPosition < 0)
			return null;
		
		return subject.substring(startPosition, endPosition);
	}
	
	public static String toFirstCharUpper(String string) {
		return Character.toUpperCase(string.charAt(0)) + string.substring(1);
	}
	
	public static String[] splitAndRemove(String subject, String delimiter, String remove) {
		return subject.replace(remove, "").split(delimiter);
	}

	public static String implode(String delimiter, String[] parts) {
		StringBuilder builder = new StringBuilder();
		builder.append(parts[0]);
		for (int i = 1; i < parts.length; i++) {
			builder.append(delimiter).append(parts[i]);
		}
		return builder.toString();
	}
	
	public static String toXMLNodeName(String name) {
		name = toUnderscored(name).replace('_', '-');
		return name.startsWith("-")? name.substring(1): name;
	}
	
	public static String fromXMLNodeNameToCamelCase(String name) {
		return toCamelcase(name.replace('-', '_'));
	}

	public static String stripTags(String line) {
		StringBuilder builder = new StringBuilder();
		boolean isTag = false;
		for (char ch : line.toCharArray()) {
			if (ch == '<') {
				isTag = true;
			}
			if (!isTag)
				builder.append(ch);
			if (ch == '>') {
				isTag = false;
			}
		}
		return builder.toString();
	}
}
