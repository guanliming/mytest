package com.shadow.biz.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.CommonEntity;
import com.shadow.biz.ICommonBiz;
import com.shadow.biz.IImportManage;
import com.shadow.biz.util.FileUtils;
import com.shadow.biz.util.ReportHeaderUtils;
import com.shadow.biz.util.ReportRecordUtils;
import com.shadow.biz.util.XmlUtils;

/**
 * @author guanliming
 *
 */
@Service
public class IImportManageImpl implements IImportManage {

	@Autowired
	private ICommonBiz iCommonBiz;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean importData(String targetFile) {
		List<String> content = FileUtils.readContent(new File(targetFile));
		try {
			String fileName = targetFile.substring(targetFile.lastIndexOf("\\"));
			Map<String, String> mm = ReportHeaderUtils.getHeader(content.get(0));
			mm.put("file_name", fileName);
			CommonEntity en = new CommonEntity("report_header", mm);
			iCommonBiz.save(en);
			for (int i = 0; i < content.size(); i++) {
				if (i > 1) {
					handleRowData(content.get(i),fileName);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("IImportManageImpl#importData,exception:{}", e);
			return false;
		}

	}

	@Override
	public boolean handleRowData(String lineData, String fileName) {
		Integer length = ReportRecordUtils.recordLength(lineData);
		Integer baseCount = 1;
		Integer identityCount = 0;
		Integer jobCount = 0;
		Integer residenceCount = 0;
		Integer guranteeCount = 0;
		Integer tradeCount = 0;
		Integer speTradeCount = 0;
		Integer maxIdentityCount = 1;
		Integer maxJobCount = 1;
		Integer maxResidenceCount = 1;
		Integer maxGuranteeCount = length / ReportRecordUtils.GURANTEE;
		Integer maxTradeCount = 1;
		Integer maxSpeTradeCount = 1;
		for (int a1 = 0; a1 <= maxIdentityCount; a1++) {
			for (int a2 = 0; a2 <= maxJobCount; a2++) {
				for (int a3 = 0; a3 <= maxResidenceCount; a3++) {
					for (int a4 = 0; a4 <= maxGuranteeCount; a4++) {
						for (int a5 = 0; a5 <= maxTradeCount; a5++) {
							for (int a6 = 0; a6 <= maxSpeTradeCount; a6++) {
								if (ReportRecordUtils.BASE + a1 * ReportRecordUtils.IDENTITY + a2 * ReportRecordUtils.JOB + a3 * ReportRecordUtils.RESIDENCE
										+ a4 * ReportRecordUtils.GURANTEE + a5 * ReportRecordUtils.TRADE + a6 * ReportRecordUtils.SPE_TRADE == length) {
									identityCount = a1;
									jobCount = a2;
									residenceCount = a3;
									guranteeCount = a4;
									tradeCount = a5;
									speTradeCount = a6;
									break;
								}
							}
						}
					}
				}
			}
		}
		String serialId = generateBillNo();

		Map<String, String> baseInfo = ReportRecordUtils.getRecordFieldMap(XmlUtils.subString(lineData, 4, ReportRecordUtils.BASE), XmlUtils.A);
		baseInfo.put("serial_id", serialId);
		baseInfo.put("file_name", fileName);
		baseInfo.put("record_length", String.valueOf(length));
		CommonEntity en = new CommonEntity("report_basis", baseInfo);
		iCommonBiz.save(en);

		if (identityCount == 1) {
			Map<String, String> identityInfo = ReportRecordUtils.getRecordFieldMap(
					XmlUtils.subString(lineData, ReportRecordUtils.BASE, ReportRecordUtils.BASE + ReportRecordUtils.IDENTITY), XmlUtils.B);
			identityInfo.put("serial_id", serialId);
			CommonEntity en2 = new CommonEntity("report_identity", identityInfo);
			iCommonBiz.save(en2);
		}
		if (jobCount == 1) {
			Map<String, String> jobInfo = ReportRecordUtils.getRecordFieldMap(
					XmlUtils.subString(lineData, ReportRecordUtils.BASE + identityCount * ReportRecordUtils.IDENTITY, ReportRecordUtils.BASE + identityCount
							* ReportRecordUtils.IDENTITY + ReportRecordUtils.JOB), XmlUtils.C);
			jobInfo.put("serial_id", serialId);
			CommonEntity en3 = new CommonEntity("report_job", jobInfo);
			iCommonBiz.save(en3);
		}
		if (residenceCount == 1) {
			Map<String, String> residenceInfo = ReportRecordUtils.getRecordFieldMap(XmlUtils.subString(lineData, ReportRecordUtils.BASE + identityCount
					* ReportRecordUtils.IDENTITY + jobCount * ReportRecordUtils.JOB, ReportRecordUtils.BASE + identityCount * ReportRecordUtils.IDENTITY
					+ jobCount * ReportRecordUtils.JOB + ReportRecordUtils.RESIDENCE), XmlUtils.D);
			residenceInfo.put("serial_id", serialId);
			CommonEntity en4 = new CommonEntity("report_residence", residenceInfo);
			iCommonBiz.save(en4);
		}
		for (int i = 0; i < guranteeCount; i++) {
			Map<String, String> guranteeInfo = ReportRecordUtils.getRecordFieldMap(
					XmlUtils.subString(lineData, ReportRecordUtils.BASE + identityCount * ReportRecordUtils.IDENTITY + jobCount * ReportRecordUtils.JOB
							+ maxResidenceCount * ReportRecordUtils.RESIDENCE + i * ReportRecordUtils.GURANTEE, ReportRecordUtils.BASE + identityCount
							* ReportRecordUtils.IDENTITY + jobCount * ReportRecordUtils.JOB + maxResidenceCount * ReportRecordUtils.RESIDENCE + (i + 1)
							* ReportRecordUtils.GURANTEE), XmlUtils.E);
			guranteeInfo.put("serial_id", serialId);
			CommonEntity en5 = new CommonEntity("report_guarantee", guranteeInfo);
			iCommonBiz.save(en5);
		}

		if (tradeCount == 1) {
			Map<String, String> tradeInfo = ReportRecordUtils.getRecordFieldMap(
					XmlUtils.subString(lineData, ReportRecordUtils.BASE + identityCount * ReportRecordUtils.IDENTITY + jobCount * ReportRecordUtils.JOB
							+ maxResidenceCount * ReportRecordUtils.RESIDENCE + guranteeCount * ReportRecordUtils.GURANTEE, ReportRecordUtils.BASE
							+ identityCount * ReportRecordUtils.IDENTITY + jobCount * ReportRecordUtils.JOB + maxResidenceCount * ReportRecordUtils.RESIDENCE
							+ guranteeCount * ReportRecordUtils.GURANTEE + ReportRecordUtils.TRADE), XmlUtils.F);
			tradeInfo.put("serial_id", serialId);
			CommonEntity en6 = new CommonEntity("report_trade", tradeInfo);
			iCommonBiz.save(en6);
		}
		if (speTradeCount == 1) {
			Map<String, String> speTradeInfo = ReportRecordUtils.getRecordFieldMap(
					XmlUtils.subString(lineData, ReportRecordUtils.BASE + identityCount * ReportRecordUtils.IDENTITY + jobCount * ReportRecordUtils.JOB
							+ maxResidenceCount * ReportRecordUtils.RESIDENCE + guranteeCount * ReportRecordUtils.GURANTEE + tradeCount
							* ReportRecordUtils.TRADE, ReportRecordUtils.BASE + identityCount * ReportRecordUtils.IDENTITY + jobCount * ReportRecordUtils.JOB
							+ maxResidenceCount * ReportRecordUtils.RESIDENCE + guranteeCount * ReportRecordUtils.GURANTEE + tradeCount
							* ReportRecordUtils.TRADE + ReportRecordUtils.SPE_TRADE), XmlUtils.G);
			speTradeInfo.put("serial_id", serialId);
			CommonEntity en7 = new CommonEntity("report_spe_trade", speTradeInfo);
			iCommonBiz.save(en7);
		}
		return true;
	}

	public static String generateBillNo() {
		Date date = Calendar.getInstance().getTime();
		String str = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
		return str + rannum;
	}

}
