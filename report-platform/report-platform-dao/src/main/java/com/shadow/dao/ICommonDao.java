package com.shadow.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.shadow.CommonEntity;

/**
 * @author 管黎明
 *
 *         All rights reserved.
 */
@Repository
public interface ICommonDao {

	int insert(CommonEntity toInsert);

	int update(CommonEntity toInsert);

	List<Map<String, Object>> queryList(CommonEntity toInsert);

	Map<String, Object> queryOne(CommonEntity toInsert);
}
