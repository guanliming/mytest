package com.shadow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.shadow.RepayEntity;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Repository
public interface IRepayDao {
	
	
	List<RepayEntity> queryByBorrowId(@Param("borrowId")final long borrowId);
	
	RepayEntity queryByBorrowIdAndPeriod(final long borrowId,final byte period );

	long save(final RepayEntity repay);
	
	
	/**
	 * @param borrowId	借款记录号
	 * @return	最近一次销账的信息
	 */
	RepayEntity queryLatestBalance(@Param("borrowId")final long borrowId);
	
	void update(final RepayEntity repay);
	
}
