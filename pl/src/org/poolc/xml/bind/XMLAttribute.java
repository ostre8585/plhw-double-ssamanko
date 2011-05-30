package org.poolc.xml.bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XMLAttribute {
	public String nodeName() default "##default";
	
	public boolean base64() default false;
}
