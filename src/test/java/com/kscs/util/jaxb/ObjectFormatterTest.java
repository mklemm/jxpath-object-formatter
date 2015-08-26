package com.kscs.util.jaxb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class ObjectFormatterTest {
	public static final String SIMPLE_EXPRESSION = "concat('Formatted Object: \"',@can-name,':',@order-key, ' - ', format:isoDateTime(valid-from) ,'\"')";
	public static final String SIMPLE_EXPECTED_RESULT = "Formatted Object: \"test object:1 - 2015-01-01T00:01:00+0100\"";

	public static final String COMPLEX_EXPRESSION = "concat('Complex Object: \"',@can-name,':',child/@display-name,' - ', child/@name, ' - ', format-number(child/underwriting-amount,'#.000'),' (spacing=', format-number(child/spacing,'0.0000%'),') \"')";
	public static final String COMPLEX_EXPECTED_RESULT = "Complex Object: \"FormatableObject:Formatable Child - Child - 9.837.836.365.454.554\"";

	public static final String CUSTOM_FUNC_EXPRESSION = "format:format('Custom func oject: \"{0}\":{1}{2}',@can-name, child/@display-name, format:decode(string(child/@name), 'ifrs', ' - IFRS', ''))";

	@Test
	public void testFormat() throws Exception {
		final ObjectFormatter objectFormatter = new ObjectFormatter(ObjectFormatterTest.SIMPLE_EXPRESSION);
		final String formatted = objectFormatter.format(new TestFormatableObject("test object", "1", new Date(115,0,1), new TestFormatableChild("Child", "Formatable Child", new BigInteger("2345678900786454112345"), new BigDecimal("8346524.83465564774555"), 9837836365454554.354736745523435d)));
		System.out.println(formatted);
		Assert.assertEquals(ObjectFormatterTest.SIMPLE_EXPECTED_RESULT, formatted);
	}

	@Test
	public void testFormatComplex() throws Exception {
		final ObjectFormatter objectFormatter = new ObjectFormatter(ObjectFormatterTest.COMPLEX_EXPRESSION, Locale.UK);

		final String formatted = objectFormatter.format(new TestFormatableObject("FormatableObject", "A", new Date(115,1,28), new TestFormatableChild("Child", "Formatable Child", new BigInteger("2345678900786454112345"), new BigDecimal("8346524.83465564774555"), 9837836365454554.354736745523435d)));
		System.out.println(formatted);
		Assert.assertNotNull(formatted);
	}

	@Test
	public void testFormatCustomFunc() throws Exception {
		final ObjectFormatter objectFormatter = new ObjectFormatter(ObjectFormatterTest.CUSTOM_FUNC_EXPRESSION, Locale.UK);

		final String formatted = objectFormatter.format(new TestFormatableObject("FormatableObject", "A", new Date(115,1,28), new TestFormatableChild("ifrs", "Formatable Child", new BigInteger("2345678900786454112345"), new BigDecimal("8346524.83465564774555"), 9837836365454554.354736745523435d)));
		System.out.println(formatted);
		Assert.assertNotNull(formatted);
	}
}
