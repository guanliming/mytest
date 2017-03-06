package com.shadow.biz.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.CommonEntity;
import com.shadow.biz.ICommonBiz;
import com.shadow.biz.util.NameUtils;
import com.shadow.dao.ICommonDao;

/**
 * @author guanliming
 *
 */
@Service
public class CommonBizImpl implements ICommonBiz {

	@Autowired
	private ICommonDao iCommonDao;

	@Override
	public void save(CommonEntity ce) {
		Map<String, String> newmm = refreshMapKeys(ce.getPairs());
		ce.setPairs(newmm);
		iCommonDao.insert(ce);
	}

	
	private Map<String, String> refreshMapKeys(Map<String, String> mm) {
		Map<String, String> newmm = new HashMap<String, String>();
		for (String key : mm.keySet()) {
			newmm.put(NameUtils.getDBSchemaName(key), mm.get(key).trim());
		}
		return newmm;
	}
}
