package com.shadow.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mobanker.framework.dao.BaseDao;
import com.shadow.NewUser;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Repository
public interface INewUserDao extends BaseDao<NewUser>{

	
	NewUser queryOne(final NewUser param);
	
	int insert(@Param("pairs")Map<String,String> param);

}
