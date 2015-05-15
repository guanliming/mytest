package com.qianlong.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianlong.UserEntry;
import com.qianlong.biz.IUserBiz;
import com.qianlong.dao.IUserDao;

@Service
public class UserBizImpl implements IUserBiz {
	@Autowired
	private IUserDao userDao;

	@Override
	public boolean insert(final UserEntry user) {
		return userDao.insert(user);
	}

}
