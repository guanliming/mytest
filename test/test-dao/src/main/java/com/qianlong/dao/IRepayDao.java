package com.qianlong.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qianlong.RepayEntity;

@Repository
public interface IRepayDao {
	
	
	List<RepayEntity> queryByBorrowId(@Param("borrowId")final long borrowId);
	
	RepayEntity queryByBorrowIdAndPeriod(@Param("borrowId")final long borrowId,@Param("period")final byte period );

	long save(final RepayEntity repay);
	
}
