package com.kscs.util.jaxb;

import org.apache.commons.jxpath.JXPathBeanInfo;
import org.apache.commons.jxpath.JXPathBeanInfoFactory;

/**
 * Creates customized beanInfo objects for JXPath
 */
public class JaxbJXPathBeanInfoFactory implements JXPathBeanInfoFactory {
	@Override
	public JXPathBeanInfo createBeanInfo(final Class clazz) {
		return new JaxbJXPathBeanInfo(clazz);
	}
}
