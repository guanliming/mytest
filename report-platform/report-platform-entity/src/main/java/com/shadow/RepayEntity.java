package com.shadow;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Data
public class RepayEntity {
	private BigDecimal actualShouldRepay;
	private String balance;
	private long borrowId;
	private long id;
	private byte period;
	private BigDecimal repay;
	private BigDecimal shouldRepay;
	private BigDecimal shouldRepayNoOnAccount;
}
