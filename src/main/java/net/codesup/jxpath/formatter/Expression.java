package net.codesup.jxpath.formatter;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.commons.jxpath.ClassFunctions;
import org.apache.commons.jxpath.CompiledExpression;
import org.apache.commons.jxpath.JXPathContext;

/**
 * Created by klemm0 on 2016-04-25.
 */
public class Expression<R> {
	private CompiledExpression expression = null;
	private final String expressionString;
	private final Locale locale;

	public Expression(final String expressionString) {
		this(expressionString, null);
	}

	public Expression(final String expressionString, final Locale locale) {
		this.expressionString = expressionString;
		this.locale = locale;
	}

	public R evaluate(final Object obj) {
		final JXPathContext context = JXPathContext.newContext(obj);
		context.setFunctions(new ClassFunctions(JXPathFormatExtension.class, "format"));
		if(this.locale != null) {
			context.setLocale(this.locale);
			context.setDecimalFormatSymbols(null, new DecimalFormatSymbols(this.locale));
		}
		if(this.expression == null) {
			this.expression = context.compilePath(this.expressionString);
		}
		return (R)this.expression.getValue(context);
	}
}
