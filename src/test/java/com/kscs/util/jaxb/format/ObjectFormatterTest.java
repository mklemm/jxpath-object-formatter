package com.kscs.util.jaxb.format;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class ObjectFormatterTest {
	public static final String SIMPLE_EXPRESSION_NO_NS = "concat('Formatted Object: \"',@can-name,':(',can-name,'):',@order-key, ' - ', format:isoDateTime(valid-from) ,'\"')";
	public static final String SIMPLE_EXPECTED_RESULT_NO_NS = "Formatted Object: \"test object:(canonical name element):1 - 2015-01-01T00:01:00+0100\"";

	public static final String SIMPLE_EXPRESSION = "concat('Formatted Object: \"',@can-name,':(',tns:can-name,'):',@order-key, ' - ', format:isoDateTime(tns:valid-from) ,'\"')";
	public static final String SIMPLE_EXPECTED_RESULT = "Formatted Object: \"test object:(canonical name element):1 - 2015-01-01T00:01:00+0100\"";

	public static final String COMPLEX_EXPRESSION = "concat('Complex Object: \"',@can-name,':(',tns:can-name,'):',tns:child/@display-name,' - ', tns:child/@name, ' - ', format-number(tns:child/ns1:underwriting-amount,'#.000'),' (spacing=', format-number(tns:child/ns1:spacing,'0.0000%'),') \"')";
	public static final String COMPLEX_EXPECTED_RESULT = "Complex Object: \"FormatableObject:Formatable Child - Child - 9.837.836.365.454.554\"";

	@Test
	public void testFormatNoNS() throws Exception {
		final ObjectFormatter objectFormatter = new ObjectFormatter(ObjectFormatterTest.SIMPLE_EXPRESSION_NO_NS);
		final String formatted = objectFormatter.format(new TestFormatableObject("test object", "canonical name element", "1", new Date(115, 0, 1), new TestFormatableChild("Child", "Formatable Child", new BigInteger("2345678900786454112345"), new BigDecimal("8346524.83465564774555"), 9837836365454554.354736745523435d)));
		System.out.println(formatted);
		Assert.assertEquals(ObjectFormatterTest.SIMPLE_EXPECTED_RESULT_NO_NS, formatted);
	}

	@Test
	public void testFormat() throws Exception {
		final ObjectFormatter objectFormatter = new ObjectFormatter(ObjectFormatterTest.SIMPLE_EXPRESSION, "tns", "http://www.kscs.com/util/jaxb/of/test", "ns1", "http://www.kscs.com/util/jaxb/of/test/sub");
		final String formatted = objectFormatter.format(new TestFormatableObject("test object", "canonical name element", "1", new Date(115, 0, 1), new TestFormatableChild("Child", "Formatable Child", new BigInteger("2345678900786454112345"), new BigDecimal("8346524.83465564774555"), 9837836365454554.354736745523435d)));
		System.out.println(formatted);
		Assert.assertEquals(ObjectFormatterTest.SIMPLE_EXPECTED_RESULT, formatted);
	}

	@Test
	public void testFormatComplex() throws Exception {
		final ObjectFormatter objectFormatter = new ObjectFormatter(ObjectFormatterTest.COMPLEX_EXPRESSION, "tns", "http://www.kscs.com/util/jaxb/of/test", "ns1", "http://www.kscs.com/util/jaxb/of/test/sub");

		final String formatted = objectFormatter.format(new TestFormatableObject("FormatableObject", "canonical name element", "A", new Date(115, 1, 28), new TestFormatableChild("Child", "Formatable Child", new BigInteger("2345678900786454112345"), new BigDecimal("8346524.83465564774555"), 9837836365454554.354736745523435d)));
		System.out.println(formatted);
		Assert.assertNotNull(formatted);
	}
}