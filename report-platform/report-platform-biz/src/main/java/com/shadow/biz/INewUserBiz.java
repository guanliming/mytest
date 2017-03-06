package com.shadow.biz;

import com.shadow.CommonEntity;
import com.shadow.NewUser;

/**
 * @author guanliming
 *
 */
public interface INewUserBiz {

	void save(CommonEntity ce);
	
	NewUser queryOne(NewUser params);

}
