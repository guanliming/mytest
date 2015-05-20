package com.qianlong;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BorrowInfoBo {
	private BigDecimal borrowAmount;

	private String borrowType;

	private String monthlyRepay;

	private BigDecimal onAccount;

	private byte period;
}
