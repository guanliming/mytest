package com.qianlong.controllers;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qianlong.BorrowParamBo;
import com.qianlong.biz.IBorrowBiz;
import com.qianlong.biz.IUserBiz;
import com.qianlong.constants.SystemConstant;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Controller
public class BorrowController {
	@Autowired
	private IBorrowBiz biz;
	@Autowired
	private IUserBiz userBiz;

	@RequestMapping("/borrow")
	public ModelAndView borrow(@ModelAttribute final BorrowParamBo param, final HttpSession session) {
		if (!validateBeforeBorrow(param)) {
			return new ModelAndView("redirect:/borrowPage");
		}
		biz.save(param, userBiz.query((String) session.getAttribute(SystemConstant.SESSION_LOGIN_NAME)).getId());
		ModelAndView mv = new ModelAndView("borrowSuccessPage");
		DecimalFormat df = new DecimalFormat("#.00");
		mv.addObject("monthlyRepay", df.format(calculateMonthlyRepay(param).doubleValue()));
//		mv.addObject("period", param.getPeriod());
//		mv.addObject("borrowAmount",param.getBorrowAmount());
		return mv;

	}

	@RequestMapping("/borrowPage")
	public ModelAndView borrowPage() {
		return new ModelAndView("borrowPage");
	}

	private BigDecimal calculateMonthlyRepay(final BorrowParamBo param) {
		BigDecimal result =new BigDecimal(new BigDecimal(param.getBorrowAmount().multiply(new BigDecimal(SystemConstant.DAILY_RATE)).doubleValue()
				* param.getPeriod() * 30).add(param.getBorrowAmount()).doubleValue()/param.getPeriod());
		result.setScale(2,BigDecimal.ROUND_DOWN);
		return result;
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
