package com.shadow.biz.util;

import org.apache.commons.lang3.StringUtils;

import com.shadow.biz.excepton.ReportBaseException;

/**
 * @author guanliming
 *
 */
public class NameUtils {

	/**
	 * 获取db列名的格式
	 * 
	 * @param key
	 */
	public static String getDBSchemaName(String key) {
		if (StringUtils.isBlank(key)) {
			throw new ReportBaseException("名字为空");
		}
		char[] charArray = key.toCharArray();
		char sep = '_';
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charArray.length; i++) {
			if (i == 0 && charArray[i] >= 65 && charArray[i] <= 90) {
				sb.append((char) (charArray[i] + 32));
				continue;
			}
			if (charArray[i] >= 65 && charArray[i] <= 90) {
				sb.append(sep);
				sb.append((char) (charArray[i] + 32));
				continue;
			}
			sb.append(charArray[i]);
		}
		return sb.toString();
	}

	/**
	 * 将目标字符串转成驼峰标示:a_b_cd->aBCd
	 *
	 * @param str
	 *            目标字符串
	 * @return
	 */
	public static String transferToCamel(String str) {
		if (StringUtils.isBlank(str)) {
			throw new ReportBaseException("名字为空");
		}
		char[] charArray = str.toCharArray();
		char sep = '_';
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		for (char aChar : charArray) {
			if (sep == aChar) {
				flag = true;
				continue;
			}
			if (flag && aChar >= 97 && aChar <= 122) {
				sb.append((char) (aChar - 32));
				flag = false;
				continue;
			}
			sb.append(aChar);
			flag = false;
		}
		return sb.toString();
	}

}
