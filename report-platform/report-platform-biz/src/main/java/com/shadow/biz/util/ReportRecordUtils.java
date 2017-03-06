package com.shadow.biz.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import com.shadow.biz.excepton.ReportBaseException;

/**
 * @author guanliming
 *
 */
public class ReportRecordUtils {

	public static final Integer BASE = 345;
	public static final Integer IDENTITY = 371;
	public static final Integer JOB = 199;
	public static final Integer RESIDENCE = 68;
	public static final Integer GURANTEE = 61;
	public static final Integer TRADE = 63;
	public static final Integer SPE_TRADE = 224;

	public static Map<String, String> getRecordHeader(String recordLine) {
		Map<String, Element> mainElements = XmlUtils.getMainElements();
		Element lineHeader = mainElements.get(XmlUtils.RECORD_HEADER);
		return returnMap(recordLine, lineHeader);
	}

	public static Map<String, String> getRecordFieldMap(String recordLine, String key) {
		Map<String, Element> mainElements = XmlUtils.getMainElements();
		Element lineHeader = mainElements.get(key);

		return returnMap(recordLine, lineHeader);
	}

	public static Integer recordLength(String recordLine) {
		if (StringUtils.isBlank(recordLine)) {
			throw new ReportBaseException();
		}
		String lengthStr = ReportRecordUtils.getRecordHeader(recordLine).get("recordLength");
		Integer length = Integer.parseInt(lengthStr);
		if (length < BASE) {
			throw new ReportBaseException();
		}
		return length;
	}

	private static Map<String, String> returnMap(String recordLine, Element lineHeader) {
		Map<String, String> columns = new LinkedHashMap<String, String>();
		if (lineHeader == null) {
			throw new ReportBaseException();
		} else {
			List<Element> fields = lineHeader.elements("field");
			for (Element field : fields) {
				columns.put(field.attributeValue("name"), XmlUtils.getString(field, recordLine));
			}
			return columns;
		}
	}

	public static void main(String[] args) {
		System.out.println(Integer.parseInt("0078"));
	}

}
