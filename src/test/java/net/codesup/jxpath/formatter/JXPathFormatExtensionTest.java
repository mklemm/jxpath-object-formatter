package net.codesup.jxpath.formatter;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Mirko Klemm 2016-05-04
 */
public class JXPathFormatExtensionTest {
	@Test
	public void isoDate() throws Exception {
		Assert.assertEquals(JXPathFormatExtension.isoDate(new Date(115, 1, 1)), "2015-02-01");
	}

	@Test
	public void isoDateTime() throws Exception {
		Assert.assertEquals(JXPathFormatExtension.isoDateTime(new Date(115, 1, 1)), "2015-02-01T00:00:00+0100");
	}

	@Test
	public void decode() throws Exception {
		Assert.assertEquals("d", JXPathFormatExtension.decode("a", "b", "c", "d"));
		Assert.assertEquals("b", JXPathFormatExtension.decode("a", "a", "b", "d"));
	}

	@Test
	public void decode1() throws Exception {
		Assert.assertEquals("f", JXPathFormatExtension.decode("a", "b", "c", "d", "e", "f"));
		Assert.assertEquals("b", JXPathFormatExtension.decode("a", "a", "b", "c", "d", "f"));
	}

	@Test
	public void decode2() throws Exception {
		Assert.assertEquals(JXPathFormatExtension.decode("a", "b", "c", "d"), "d");
		Assert.assertEquals(JXPathFormatExtension.decode("a", "a", "b", "d"), "b");
	}

	@Test
	public void decode3() throws Exception {
	}

	@Test
	public void decode4() throws Exception {
	}

	@Test
	public void decode5() throws Exception {
	}

	@Test
	public void decode6() throws Exception {
	}

	@Test
	public void decode7() throws Exception {
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

	@Test
	public void coalesce2() throws Exception {
	}

	@Test
	public void coalesce3() throws Exception {
	}

	@Test
	public void coalesce4() throws Exception {
	}

	@Test
	public void coalesce5() throws Exception {
	}

	@Test
	public void coalesce6() throws Exception {
	}

	@Test
	public void coalesce7() throws Exception {
	}

	@Test
	public void format() throws Exception {
	}

	@Test
	public void format1() throws Exception {
	}

	@Test
	public void format2() throws Exception {
	}

	@Test
	public void format3() throws Exception {
	}

	@Test
	public void format4() throws Exception {
	}

	@Test
	public void format5() throws Exception {
	}

	@Test
	public void date() throws Exception {
	}

	@Test
	public void hash() throws Exception {
	}
}
