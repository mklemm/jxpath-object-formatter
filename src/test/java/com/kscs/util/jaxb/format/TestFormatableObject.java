package com.kscs.util.jaxb.format;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Created by klemm0 on 2015-01-22.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"canonicalName","canonicalNameElement","orderKey", "validFrom", "formatableChild"})
@XmlRootElement(name = "test-formatable-object", namespace = "http://www.kscs.com/util/jaxb/of/test")
public class TestFormatableObject {
	@XmlAttribute(name = "can-name")
	private String canonicalName;
	@XmlElement(name = "can-name")
	private String canonicalNameElement;
	@XmlAttribute(name = "order-key")
	private String orderKey;
	@XmlElement(name="child")
	private TestFormatableChild formatableChild;
	@XmlElement(name = "valid-from")
	private Date validFrom;

	public TestFormatableObject(final String canonicalName, String canonicalNameElement, final String orderKey, final Date validFrom, final TestFormatableChild formatableChild) {
		this.canonicalName = canonicalName;
		this.orderKey = orderKey;
		this.validFrom = validFrom;
		this.formatableChild = formatableChild;
		this.canonicalNameElement = canonicalNameElement;
	}

	public TestFormatableObject() {
	}

	public String getCanonicalName() {
		return this.canonicalName;
	}

	public void setCanonicalName(final String canonicalName) {
		this.canonicalName = canonicalName;
	}

	public String getOrderKey() {
		return this.orderKey;
	}

	public void setOrderKey(final String orderKey) {
		this.orderKey = orderKey;
	}

	public TestFormatableChild getFormatableChild() {
		return this.formatableChild;
	}

	public void setFormatableChild(final TestFormatableChild formatableChild) {
		this.formatableChild = formatableChild;
	}

	public Date getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(final Date validFrom) {
		this.validFrom = validFrom;
	}

	public String getCanonicalNameElement() {
		return this.canonicalNameElement;
	}

	public void setCanonicalNameElement(final String canonicalNameElement) {
		this.canonicalNameElement = canonicalNameElement;
	}
}
