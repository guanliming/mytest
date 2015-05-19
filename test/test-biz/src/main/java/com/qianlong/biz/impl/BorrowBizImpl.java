package com.qianlong.biz.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianlong.BorrowEntity;
import com.qianlong.BorrowParamBo;
import com.qianlong.biz.IBorrowBiz;
import com.qianlong.dao.IBorrowDao;

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
		return dao.save(borrow);
	}

}
