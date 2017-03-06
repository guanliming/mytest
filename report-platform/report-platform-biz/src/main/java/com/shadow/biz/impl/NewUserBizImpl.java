package com.shadow.biz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.CommonEntity;
import com.shadow.NewUser;
import com.shadow.biz.INewUserBiz;
import com.shadow.biz.util.TransferUtil;
import com.shadow.dao.INewUserDao;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Service
class NewUserBizImpl implements INewUserBiz {
	@Autowired
	private INewUserDao iNewUserDao;

	@Override
	public NewUser queryOne(NewUser params) {
		Map<String, String> params2 = TransferUtil.getObjMap(params);
		CommonEntity param = new CommonEntity();
		param.setPairs(params2);
		param.setTableName("user");
		return new NewUser();
	}


	@Override
	public void save(CommonEntity ce) {
		// TODO Auto-generated method stub
		
	}

}
