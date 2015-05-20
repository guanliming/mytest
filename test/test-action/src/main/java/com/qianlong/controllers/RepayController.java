package com.qianlong.controllers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.qianlong.BorrowEntity;
import com.qianlong.RepayEntity;
import com.qianlong.biz.IBorrowBiz;
import com.qianlong.biz.IRepayBiz;
import com.qianlong.biz.IUserBiz;
import com.qianlong.biz.constants.SystemConstant;
import com.qianlong.biz.constants.TimeConstant;
import com.qianlong.dao.IRepayDao;

/**
 * @author 管黎明
 * 
 *         All rights reserved.
 */
@Controller
public class RepayController {
	@Autowired
	private IBorrowBiz borrowBiz;
	@Autowired
	private IRepayBiz repayBiz;
	@Autowired
	private IRepayDao repayDao;
	@Autowired
	private IUserBiz userBiz;

	@RequestMapping("/repay")
	public ModelAndView repay(final HttpSession session, @RequestParam("repayAccount") final BigDecimal repayAccount)
			throws Exception {
		final BorrowEntity borrowEntity = obtainCurrentUserBorrowInfo(session);
		final byte currentPeriod = currentPeriod(borrowEntity.getBorrowTime(), borrowEntity.getPeriod());
		// 补充插入到当前期数(只插入不存在的)
		acomplishForegone(borrowEntity, currentPeriod);
		final RepayEntity currentRepayEntity = repayDao.queryByBorrowIdAndPeriod(borrowEntity.getId(), currentPeriod);
		rawRepay(currentRepayEntity, borrowEntity, repayAccount);
		return new ModelAndView("repaySuccess");
	}

	@RequestMapping("/repayPage")
	public ModelAndView repayPage(final HttpSession session) throws Exception {
		final BorrowEntity borrowEntity = obtainCurrentUserBorrowInfo(session);
		final byte currentPeriod = currentPeriod(borrowEntity.getBorrowTime(), borrowEntity.getPeriod());
		// 补充插入到当前期数(只插入不存在的)
		acomplishForegone(borrowEntity, currentPeriod);
		final RepayEntity currentRepayEntity = repayDao.queryByBorrowIdAndPeriod(borrowEntity.getId(), currentPeriod);
		final ModelAndView mv = new ModelAndView("repayPage");
		mv.addObject("actualShouldRepay", currentRepayEntity.getActualShouldRepay());
		return mv;
	}

	/**
	 * 补充当前期数之前的还款信息
	 * 
	 * @param borrow
	 *            借款信息
	 * @param currentPeriod
	 *            当前期数
	 */
	private void acomplishForegone(final BorrowEntity borrow, final byte currentPeriod) {
		final List<RepayEntity> repayEntityList = repayDao.queryByBorrowId(borrow.getId());
		final byte overduePeriod = repayBiz.calculateOverduePeriod(repayEntityList, currentPeriod, borrow.getId());
		outer: for (byte i = 1; i <= currentPeriod; i++) {
			for (final RepayEntity repay : repayEntityList) {
				if (repay.getPeriod() == i) {
					continue outer;
				}
			}
			repayBiz.save(borrow, repayEntityList, i, overduePeriod);
		}
	}

	private byte currentPeriod(final Date borrowTime, final byte totalPeriod) {
		final long borrowUpToNow = System.currentTimeMillis() - borrowTime.getTime() - TimeConstant.DAY_TO_TIMEMILLS;
		if (borrowUpToNow <= 0) {
			return 1;
		} else {
			return (byte) (borrowUpToNow / TimeConstant.PER_PERIOD_TO_TIMEMILLS + 1);
		}
	}

	private BorrowEntity obtainCurrentUserBorrowInfo(final HttpSession session) throws Exception {
		final long userId = userBiz.query((String) session.getAttribute(SystemConstant.SESSION_LOGIN_NAME)).getId();
		final List<BorrowEntity> borrowEntityList = borrowBiz.query(userId);
		for (final BorrowEntity borrowEntity : borrowEntityList) {
			if (StringUtils.equals(borrowEntity.getCompletelyPayOff(), "N")) {
				return borrowEntity;
			}
		}
		throw new Exception("无借贷，不用还款!");
	}

	private void rawRepay(final RepayEntity currentRepayEntity, final BorrowEntity borrowEntity,
			final BigDecimal repayAccount) {
		if (StringUtils.equals(currentRepayEntity.getBalance(), "Y")) {
			currentRepayEntity.setRepay(currentRepayEntity.getRepay().add(repayAccount));
			borrowEntity.setOnAccount(borrowEntity.getOnAccount().add(repayAccount));
			borrowBiz.updateOnAccount(borrowEntity);
			repayBiz.update(currentRepayEntity);
			return;
		}
		borrowEntity.setCompletelyPayOff("N");
		if (repayAccount.compareTo(currentRepayEntity.getActualShouldRepay()) >= 0) {
			borrowEntity.setOnAccount(repayAccount.subtract(
					currentRepayEntity.getActualShouldRepay()));
			currentRepayEntity.setBalance("Y");
			currentRepayEntity.setActualShouldRepay(BigDecimal.ZERO);
			if (currentRepayEntity.getPeriod() >= borrowEntity.getPeriod()) {
				borrowEntity.setCompletelyPayOff("Y");
			}
		} else {
			borrowEntity.setOnAccount(borrowEntity.getOnAccount().add(repayAccount));
			currentRepayEntity.setActualShouldRepay(currentRepayEntity.getShouldRepayNoOnAccount().subtract(
					borrowEntity.getOnAccount()));
		}
		currentRepayEntity.setRepay(currentRepayEntity.getRepay().add(repayAccount));
		borrowBiz.updateOnAccount(borrowEntity);
		repayBiz.update(currentRepayEntity);

	}

}
