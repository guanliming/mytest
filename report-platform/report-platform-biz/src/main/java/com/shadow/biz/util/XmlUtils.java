package com.shadow.biz.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shadow.biz.excepton.ReportBaseException;

/**
 * @author guanliming
 *
 */
public class XmlUtils {

	private static final String USE_CONFIG_VERSION = "1.3";

	private static Map<String, Element> mainElements = null;
	private static Logger logger = LoggerFactory.getLogger(XmlUtils.class);

	public static final String HEAD = "HEAD";
	public static final String RECORD_HEADER = "RECORDHEADER";
	public static final String A = "A";
	public static final String B = "B";
	public static final String C = "C";
	public static final String D = "D";
	public static final String E = "E";
	public static final String F = "F";
	public static final String G = "G";

	private XmlUtils() {

	}

	/**
	 * Map中包含HEAD、RECORD_HEADER、A、B、C、D、E、F、G 9个对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Element> getMainElements() {
		if (mainElements == null) {
			init();
		}
		return mainElements;
	}

	static {
		// init();
	}

	private static void init() {
		mainElements = new HashMap<String, Element>();

		SAXReader reader = new SAXReader();
		String path = ReportHeaderUtils.class.getClassLoader().getResource("/conf/message.xml").getPath();
		File file = new File(path);
		try {
			Document document = reader.read(file);
			Element root = document.getRootElement();
			Element messageList = root.element("messageList");
			List<Element> messageElements = messageList.elements("message");
			Element targetElement = null;
			for (Element child : messageElements) {
				if (StringUtils.equals(child.attributeValue("version"), USE_CONFIG_VERSION)) {
					targetElement = child;
					break;
				}
			}
			if (targetElement != null) {

				List<Element> segmentElements = targetElement.elements("segment");
				for (Element segmentElement : segmentElements) {
					if (StringUtils.equals(segmentElement.attributeValue("name"), "Head")) {
						mainElements.put(HEAD, segmentElement);
						continue;
					}
					if (StringUtils.equals(segmentElement.attributeValue("name"), "RecordHeader")) {
						mainElements.put(RECORD_HEADER, segmentElement);
						continue;
					}
					if (StringUtils.equals(segmentElement.attributeValue("name"), "A")) {
						mainElements.put(A, segmentElement);
						continue;
					}
					if (StringUtils.equals(segmentElement.attributeValue("name"), "B")) {
						mainElements.put(B, segmentElement);
						continue;
					}
					if (StringUtils.equals(segmentElement.attributeValue("name"), "C")) {
						mainElements.put(C, segmentElement);
						continue;
					}
					if (StringUtils.equals(segmentElement.attributeValue("name"), "D")) {
						mainElements.put(D, segmentElement);
						continue;
					}
					if (StringUtils.equals(segmentElement.attributeValue("name"), "E")) {
						mainElements.put(E, segmentElement);
						continue;
					}
					if (StringUtils.equals(segmentElement.attributeValue("name"), "F")) {
						mainElements.put(F, segmentElement);
						continue;
					}
					if (StringUtils.equals(segmentElement.attributeValue("name"), "G")) {
						mainElements.put(G, segmentElement);
						continue;
					}
				}
			}
		} catch (DocumentException e) {
			logger.error("读取文件失败,e:{}", e);
		}
	}

	/**
	 * 根据Field定义，把该field从E中取出来
	 * 
	 * @param e
	 * @param fieldLine
	 * @return
	 */
	public static String getString(Element e, String fieldLine) {
		if (e == null || StringUtils.isBlank(fieldLine)) {
			throw new ReportBaseException();
		}
		Integer start = Integer.parseInt(e.attributeValue("startPosition"));
		Integer end = Integer.parseInt(e.attributeValue("endPosition"));
		if (start > end) {
			throw new ReportBaseException();
		}
		Integer length = end - start + 1;

		byte[] bytes = null;
		String result = null;
		try {
			bytes = fieldLine.getBytes("gbk");

			byte[] str = new byte[length];
			int index = 0;
			for (int i = 0; i < bytes.length; i++) {
				if (i >= start && i <= end) {
					str[index++] = bytes[i];
				}
			}
			result = new String(str, "gbk");
			logger.debug("拿到的列值,str:{}", result);
		} catch (UnsupportedEncodingException e1) {
			logger.error("不支持的编码格式");
		}
		return result;
	}

	/**
	 * 截取content 从[start-end)区间的
	 * 
	 * @param content
	 * @param start
	 * @param end
	 * @return
	 */
	public static String subString(String content, Integer start, Integer end) {
		Integer length = end - start + 1;

		byte[] bytes = null;
		String result = null;
		try {
			bytes = content.getBytes("gbk");

			byte[] str = new byte[length - 1];
			int index = 0;
			for (int i = 0; i < bytes.length; i++) {
				if (i >= start && i < end) {
					str[index++] = bytes[i];
				}
			}
			result = new String(str, "gbk");
			logger.debug("拿到的列值,str:{}", result);
		} catch (UnsupportedEncodingException e1) {
			logger.error("不支持的编码格式");
		}
		return result;
	}

	public static Map<String, String> getSegmentFieldBlock(Element segment, String fieldBlock) {
		Map<String, String> headerColumns = new LinkedHashMap<String, String>();
		if (segment == null) {
			throw new ReportBaseException();
		} else {
			List<Element> fieldList = segment.elements("field");
			for (Element field : fieldList) {
				headerColumns.put(field.attributeValue("name"), XmlUtils.getString(field, fieldBlock));
			}
			return headerColumns;
		}
	}

}
