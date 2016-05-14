/*
 * MIT License
 *
 * Copyright (c) 2014 Klemm Software Consulting, Mirko Klemm
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.codesup.jxpath.formatter;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Mirko Klemm 2015-01-22
 */
public final class JXPathFormatExtension {
	private JXPathFormatExtension() {
	}

	public static String isoDate(final Date date) {
		return date(date, "yyyy-MM-dd");
	}

	public static String isoDateTime(final Date date) {
		return date(date, "yyyy-MM-dd'T'HH:mm:ssZ");
	}

	private static Object decode(final Object ref, final Object[] keyValues) {
		return decode(ref, keyValues, null);
	}

	private static Object decode(final Object ref, final Object[] keyValues, final Object def) {
		if (keyValues.length % 2 > 0) {
			throw new IllegalArgumentException("Invalid number of arguments to \"decode\" function: Usage: decode(expr [,key,value]* [,default]?)");
		}
		if (ref == null) {
			for (int i = 0; i < keyValues.length; i += 2) {
				final Object key = keyValues[i];
				final Object val = keyValues[i + 1];
				if (key == null) {
					return val;
				}
			}
			return def;
		} else {
			for (int i = 0; i < keyValues.length; i += 2) {
				final Object key = keyValues[i];
				final Object val = keyValues[i + 1];
				if (ref.equals(key)) {
					return val;
				}
			}
			return def;
		}
	}

	private static Object coalesce(final Object[] refs) {
		for (final Object ref : refs) {
			if (ref != null) return ref;
		}
		return null;
	}

	public static Object decode(final Object ref) {
		return decode(ref, new Object[]{});
	}

	public static Object decode(final Object ref, final Object def) {
		return decode(ref, new Object[]{}, def);
	}

	public static Object decode(final Object ref, final Object key1, final Object val1) {
		return decode(ref, new Object[]{key1, val1});
	}

	public static Object decode(final Object ref, final Object key1, final Object val1, final Object def) {
		return decode(ref, new Object[]{key1, val1}, def);
	}

	public static Object decode(final Object ref, final Object key1, final Object val1, final Object key2, final Object val2) {
		return decode(ref, new Object[]{key1, val1, key2, val2});
	}

	public static Object decode(final Object ref, final Object key1, final Object val1, final Object key2, final Object val2, final Object def) {
		return decode(ref, new Object[]{key1, val1, key2, val2}, def);
	}

	public static Object decode(final Object ref, final Object key1, final Object val1, final Object key2, final Object val2, final Object key3, final Object val3) {
		return decode(ref, new Object[]{key1, val1, key2, val2, key3, val3});
	}

	public static Object decode(final Object ref, final Object key1, final Object val1, final Object key2, final Object val2, final Object key3, final Object val3, final Object def) {
		return decode(ref, new Object[]{key1, val1, key2, val2, key3, val3}, def);
	}

	public static Object decode(final Object ref, final Object key1, final Object val1, final Object key2, final Object val2, final Object key3, final Object val3, final Object key4, final Object val4) {
		return decode(ref, new Object[]{key1, val1, key2, val2, key3, val3, key4, val4});
	}

	public static Object decode(final Object ref, final Object key1, final Object val1, final Object key2, final Object val2, final Object key3, final Object val3, final Object key4, final Object val4, final Object def) {
		return decode(ref, new Object[]{key1, val1, key2, val2, key3, val3, key4, val4}, def);
	}

	public static Object coalesce(final Object ref0, final Object ref1) {
		return coalesce(new Object[]{ref0, ref1});
	}

	public static Object coalesce(final Object ref0, final Object ref1, final Object ref2) {
		return coalesce(new Object[]{ref0, ref1, ref2});
	}

	public static Object coalesce(final Object ref0, final Object ref1, final Object ref2, final Object ref3) {
		return coalesce(new Object[]{ref0, ref1, ref2, ref3});
	}

	public static Object coalesce(final Object ref0, final Object ref1, final Object ref2, final Object ref3, final Object ref4) {
		return coalesce(new Object[]{ref0, ref1, ref2, ref3, ref4});
	}

	public static Object coalesce(final Object ref0, final Object ref1, final Object ref2, final Object ref3, final Object ref4, final Object ref5) {
		return coalesce(new Object[]{ref0, ref1, ref2, ref3, ref4, ref5});
	}

	public static Object coalesce(final Object ref0, final Object ref1, final Object ref2, final Object ref3, final Object ref4, final Object ref5, final Object ref6) {
		return coalesce(new Object[]{ref0, ref1, ref2, ref3, ref4, ref5, ref6});
	}

	public static Object coalesce(final Object ref0, final Object ref1, final Object ref2, final Object ref3, final Object ref4, final Object ref5, final Object ref6, final Object ref7) {
		return coalesce(new Object[]{ref0, ref1, ref2, ref3, ref4, ref5, ref6, ref7});
	}

	public static Object coalesce(final Object ref0, final Object ref1, final Object ref2, final Object ref3, final Object ref4, final Object ref5, final Object ref6, final Object ref7, final Object ref8) {
		return coalesce(new Object[]{ref0, ref1, ref2, ref3, ref4, ref5, ref6, ref7, ref8});
	}

	private static String flattenFormat(final String pattern, final Object... params) {
		final Object[] convertedParams = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			if (params[i] instanceof List) {
				final List<?> list = (List<?>)params[i];
				if (!list.isEmpty()) {
					convertedParams[i] = list.get(0);
				} else {
					convertedParams[i] = params[i];
				}
			} else {
				convertedParams[i] = params[i];
			}
		}
		return MessageFormat.format(pattern, convertedParams);
	}

	public static String format(final String pattern, final Object val) {
		return flattenFormat(pattern, val);
	}

	public static String format(final String pattern, final Object val1, final Object val2) {
		return flattenFormat(pattern, val1, val2);
	}

	public static String format(final String pattern, final Object val1, final Object val2, final Object val3) {
		return flattenFormat(pattern, val1, val2, val3);
	}

	public static String format(final String pattern, final Object val1, final Object val2, final Object val3, final Object val4) {
		return flattenFormat(pattern, val1, val2, val3, val4);
	}

	public static String format(final String pattern, final Object val1, final Object val2, final Object val3, final Object val4, final Object val5) {
		return flattenFormat(pattern, val1, val2, val3, val4, val5);
	}

	public static String format(final String pattern, final Object val1, final Object val2, final Object val3, final Object val4, final Object val5, final Object val6) {
		return flattenFormat(pattern, val1, val2, val3, val4, val5, val6);
	}

	public static String date(final Date date, final String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static int hash(final Object o) {
		return o == null ? 0 : o.hashCode();
	}
}
