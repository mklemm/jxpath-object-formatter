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

package com.kscs.util.jaxb;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mirko Klemm 2015-01-22
 */
public class JXPathFormatExtension {
	public static String isoDate(final Date date) {
		return date(date, "yyyy-MM-dd");
	}

	public static String isoDateTime(final Date date) {
		return date(date, "yyyy-MM-dd'T'HH:MM:ssZ");
	}

	public static String format(final String pattern, final Object val) {
		return MessageFormat.format(pattern, val);
	}
	public static String format(final String pattern, final Object val1, final Object val2) {
		return MessageFormat.format(pattern, val1, val2);
	}
	public static String format(final String pattern, final Object val1, final Object val2, final Object val3) {
		return MessageFormat.format(pattern, val1, val2, val3);
	}
	public static String format(final String pattern, final Object val1, final Object val2, final Object val3, final Object val4) {
		return MessageFormat.format(pattern, val1, val2, val3, val4);
	}
	public static String format(final String pattern, final Object val1, final Object val2, final Object val3, final Object val4, final Object val5) {
		return MessageFormat.format(pattern, val1, val2, val3, val4, val5);
	}
	public static String format(final String pattern, final Object val1, final Object val2, final Object val3, final Object val4, final Object val5, final Object val6) {
		return MessageFormat.format(pattern, val1, val2, val3, val4, val5, val6);
	}

	public static String date(final Date date, final String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
}
