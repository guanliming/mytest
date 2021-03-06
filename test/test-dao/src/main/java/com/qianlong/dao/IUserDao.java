package com.qianlong.dao;

import org.springframework.stereotype.Repository;

import com.qianlong.UserEntity;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Repository
public interface IUserDao {

	UserEntity query(final String name);
	
	boolean insert(final UserEntity user);

}
