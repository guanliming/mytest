package com.qianlong.biz;

import com.qianlong.BorrowParamBo;

/**
 * @author 管黎明
 * 
 *         All rights reserved.
 */
public interface IBorrowBiz {
	void save(final BorrowParamBo param, long userId);
}
