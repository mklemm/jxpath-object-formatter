package com.kscs.util.jaxb.format;

import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.jxpath.ClassFunctions;
import org.apache.commons.jxpath.CompiledExpression;
import org.apache.commons.jxpath.JXPathContext;

/**
 * XPath-inspired formatter for object graph
 */
public class ObjectFormatter {
	private CompiledExpression expression = null;
	private final String expressionString;
	private final Locale locale;
	private final Map<String,String> namespaces;

	public ObjectFormatter(final String expression) {
		this(expression, null, Collections.<String, String>emptyMap());
	}

	public ObjectFormatter(final String expression, final Locale locale) {
		this(expression, locale, Collections.<String,String>emptyMap());
	}

	public ObjectFormatter(final String expression, final Map<String,String> namespaces) {
		this(expression, null, namespaces);
	}

	public ObjectFormatter(final String expression, final Locale locale, final Map<String,String> namespaces) {
		this.expressionString = expression;
		this.locale = locale;
		this.namespaces = namespaces;
	}

	public ObjectFormatter(final String expression, final String... xmlnsDecls) {
		this(expression, null, xmlnsDecls);
	}

	public ObjectFormatter(final String expression, final Locale locale,  final String... xmlnsDecls) {
		this(expression, locale, createMap(xmlnsDecls));
	}

	public String format(final Object obj) {
		final JXPathContext context = JXPathContext.newContext(obj);
		context.setFunctions(new ClassFunctions(JXPathFormatExtension.class, "format"));
		for(final Map.Entry<String,String> entry : this.namespaces.entrySet()) {
			context.registerNamespace(entry.getKey(), entry.getValue());
		}
		if(this.locale != null) {
			context.setLocale(this.locale);
			context.setDecimalFormatSymbols(null, new DecimalFormatSymbols(this.locale));
		}
		if(this.expression == null) {
			this.expression = context.compilePath(this.expressionString);
		}
		final Object val = this.expression.getValue(context);
		return val == null ? null : val.toString();
	}

	private static <E> Map<E,E> createMap(final E[] keyValues) {
		final Map<E,E> map = new LinkedHashMap<>();
		for(int i = 0; i < keyValues.length; i++) {
			map.put(keyValues[i], keyValues[++i]);
		}
		return map;
	}
}
