package com.shadow.biz.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.BorrowEntity;
import com.shadow.BorrowParamBo;
import com.shadow.biz.IBorrowBiz;
import com.shadow.dao.IBorrowDao;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Service
 class BorrowBizImpl implements IBorrowBiz {
	@Autowired
	private IBorrowDao dao;
	
	@Override
	public List<BorrowEntity> query(final long userId) {
		return dao.query(userId);
	}

	@Override
	public long save(final BorrowParamBo param, final long userId) {
		final BorrowEntity borrow = new BorrowEntity();
		borrow.setBorrowAmount(param.getBorrowAmount());
		borrow.setBorrowTime(new Date());
		borrow.setBorrowType(param.getMode());
		borrow.setBorrowUserId(userId);
		borrow.setPeriod(param.getPeriod());
		borrow.setOnAccount(BigDecimal.ZERO);
		return dao.save(borrow);
	}

	@Override
	public void updateOnAccount(final BorrowEntity borrow) {
		dao.updateOnAccount(borrow);
	}

}
