package com.shadow.biz.util;

import java.util.Map;

import org.dom4j.Element;

/**
 * @author guanliming
 *
 */
public class ReportHeaderUtils {

	public static Map<String, String> getHeader(String headerLine) {
		Map<String, Element> mainElements = XmlUtils.getMainElements();
		Element head = mainElements.get(XmlUtils.HEAD);
		return XmlUtils.getSegmentFieldBlock(head, headerLine);
	}

}
