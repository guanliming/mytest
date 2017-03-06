package com.shadow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shadow.biz.INewUserBiz;
import com.shadow.dao.INewUserDao;

/**
 * @author guanliming
 *
 */
@Controller
@RequestMapping("borrowManage")
public class NewBorrowController {
	@Autowired
	private INewUserBiz iNewUserBiz;
	@Autowired
	private INewUserDao iNewUserDao;
	
//	@RequestMapping(value = "borrow")
//	@ResponseBody
//	public String borrow(final NewUser params) throws Exception {
//		NewUser params2 = new NewUser();
//		NewUser params3 = new NewUser();
//		params3.setUserName("就当减肥拉尔");
//		params2.setId("123");
//		iNewUserDao.insert(params3);
//		NewUser nue = iNewUserDao.getOne(params2);
//		return "";
//	}
//	
//	@RequestMapping(value = "borrow")
//	@ResponseBody
//	public String borrow(final NewUser params) throws Exception {
//		NewUser params2 = new NewUser();
//		NewUser params3 = new NewUser();
//		params3.setUserName("就当减肥拉尔");
//		params2.setId("123");
//		iNewUserDao.insert(params3);
//		NewUser nue = iNewUserDao.getOne(params2);
//		return "";
//	}
	
	

}
