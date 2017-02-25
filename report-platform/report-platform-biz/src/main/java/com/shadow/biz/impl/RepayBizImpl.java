package com.shadow.biz.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.BorrowEntity;
import com.shadow.RepayEntity;
import com.shadow.biz.IRepayBiz;
import com.shadow.biz.constants.SystemConstant;
import com.shadow.dao.IRepayDao;

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
	public byte calculateOverduePeriod(final List<RepayEntity> repayEntityList, final byte currentPeriod,
			final long borrowId) {
		final RepayEntity repayEntity = repayDao.queryLatestBalance(borrowId);
		if (repayEntity != null) {
			return (byte) (repayEntity.getPeriod() + 1);
		}
//		outer: for (int i = 1; i < currentPeriod; i++) {
//			for (final RepayEntity repay : repayEntityList) {
//				if (repay.getPeriod() == i) {
//					continue outer;
//				}
//			}
//			return (byte) i;
//		}
		return 1;
	}

	@Override
	public long save(final BorrowEntity borrow, final List<RepayEntity> repayEntityList, final byte toSavePeriod,
			final byte overduePeriod) {
		final RepayEntity repay = new RepayEntity();
		repay.setBorrowId(borrow.getId());
		repay.setPeriod(toSavePeriod);
		repay.setRepay(BigDecimal.ZERO);
		if (toSavePeriod == 1) {
			final BigDecimal shouldRepay = calculateMonthlyRepay(borrow.getBorrowAmount(), borrow.getPeriod());
			repay.setActualShouldRepay(shouldRepay.subtract(borrow.getOnAccount()));
			repay.setShouldRepayNoOnAccount(shouldRepay);
			repay.setShouldRepay(shouldRepay);
		} else {
			RepayEntity lastRepay = new RepayEntity();
			try {
				lastRepay = getLastRepay(toSavePeriod, borrow.getId());
			} catch (final Exception e) {
				e.printStackTrace(); // It is impossilbe;
			}
			repay.setShouldRepay(calculateShouldPay(borrow, calculateOverdueDays(toSavePeriod, overduePeriod)));
			repay.setShouldRepayNoOnAccount(calculateShouldRepayNoOnAccount(repay.getShouldRepay(),
					lastRepay.getShouldRepayNoOnAccount()));
			repay.setActualShouldRepay(calculateActualShouldRepay(repay.getShouldRepay(),
					lastRepay.getActualShouldRepay(), borrow.getOnAccount()));
		}
		repay.setBalance("N");
		return repayDao.save(repay);
	}

	@Override
	public void update(final RepayEntity repay) {
		repayDao.update(repay);
	}

	/**
	 * @param shouldRepayNoOnAccount
	 *            本期应还金额（不含挂账）
	 * @param onAccount
	 *            挂账金额
	 * @return 本期应还金额
	 */
	private BigDecimal calculateActualShouldRepay(final BigDecimal shouldRepay, final BigDecimal lastActualShouldRepay,
			final BigDecimal onAccount) {
		if (lastActualShouldRepay.compareTo(BigDecimal.ZERO) != 0) {
			return shouldRepay.add(lastActualShouldRepay);
		} else {
			return shouldRepay.subtract(onAccount);
		}
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
	 * 逾期天数
	 * 
	 * @param toSavePeriod
	 *            待保存期数
	 * @param overduePeriod
	 *            逾期开始期数
	 * @return 逾期天数
	 */
	private int calculateOverdueDays(final byte toSavePeriod, final byte overduePeriod) {
		return (toSavePeriod - overduePeriod) >= 0 ? (toSavePeriod - overduePeriod) * 30 : 0;
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

	/**
	 * @param shouldRepay
	 *            本期应还
	 * @param lastShouldRepay
	 *            上期应还（不含挂账）
	 * @return 本期应还(不含挂账)
	 */
	private BigDecimal calculateShouldRepayNoOnAccount(final BigDecimal shouldRepay,
			final BigDecimal lastShouldRepayNoOnAccount) {
		return shouldRepay.add(lastShouldRepayNoOnAccount);
	}

	private RepayEntity getLastRepay(final byte toSavePeriod, final long borrowId) throws Exception {
		final RepayEntity result = repayDao.queryByBorrowIdAndPeriod(borrowId, (byte) (toSavePeriod - 1));
		if (result == null) {
			throw new Exception("找不到上期还款信息");
		} else {
			return result;
		}
	}

}
