package com.qianlong.biz;

import java.math.BigDecimal;
import java.util.List;

import com.qianlong.BorrowEntity;
import com.qianlong.RepayEntity;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
public interface IRepayBiz {

	/**
	 * @param borrow 借款信息
	 * @param repayEntityList 还款信息
	 * @param toSavePeriod	待保存期限
	 * @param overduePeriod	逾期期限
	 * @return
	 */
	long save(final BorrowEntity borrow, final List<RepayEntity> repayEntityList, final byte toSavePeriod,
			final byte overduePeriod) ;

	/**
	 * 计算初始每月应还款（不含逾期罚金）
	 * @param borrowAmount	本金
	 * @param toSavePeriod	分期付款总期数
	 * @return	每月应还款
	 */
	BigDecimal calculateMonthlyRepay(final BigDecimal borrowAmount, final byte period);

	byte calculateOverduePeriod(final List<RepayEntity> repayEntityList, final byte currentPeriod, final long borrowId);


	void update(final RepayEntity repay);
}
