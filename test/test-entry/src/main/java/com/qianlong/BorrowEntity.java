package com.qianlong;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Data
public class BorrowEntity {
	private BigDecimal borrowAmount;
	private Date borrowTime;
	private String borrowType;
	private long borrowUserId;
	private String completelyPayOff;
	private long id;
	private BigDecimal onAccount;
	private byte period;
	private BigDecimal repayAll;
	private BigDecimal shouldRepayAll;
}
