package com.kscs.util.jaxb;

import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.jxpath.ClassFunctions;
import org.apache.commons.jxpath.CompiledExpression;
import org.apache.commons.jxpath.JXPathContext;

/**
 * Expression evaluator, caching compiled expressions, and
 * evaluating on demand
 */
public class Evaluator {
	private final HashMap<String, CompiledExpression> expressions = new HashMap<>();
	private final JXPathContext context;

	public Evaluator(final Object instance) {
		this(instance, null);
	}

	public Evaluator(final Object instance, final Locale locale) {
		this.context = JXPathContext.newContext(instance);
		this.context.setFunctions(new ClassFunctions(JXPathFormatExtension.class, "format"));
		if(locale!= null) {
			this.context.setLocale(locale);
			this.context.setDecimalFormatSymbols(null, new DecimalFormatSymbols(locale));
		}
	}

	public Object evaluate(final String expressionString) {
		CompiledExpression expression = this.expressions.get(expressionString);
		if(expression == null) {
			expression = this.context.compilePath(expressionString);
			this.expressions.put(expressionString, expression);
		}
		return expression.getValue(this.context);
	}
}
