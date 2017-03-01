package com.shadow.biz.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icfcc.batch.ui.PreConditionCheck;
import com.shadow.biz.IReportManage;
import com.shadow.biz.component.SystemVariable;
import com.shadow.biz.excepton.ReportBaseException;
import com.shadow.biz.util.FileUtils;
import com.shadow.biz.util.ReportCheckUtils;

/**
 * @author guanliming
 *
 */
@Service
public class ReportManageImpl implements IReportManage {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static String PROJECTHOME = ReportManageImpl.class.getResource("/").getPath();;
	@Autowired
	private SystemVariable systemVariable;

	@Override
	public String validateAndClassifyFile(String targetDir, String targetFile) {
		if (StringUtils.isBlank(targetDir) && StringUtils.isBlank(targetFile)) {
			throw new ReportBaseException("targetDir或targetFile不能同时为空");
		}
		if (StringUtils.isNotBlank(targetFile)) {
			try {
				if (validateReport(new File(targetFile))) {
					return "file:" + targetFile + " handle success";
				}
				return "file:" + targetFile + " handle fail";
			} catch (Exception e) {
				logger.error("加压失败!文件名:" + targetFile);
				return "file:" + targetFile + " handle fail";
			}
		} else {
			PreConditionCheck preCheck = new PreConditionCheck(PROJECTHOME);
			Integer sumFilesCount = 0;
			Integer successCount = 0;
			Integer failCount = 0;
			validateRequestParams(targetDir);
			for (File file : fetchFiles(targetDir)) {
				sumFilesCount++;
				if (validateReport(file)) {
					successCount++;
				} else {
					failCount++;
				}
			}
			return "succ:" + successCount + ",fail:" + failCount + ",sum:" + sumFilesCount;
		}
	}

	private List<File> fetchFiles(String targetDir) {
		File f = new File(targetDir);
		if (!f.exists() || !f.isDirectory()) {
			throw new ReportBaseException("报文检测目录不存在");
		}
		File[] ff = f.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.isFile()) {
					return true;
				}
				return false;
			}
		});
		return Arrays.asList(ff);
	}

	private void validateRequestParams(String targetDir) {
		if (StringUtils.isBlank(targetDir)) {
			throw new ReportBaseException("报文检测目录为空");
		}
	}

	private boolean validateReport(File file) {
		try {
			PreConditionCheck preCheck = new PreConditionCheck(PROJECTHOME);
			ReportCheckUtils.checkFileName(file);
			ReportCheckUtils.checkHead(file, preCheck);
			ReportCheckUtils.checkBody(file, preCheck);
			List<String> content = FileUtils.readContent(file);
			ReportCheckUtils.checkRecords(content, preCheck);
			ReportCheckUtils.compressFile(file, preCheck, systemVariable.getBaseDir());
			logger.info("ReportManageImpl#validateReport,success:{}", file.getAbsolutePath());
			return true;
		} catch (Exception e) {
			String failDir = FileUtils.mkdirIfNotExist(systemVariable.getBaseDir(), "fail_file");
			String failFile = failDir + File.separator + file.getName();
			try {
				String content = FileUtils.read(file);
				FileUtils.write(content, failFile);
			} catch (Exception e2) {
				logger.error("ReportManageImpl#validateReport,fail file write fail:{},exception:{}", failFile, e);
			}
			logger.error("ReportManageImpl#validateReport,filename:{},exception:{}", file.getAbsolutePath(), e);
			return false;
		}
	}

	

	@Override
	public String decryptAndDecompress(String targetDir, String targetFile) {
		PreConditionCheck preCheck = new PreConditionCheck(PROJECTHOME);
		if (StringUtils.isBlank(targetDir) && StringUtils.isBlank(targetFile)) {
			throw new ReportBaseException("targetDir或targetFile不能同时为空");
		}
		if (StringUtils.isNotBlank(targetFile)) {
			try {
				ReportCheckUtils.decryptCompressFile(new File(targetFile), preCheck, systemVariable.getBaseDir());
				return "file:" + targetFile + " handle success";
			} catch (Exception e) {
				logger.error("解压失败!文件名:" + targetFile);
				return "file:" + targetFile + " handle fail";
			}
		} else {
			Integer sumFilesCount = 0;
			Integer successCount = 0;
			Integer failCount = 0;
			for (File file : fetchFiles(targetDir)) {
				sumFilesCount++;
				try {
					ReportCheckUtils.decryptCompressFile(file, preCheck, systemVariable.getBaseDir());
					successCount++;
				} catch (Exception e) {
					logger.error("解压失败!文件名:" + file.getAbsolutePath());
					failCount++;
				}
			}
			return "succ:" + successCount + ",fail:" + failCount + ",sum:" + sumFilesCount;
		}
	}
}
