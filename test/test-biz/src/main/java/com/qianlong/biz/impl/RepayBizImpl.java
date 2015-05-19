package com.qianlong.biz.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianlong.BorrowEntity;
import com.qianlong.RepayEntity;
import com.qianlong.biz.IRepayBiz;
import com.qianlong.biz.constants.SystemConstant;
import com.qianlong.dao.IRepayDao;

/**
 * @author 管黎明
 * 
 *         All rights reserved.
 */
@Service
class RepayBizImpl implements IRepayBiz {
	@Autowired
	private IRepayDao repayDao;

	@Override
	public BigDecimal calculateMonthlyRepay(final BigDecimal borrowAmount, final byte period) {
		return new BigDecimal(calculateInterest(borrowAmount, period).add(borrowAmount).doubleValue() / period);
	}

	@Override
	public byte calculateOverduePeriod(final List<RepayEntity> repayEntityList, final byte currentPeriod) {
		outer: for (int i = 1; i < currentPeriod; i++) {
			for (final RepayEntity repay : repayEntityList) {
				if (repay.getPeriod() == i) {
					continue outer;
				}
			}
			return (byte) i;
		}
		return 0;
	}

	@Override
	public long save(final BorrowEntity borrow, final List<RepayEntity> repayEntityList, final byte toSavePeriod,
			final byte overduePeriod)  {
		final RepayEntity repay = new RepayEntity();
		repay.setBorrowId(borrow.getId());
		repay.setPeriod(toSavePeriod);
		repay.setRepay(BigDecimal.ZERO);
		if (toSavePeriod == 1) {
			final BigDecimal shouldRepay = calculateMonthlyRepay(borrow.getBorrowAmount(), borrow.getPeriod());
			repay.setActualShouldRepay(shouldRepay);
			repay.setShouldRepay(shouldRepay);
		} else {
			RepayEntity lastRepay = new RepayEntity();
			try {
				lastRepay = getLastRepay(repayEntityList, toSavePeriod);
			} catch (final Exception e) {
				e.printStackTrace(); //It is impossilbe;
			}
			repay.setShouldRepay(calculateShouldPay(borrow, (toSavePeriod - overduePeriod) * 30));
			repay.setActualShouldRepay(calculateActualShouldRepay(repay.getShouldRepay(),lastRepay.getShouldRepay(),borrow.getOnAccount()));
		}
		return repayDao.save(repay);
	}
	
	private BigDecimal calculateActualShouldRepay(final BigDecimal shouldRepay,final BigDecimal lastShouldRepay,final BigDecimal onAccount){
		return shouldRepay.add(lastShouldRepay).add(onAccount);
	}

	private BigDecimal calculateCaptialAndInterest(final BigDecimal borrowAmount, final byte period) {
		return borrowAmount.add(calculateInterest(borrowAmount, period));
	}

	/**
	 * 计算总利息
	 * 
	 * @param borrowAmount
	 *            本金
	 * @param period
	 *            分期付款总期数
	 * @return
	 */
	private BigDecimal calculateInterest(final BigDecimal borrowAmount, final byte period) {
		return new BigDecimal(borrowAmount.multiply(new BigDecimal(SystemConstant.DAILY_RATE)).doubleValue() * period
				* 30);
	}

	/**
	 * 计算逾期罚息
	 * 
	 * @param borrowAmount
	 *            本金
	 * @param overdueDays
	 *            逾期天数
	 * @return
	 */
	private BigDecimal calculateOverdueInterest(final BigDecimal borrowAmount, final int overdueDays) {
		return new BigDecimal(borrowAmount.doubleValue() * SystemConstant.OVERDUE_INTEREST * overdueDays);
	}

	/**
	 * 计算理论上待保存还款信息期限的应还金额 公式:(借款金额+借款利率+逾期罚息)/借款期数
	 * 
	 * @param borrowAmount
	 *            本金
	 * @param lastRepayInfo
	 *            上次
	 * @param overdueDays
	 * @return
	 */
	private BigDecimal calculateShouldPay(final BorrowEntity borrow, final int overdueDays) {
		final BigDecimal overdueInterest = calculateOverdueInterest(borrow.getBorrowAmount(), overdueDays);
		return new BigDecimal(calculateCaptialAndInterest(borrow.getBorrowAmount(), borrow.getPeriod()).add(
				overdueInterest).doubleValue()
				/ borrow.getPeriod());
	}

	private RepayEntity getLastRepay(final List<RepayEntity> repayEntityList, final byte toSavePeriod) throws Exception {
		for (final RepayEntity targetRepay : repayEntityList) {
			if (targetRepay.getPeriod() == (toSavePeriod - 1)) {
				return targetRepay;
			}
		}
		throw new Exception("找不到上期还款信息");
	}

}
