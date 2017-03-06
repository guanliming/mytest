package com.shadow.biz.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author guanliming
 *
 */
public class NameUtilsTest {

	@Test
	public void testGetDBSchemaName() {
		Assert.assertEquals(NameUtils.getDBSchemaName("FinianceCode"), "finiance_code");
	}

	@Test
	public void testTransferToCamel() {
		Assert.assertEquals(NameUtils.transferToCamel("a_b_c"), "aBC");
	}

}
