package com.qianlong.biz;

import java.util.List;

import com.qianlong.BorrowEntity;
import com.qianlong.BorrowParamBo;

/**
 * @author 管黎明
 * 
 *         All rights reserved.
 */
public interface IBorrowBiz {
	
	long save(final BorrowParamBo param, final long userId);
	
	List<BorrowEntity> query(final long userId); 
	
	void updateOnAccount(final BorrowEntity borrow);
}
