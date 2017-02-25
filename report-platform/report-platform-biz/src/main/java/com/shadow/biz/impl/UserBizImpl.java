package com.shadow.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.UserEntity;
import com.shadow.biz.IUserBiz;
import com.shadow.dao.IUserDao;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Service
 class UserBizImpl implements IUserBiz {
	@Autowired
	private IUserDao userDao;

	@Override
	public boolean insert(final UserEntity user) {
		return userDao.insert(user);
	}

	@Override
	public UserEntity query(final String name) {
		return userDao.query(name);
	}

}
