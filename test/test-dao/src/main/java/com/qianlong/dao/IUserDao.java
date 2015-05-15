package com.qianlong.dao;

import org.springframework.stereotype.Repository;

import com.qianlong.UserEntry;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Repository
public interface IUserDao {

	UserEntry query(final String name);
	
	boolean insert(final UserEntry user);

}
