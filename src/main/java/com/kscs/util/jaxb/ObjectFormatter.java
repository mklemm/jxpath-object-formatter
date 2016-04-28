package com.kscs.util.jaxb;

import java.util.Locale;

/**
 * XPath-inspired formatter for object graph
 */
public class ObjectFormatter extends Expression<String> {
	public ObjectFormatter(final String expressionString) {
		super(expressionString);
	}

	public ObjectFormatter(final String expressionString, final Locale locale) {
		super(expressionString, locale);
	}

	public String format(final Object obj) {
		return evaluate(obj);
	}
}
