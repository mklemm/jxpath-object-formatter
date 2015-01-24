package com.kscs.util.jaxb;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
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


	public ObjectFormatter(final String expression) {
		this.expressionString = expression;
		this.locale = null;
	}

	public ObjectFormatter(final String expression, final Locale locale) {
		this.expressionString = expression;
		this.locale = locale;

	}

	public String format(final Object obj) {
		final JXPathContext context = JXPathContext.newContext(obj);
		context.setFunctions(new ClassFunctions(JXPathFormatExtension.class, "format"));
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
}
