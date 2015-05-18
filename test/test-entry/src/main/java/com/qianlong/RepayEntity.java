package com.qianlong;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Data
public class RepayEntity {
	private long borrowId;
	private long id;
	private byte period;
	private BigDecimal repay;
	private BigDecimal shouldRepay;
}
