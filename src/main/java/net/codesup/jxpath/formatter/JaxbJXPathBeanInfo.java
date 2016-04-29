package net.codesup.jxpath.formatter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.jxpath.JXPathBasicBeanInfo;

/**
 * Special BeanInfo that can map XML names to java property names
 */
public class JaxbJXPathBeanInfo extends JXPathBasicBeanInfo {
	private final Map<String,String> propertiesByXmlName;
	private final Map<String,String> propertiesByJavaName;

	public JaxbJXPathBeanInfo(final Class clazz) {
		super(clazz);
		this.propertiesByXmlName = createPropertyMap(clazz);
		this.propertiesByJavaName = createReverseMap(this.propertiesByXmlName);
	}

	public JaxbJXPathBeanInfo(final Class clazz, final boolean atomic) {
		super(clazz, atomic);
		this.propertiesByXmlName = createPropertyMap(clazz);
		this.propertiesByJavaName = createReverseMap(this.propertiesByXmlName);
	}

	public JaxbJXPathBeanInfo(final Class clazz, final Class dynamicPropertyHandlerClass) {
		super(clazz, dynamicPropertyHandlerClass);
		this.propertiesByXmlName = createPropertyMap(clazz);
		this.propertiesByJavaName = createReverseMap(this.propertiesByXmlName);
	}

	private static Map<String,String> createPropertyMap(final Class clazz) {
		final Map<String,String> propertyMap = clazz.getSuperclass() == null ? new HashMap<String,String>(clazz.getDeclaredFields().length) : createPropertyMap(clazz.getSuperclass());
		for(final Field field : clazz.getDeclaredFields()) {
			final XmlElement xmlElementAnnotation= field.getAnnotation(XmlElement.class);
			if(xmlElementAnnotation != null&& xmlElementAnnotation.name() != null && !"##default".equals(xmlElementAnnotation.name())) {
				propertyMap.put(xmlElementAnnotation.name(), field.getName());
			} else {
				final XmlAttribute xmlAttributeAnnotation = field.getAnnotation(XmlAttribute.class);
				if(xmlAttributeAnnotation != null && xmlAttributeAnnotation.name() != null && !"##default".equals(xmlAttributeAnnotation.name())) {
					propertyMap.put(xmlAttributeAnnotation.name(), field.getName());
				} else {
					propertyMap.put(field.getName(), field.getName());
				}
			}
		}
		return propertyMap;
	}

	private static <K,V> Map<V,K> createReverseMap(final Map<K,V> map) {
		final Map<V,K> result = new HashMap<>(map.size());
		for(final Map.Entry<K,V> entry:map.entrySet()) {
			result.put(entry.getValue(), entry.getKey());
		}
		return result;
	}

	@Override
	public String getXPathPropertyName(final String modelPropertyName) {
		return this.propertiesByJavaName.get(modelPropertyName);
	}

	@Override
	public String getModelPropertyName(final String xPathPropertyName) {
		return this.propertiesByXmlName.get(xPathPropertyName);
	}
}
