package com.kscs.util.jaxb;

import javax.xml.namespace.NamespaceContext;

import org.apache.commons.jxpath.JXPathBeanInfo;
import org.apache.commons.jxpath.JXPathBeanInfoFactory;

/**
 * Creates customized beanInfo objects for JXPath
 */
public class JaxbJXPathBeanInfoFactory implements JXPathBeanInfoFactory {
	@Override
	public JXPathBeanInfo createBeanInfo(final NamespaceContext namespaceContext, final Class clazz) {
		return new JaxbJXPathBeanInfo(namespaceContext, clazz);
	}
}
