package com.kscs.util.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.NamespaceContext;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.jxpath.JXPathBasicBeanInfo;
import org.apache.commons.jxpath.PropertyXMLMapping;
import org.apache.commons.jxpath.ri.QName;

/**
 * Special BeanInfo that can map XML names to java property names
 */
public class JaxbJXPathBeanInfo extends JXPathBasicBeanInfo {
	public static final String DEFAULT_ELEMENT_NAME = "##default";
	private final NamespaceContext namespaceContext;
	private final Map<PropertyXMLMapping,PropertyXMLMapping> propertiesByXmlName = new LinkedHashMap<>();
	private final Map<String,PropertyXMLMapping> propertiesByJavaName = new LinkedHashMap<>();
	private final String targetNamespace;

	public JaxbJXPathBeanInfo(final NamespaceContext namespaceContext, final Class<?> clazz) {
		super(clazz);
		this.namespaceContext = namespaceContext;
		this.targetNamespace = getTargetNamespace(clazz);
		initMaps(clazz);
	}

	public JaxbJXPathBeanInfo(final NamespaceContext namespaceContext, final Class<?> clazz, final boolean atomic) {
		super(clazz, atomic);
		this.namespaceContext = namespaceContext;
		this.targetNamespace = getTargetNamespace(clazz);
		initMaps(clazz);
	}

	public JaxbJXPathBeanInfo(final NamespaceContext namespaceContext, final Class<?> clazz, final Class dynamicPropertyHandlerClass) {
		super(clazz, dynamicPropertyHandlerClass);
		this.namespaceContext = namespaceContext;
		this.targetNamespace = getTargetNamespace(clazz);
		initMaps(clazz);
	}

	private void initMaps(final Class<?> clazz) {
		if(clazz.getSuperclass() != null) {
			initMaps(clazz);
		}
		for(final Field field : clazz.getDeclaredFields()) {
			final String localName;
			final String namespaceUri;
			final boolean attribute;
			final XmlElement xmlElementAnnotation= field.getAnnotation(XmlElement.class);
			if(xmlElementAnnotation != null&& xmlElementAnnotation.name() != null) {
				localName = JaxbJXPathBeanInfo.DEFAULT_ELEMENT_NAME.equals(xmlElementAnnotation.name()) ? field.getName() : xmlElementAnnotation.name();
				namespaceUri = JaxbJXPathBeanInfo.DEFAULT_ELEMENT_NAME.equals(xmlElementAnnotation.namespace()) ? this.targetNamespace : xmlElementAnnotation.namespace();
				attribute = false;
			} else {
				final XmlAttribute xmlAttributeAnnotation = field.getAnnotation(XmlAttribute.class);
				if(xmlAttributeAnnotation != null && xmlAttributeAnnotation.name() != null) {
					localName = JaxbJXPathBeanInfo.DEFAULT_ELEMENT_NAME.equals(xmlAttributeAnnotation.name()) ? field.getName() : xmlAttributeAnnotation.name();
					namespaceUri = JaxbJXPathBeanInfo.DEFAULT_ELEMENT_NAME.equals(xmlAttributeAnnotation.namespace()) ? this.targetNamespace : xmlAttributeAnnotation.namespace();
					attribute = true;
				} else {
					localName = field.getName();
					namespaceUri = this.targetNamespace;
					attribute = false;
				}
			}
			final PropertyXMLMapping propertyXMLMapping = new PropertyXMLMapping(getPropertyDescriptor(field.getName()), namespaceUri, localName, attribute);
			this.propertiesByJavaName.put(field.getName(), propertyXMLMapping);
			this.propertiesByXmlName.put(propertyXMLMapping, propertyXMLMapping);
		}
	}

	private String getTargetNamespace(final Class<?> clazz) {
		final XmlRootElement xmlRootElementAnnotation = clazz.getAnnotation(XmlRootElement.class);
		if(xmlRootElementAnnotation == null) {
			final XmlType xmlTypeAnnotation = clazz.getAnnotation(XmlType.class);
			return xmlTypeAnnotation == null ? "" : xmlTypeAnnotation.namespace();
		} else {
			return xmlRootElementAnnotation.namespace();
		}
	}

	@Override
	public PropertyXMLMapping getPropertyXMLMapping(final String modelPropertyName) {
		return this.propertiesByJavaName.get(modelPropertyName);
	}

	@Override
	public PropertyXMLMapping getPropertyXMLMapping(final QName xmlPropertyName, final boolean attribute) {
		final String namespaceUri = namespaceContext.getNamespaceURI(xmlPropertyName.getPrefix());
		return this.propertiesByXmlName.get(new PropertyXMLMapping(null, namespaceUri, xmlPropertyName.getName(), attribute));
	}
}
