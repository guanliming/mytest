package com.shadow.biz.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.util.Assert;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
public final class Amount2String {
	
	public static String transfer(final BigDecimal toTransfer){
		Assert.isTrue(toTransfer!=null);
		final DecimalFormat df = new DecimalFormat("#.00");
		return df.format(toTransfer.doubleValue());
	}
}
