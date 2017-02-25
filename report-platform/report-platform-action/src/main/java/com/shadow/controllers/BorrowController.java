package com.shadow.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qianlong.BorrowParamBo;
import com.shadow.BorrowEntity;
import com.shadow.biz.IBorrowBiz;
import com.shadow.biz.IRepayBiz;
import com.shadow.biz.IUserBiz;
import com.shadow.biz.constants.SystemConstant;
import com.shadow.biz.util.Amount2String;

/**
 * @author 管黎明
 * 
 *         All rights reserved.
 */
@Controller
public class BorrowController {
	@Autowired
	private IBorrowBiz borrowBiz;
	@Autowired
	private IRepayBiz repayBiz;
	@Autowired
	private IUserBiz userBiz;

	@RequestMapping("borrow")
	public ModelAndView borrow(@ModelAttribute final BorrowParamBo param, final HttpSession session) {
		if (!validateBeforeBorrow(param)) {
			return new ModelAndView("redirect:/borrowPage");
		}
		final long userId = userBiz.query((String) session.getAttribute(SystemConstant.SESSION_LOGIN_NAME)).getId();
		final List<BorrowEntity> borrowEntityList = borrowBiz.query(userId);

		if (!CollectionUtils.isEmpty(borrowEntityList)) {
			for (final BorrowEntity borrowEntity : borrowEntityList) {
//				if (borrowEntity.getShouldRepayAll() == null) {
//					continue;
//				}
				if (StringUtils.equals(borrowEntity.getCompletelyPayOff(), "N")) {
					final ModelAndView mv = new ModelAndView("main");
					mv.addObject("msg", "aaaaaa");
					return mv;
				}
			}
		}
		if (StringUtils.equals(param.getMode(), SystemConstant.BORROW_MODE_ALL)) {
			param.setPeriod((byte) 1);
		}
		final String monthlyRepay = Amount2String.transfer(repayBiz.calculateMonthlyRepay(param.getBorrowAmount(),
				param.getPeriod()));
		borrowBiz.save(param, userId);
		final ModelAndView mv = new ModelAndView("borrowSuccessPage");
		mv.addObject("monthlyRepay", monthlyRepay);
		return mv;

	}

	@RequestMapping("/borrowPage")
	public ModelAndView borrowPage() {
		return new ModelAndView("borrowPage");
	}

	private boolean validateBeforeBorrow(final BorrowParamBo param) {
		if (StringUtils.equals(SystemConstant.BORROW_MODE_INSTALMENT, param.getMode()) && param.getPeriod() > 0
				&& param.getPeriod() <= 36) {
			return true;
		}
		if (StringUtils.equals(SystemConstant.BORROW_MODE_ALL, param.getMode())) {
			return true;
		}
		return false;
	}
}
