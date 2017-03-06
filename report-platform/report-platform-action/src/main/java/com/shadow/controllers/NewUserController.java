package com.shadow.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobanker.framework.dto.ResponseEntity;
import com.shadow.NewUser;
import com.shadow.annos.Environment;
import com.shadow.biz.INewUserBiz;
import com.shadow.biz.util.RequiredUtil;
import com.shadow.biz.util.ResponseEntityUtil;
import com.shadow.dao.INewUserDao;

/**
 * @author guanliming
 *
 */
@Controller
@RequestMapping("userManage")
public class NewUserController {
	@Autowired
	private INewUserBiz iNewUserBiz;
	@Autowired
	private INewUserDao iNewUserDao;

	private Logger logger = LoggerFactory.getLogger(NewUserController.class);

	@RequestMapping(value = "query")
	@ResponseBody
	public ResponseEntity query(final NewUser params) throws Exception {
		ResponseEntity re = new ResponseEntity();
		try {
			RequiredUtil.validate(params,Environment.QUERY);
			List<NewUser> users = iNewUserDao.get(params);
			ResponseEntityUtil.setSuccess(re, users);
		} catch (Exception e) {
			logger.error("NewUserController#query,exception e:{}", e);
			ResponseEntityUtil.setFail(re,e.getMessage());
		}
		return re;
	}

	@RequestMapping(value = "modify")
	@ResponseBody
	public ResponseEntity update(final NewUser params) throws Exception {
		ResponseEntity re = new ResponseEntity();
		try {
			iNewUserDao.update(params);
			ResponseEntityUtil.setSuccess(re, null);
		} catch (Exception e) {
			logger.error("NewUserController#query,exception e:{}", e);
			ResponseEntityUtil.setFail(re,e.getMessage());
		}
		return re;
	}

	@RequestMapping(value = "addUser")
	@ResponseBody
	public ResponseEntity save(final NewUser params) throws Exception {
		ResponseEntity re = new ResponseEntity();
		try {
			iNewUserDao.insert(params);
			ResponseEntityUtil.setSuccess(re, null);
		} catch (Exception e) {
			logger.error("NewUserController#query,exception e:{}", e);
			ResponseEntityUtil.setFail(re,e.getMessage());
		}
		return re;
	}

}
