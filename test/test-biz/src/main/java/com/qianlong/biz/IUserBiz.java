package com.qianlong.biz;

import com.qianlong.UserEntry;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
public interface IUserBiz {
	
	boolean insert(final UserEntry user);
	
	UserEntry query(final String name);
}
