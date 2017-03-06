package com.shadow.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shadow.CommonEntity;
import com.shadow.biz.IImportManage;
import com.shadow.biz.util.FileUtils;
import com.shadow.biz.util.NameUtils;
import com.shadow.biz.util.ReportHeaderUtils;
import com.shadow.dao.ICommonDao;

/**
 * @author guanliming
 *
 */
@Controller
public class ImportDataController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ICommonDao iCommonDao;
	@Autowired
	private IImportManage iImportManage;

	@ResponseBody
	@RequestMapping(value = "importRawData", method = { RequestMethod.POST, RequestMethod.GET })
	public String importData(String targetDir, String targetFile) {
		logger.info("enter ImportDataController#importData,targetDir:{}", targetDir);
		try {
			iImportManage.importData(targetFile);
		} catch (Exception e) {
			logger.info("exit ImportDataController#importData,exception:{}", e);
		}
		logger.info("exit ImportDataController#importData,targetDir:{}", targetDir);
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "test33")
	public String test(String targetDir, String targetFile) {
		logger.info("enter ReportManageController#validateAndClassifyFile,targetDir:{}", targetDir);
		try {
			Map<String, String> mm = ReportHeaderUtils.getHeader(FileUtils.readContent(new File("F:\\abc\\Z1057923000019201611Y1C1DC2.txt")).get(0));
			Map<String, String> newmm = refreshMapKeys(mm);
			CommonEntity en = new CommonEntity("report_header", newmm);
			iCommonDao.insert(en);
			System.out.println(en.getId());
		} catch (Exception e) {
			logger.info("exit ReportManageController#validateAndClassifyFile,exception:{}", e);
		}
		logger.info("exit ReportManageController#validateAndClassifyFile,targetDir:{}", targetDir);
		return "";
	}

	private Map<String, String> refreshMapKeys(Map<String, String> mm) {
		Map<String, String> newmm = new HashMap<String, String>();
		for (String key : mm.keySet()) {
			newmm.put(NameUtils.getDBSchemaName(key), mm.get(key).trim());
		}
		return newmm;
	}

}
