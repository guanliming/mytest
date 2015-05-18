package com.qianlong.biz;

import com.qianlong.UserEntity;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
public interface IUserBiz {
	
	boolean insert(final UserEntity user);
	
	UserEntity query(final String name);
}
