package com.qianlong;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author 管黎明
 * 
 *         All rights reserved.
 */
@Data
public class BorrowParamBo {
	private BigDecimal borrowAmount;
	private String mode;
	private byte period;
}
