package com.shadow.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shadow.biz.IReportManage;

/**
 * @author guanliming
 *
 */
@Controller
public class ReportManageController {
	@Autowired
	private IReportManage iReportManage;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 对输入目录下的所有文件进行加密
	 * 
	 * @param targetDir
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validateAndClassifyFile", method = { RequestMethod.POST, RequestMethod.GET })
	public String validateAndClassifyFile(String targetDir, String targetFile) {
		logger.info("enter ReportManageController#validateAndClassifyFile,targetDir:{}", targetDir);
		String str = null;
		try {
			str = iReportManage.validateAndClassifyFile(targetDir, targetFile);
		} catch (Exception e) {
			logger.error("ReportManageController#decryptAndDecompress,unexpected error:{}", e);
			str = e.getMessage();
		}
		logger.info("exit ReportManageController#validateAndClassifyFile,targetDir:{}", targetDir);
		return str;
	}

	/**
	 * 对输入目录下的所有文件进行加密
	 * 
	 * @param targetDir
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "decryptAndDecompress", method = { RequestMethod.POST, RequestMethod.GET })
	public String decryptAndDecompress(String targetDir, String targetFile) {
		logger.info("enter ReportManageController#validateAndClassifyFile,targetDir:{}", targetDir);
		String str = null;
		try {
			str = iReportManage.decryptAndDecompress(targetDir, targetFile);
		} catch (Exception e) {
			logger.error("ReportManageController#decryptAndDecompress,unexpected error:{}", e);
			str = e.getMessage();
		}
		logger.info("exit ReportManageController#validateAndClassifyFile,targetDir:{}", targetDir);
		return str;
	}

}
