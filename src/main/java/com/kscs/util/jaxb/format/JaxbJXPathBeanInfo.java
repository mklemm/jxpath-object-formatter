package com.kscs.util.jaxb.format;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.jxpath.JXPathBasicBeanInfo;
import org.apache.commons.jxpath.JXPathException;
import org.apache.commons.jxpath.util.JXPathPropertyDescriptor;
import org.apache.commons.jxpath.util.PropertyIdentifier;

/**
 * Special BeanInfo that can map XML names to java property names
 */
public class JaxbJXPathBeanInfo extends JXPathBasicBeanInfo {
	public static final String DEFAULT_ELEMENT_NAME = "##default";
	private final String targetNamespace;
	private final Comparator<JXPathPropertyDescriptor> propertyComparator;

	public JaxbJXPathBeanInfo(final Class<?> clazz, final boolean namespaceAware) {
		super(clazz);
		this.targetNamespace = namespaceAware ? getTargetNamespace(clazz) : null;
		this.propertyComparator = createPropertyComparator(clazz);
	}


	public JaxbJXPathBeanInfo(final Class<?> clazz, final boolean atomic, final boolean namespaceAware) {
		super(clazz, atomic);
		this.targetNamespace = namespaceAware ? getTargetNamespace(clazz) : null;
		this.propertyComparator = createPropertyComparator(clazz);
	}

	public JaxbJXPathBeanInfo(final Class<?> clazz, final Class dynamicPropertyHandlerClass, final boolean namespaceAware) {
		super(clazz, dynamicPropertyHandlerClass);
		this.targetNamespace = namespaceAware ? getTargetNamespace(clazz) : null;
		this.propertyComparator = createPropertyComparator(clazz);
	}

	private static Comparator<JXPathPropertyDescriptor> createPropertyComparator(final Class<?> beanClass) {
		final Map<String, Integer> propMap = new HashMap<>();
		getPropertyOrder(propMap, beanClass, 0);
		return new Comparator<JXPathPropertyDescriptor>() {
			@Override
			public int compare(final JXPathPropertyDescriptor o1, final JXPathPropertyDescriptor o2) {
				final Integer index1 = propMap.get(o1.getPropertyDescriptor().getName());
				final Integer index2 = propMap.get(o2.getPropertyDescriptor().getName());
				return index1.compareTo(index2);
			}
		};
	}

	private static int getPropertyOrder(final Map<String, Integer> propMap, final Class<?> beanClass, final int offset) {
		int index = offset;
		if (beanClass.getSuperclass() != null) {
			index = getPropertyOrder(propMap, beanClass.getSuperclass(), offset);
		}
		final XmlType xmlTypeAnnotation = beanClass.getAnnotation(XmlType.class);
		if (xmlTypeAnnotation != null && xmlTypeAnnotation.propOrder() != null && !xmlTypeAnnotation.propOrder()[0].isEmpty()) {
			for (final String propName : xmlTypeAnnotation.propOrder()) {
				propMap.put(propName, index++);
			}
		} else {
			try {
				final BeanInfo beanInfo = Introspector.getBeanInfo(beanClass, beanClass.getSuperclass());
				for (final PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
					propMap.put(propertyDescriptor.getName(), index++);
				}
			} catch (final IntrospectionException e) {
				throw new JXPathException(e);
			}
		}
		return index;
	}

	private static Field findField(final Class<?> beanClass, final String fieldName) throws NoSuchFieldException {
		final Field field = beanClass.getDeclaredField(fieldName);
		return field != null ? field : (beanClass.getSuperclass() != null ? findField(beanClass.getSuperclass(), fieldName) : null);
	}

	private String getTargetNamespace(final Class<?> clazz) {
		final XmlRootElement xmlRootElementAnnotation = clazz.getAnnotation(XmlRootElement.class);
		if (xmlRootElementAnnotation == null) {
			final XmlType xmlTypeAnnotation = clazz.getAnnotation(XmlType.class);
			return xmlTypeAnnotation == null ? null : xmlTypeAnnotation.namespace();
		} else {
			return xmlRootElementAnnotation.namespace();
		}
	}

	@Override
	protected String createKey(final PropertyIdentifier propertyIdentifier) {
		return propertyIdentifier.toString();
	}

	@Override
	public String getTargetNamespace() {
		return this.targetNamespace;
	}

	@Override
	protected Comparator<JXPathPropertyDescriptor> getPropertyOrderComparator() {
		return this.propertyComparator;
	}

	@Override
	protected JXPathPropertyDescriptor createMappingDescriptor(final PropertyDescriptor propertyDescriptor) {
		try {
			final Field field = findField(this.clazz, propertyDescriptor.getName());
			final String localName;
			final String namespaceUri;
			final boolean attribute;
			final XmlElement xmlElementAnnotation = field.getAnnotation(XmlElement.class);
			if (xmlElementAnnotation != null && xmlElementAnnotation.name() != null) {
				localName = JaxbJXPathBeanInfo.DEFAULT_ELEMENT_NAME.equals(xmlElementAnnotation.name()) ? field.getName() : xmlElementAnnotation.name();
				namespaceUri = JaxbJXPathBeanInfo.DEFAULT_ELEMENT_NAME.equals(xmlElementAnnotation.namespace()) ? this.targetNamespace : xmlElementAnnotation.namespace();
				attribute = false;
			} else {
				final XmlAttribute xmlAttributeAnnotation = field.getAnnotation(XmlAttribute.class);
				if (xmlAttributeAnnotation != null && xmlAttributeAnnotation.name() != null) {
					localName = JaxbJXPathBeanInfo.DEFAULT_ELEMENT_NAME.equals(xmlAttributeAnnotation.name()) ? field.getName() : xmlAttributeAnnotation.name();
					namespaceUri = JaxbJXPathBeanInfo.DEFAULT_ELEMENT_NAME.equals(xmlAttributeAnnotation.namespace()) ? this.targetNamespace : xmlAttributeAnnotation.namespace();
					attribute = true;
				} else {
					localName = field.getName();
					namespaceUri = this.targetNamespace;
					attribute = false;
				}
			}
			return new JXPathPropertyDescriptor(new PropertyIdentifier(namespaceUri, localName, attribute), propertyDescriptor);
		} catch (final Exception e) {
			throw new JXPathException(e);
		}
	}
}
