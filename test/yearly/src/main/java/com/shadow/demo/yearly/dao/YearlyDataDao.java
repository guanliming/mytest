package com.shadow.demo.yearly.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.shadow.demo.yearly.entity.YearlyData;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Repository
public interface YearlyDataDao {
	Integer updateFileName(String fileName);

	String getUrl();
	
	YearlyData query(@Param("type")String type, @Param("order")String order);
	
	
	int update(YearlyData toUpdate);

}
