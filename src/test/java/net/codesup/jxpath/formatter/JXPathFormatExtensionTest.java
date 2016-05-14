package net.codesup.jxpath.formatter;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mirko Klemm 2016-05-04
 */
public class JXPathFormatExtensionTest {
	@Test
	public void testIsoDate() throws Exception {
		Assert.assertEquals("2015-02-01", JXPathFormatExtension.isoDate(new Date(115, 1, 1)));
	}

	@Test
	public void testIsoDateTime() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(2015, Calendar.FEBRUARY, 1, 0, 0, 0);
		System.out.println(cal.getTime().toString());
		Assert.assertEquals("2015-02-01T00:00:00+0100", JXPathFormatExtension.isoDateTime(cal.getTime()));
	}

	@Test
	public void testDecode1() throws Exception {
		Assert.assertNull(JXPathFormatExtension.decode(null));
		Assert.assertNull(JXPathFormatExtension.decode("a"));
	}

	@Test
	public void testDecode2() throws Exception {
		Assert.assertEquals("a", JXPathFormatExtension.decode(null, "a"));
		Assert.assertEquals("b", JXPathFormatExtension.decode("a", "b"));
	}

	@Test
	public void testDecode3() throws Exception {
		Assert.assertNull(JXPathFormatExtension.decode("a", "b", "c"));
		Assert.assertEquals("b", JXPathFormatExtension.decode("a", "a", "b"));
	}

	@Test
	public void testDecode4() throws Exception {
		Assert.assertEquals("d", JXPathFormatExtension.decode("a", "b", "c", "d"));
		Assert.assertEquals("c", JXPathFormatExtension.decode("a", "a", "c", "d"));
	}

	@Test
	public void testDecode5() throws Exception {
		Assert.assertNull(JXPathFormatExtension.decode("a", "b", "c", "d", "e"));
		Assert.assertEquals("e", JXPathFormatExtension.decode("d", "b", "c", "d", "e"));
	}

	@Test
	public void coalesce() throws Exception {
		Assert.assertNull(JXPathFormatExtension.coalesce(null, null));
		Assert.assertEquals("a", JXPathFormatExtension.coalesce("a", "b"));
		Assert.assertEquals("b", JXPathFormatExtension.coalesce(null, "b"));
	}

	@Test
	public void coalesce1() throws Exception {
		Assert.assertNull(JXPathFormatExtension.coalesce(null, null, null));
		Assert.assertEquals("a", JXPathFormatExtension.coalesce("a", "b", "c"));
		Assert.assertEquals("b", JXPathFormatExtension.coalesce(null, "b", "c"));
		Assert.assertEquals("c", JXPathFormatExtension.coalesce(null, null, "c"));
	}
}
