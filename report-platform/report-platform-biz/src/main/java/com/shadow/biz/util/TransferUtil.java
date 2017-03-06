package com.shadow.biz.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 管黎明
 *
 *         创建时间:2015年11月9日
 */
public final class TransferUtil {

	/**
	 * 将obj中的所有String域转成map对象
	 *
	 * @param obj
	 * @return DAWN
	 */
	public static Map<String, String> getObjMap(Object obj) {
		if (obj == null) {
			return new HashMap<String, String>();
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		Set<Field> fSet = getAllFieldSet(obj);
		for (Field f : fSet) {
			if (String.class.isAssignableFrom(f.getType())) {
				Object value = getFieldValue(obj, f);
				if (value != null) {
					resultMap.put(f.getName(), (String) value);
				}
			} else if (f.getType().isPrimitive()) {
				continue;
			} else if (TransferUtil.isPackage(f.getType())) {
				continue;
			} else if (Date.class.isAssignableFrom(f.getType())) {
				continue;
			} else {
				resultMap.putAll(TransferUtil.getObjMap(TransferUtil.getFieldValue(obj, f)));
			}
		}
		return resultMap;
	}

	/**
	 * 获取对象所有的域
	 *
	 * @param obj
	 * @return
	 * @author DAWN
	 */
	public static Set<Field> getAllFieldSet(Object obj) {
		Set<Field> fSet = new HashSet<Field>();
		for (Class<?> clazz = obj.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
			fSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		return fSet;
	}

	/**
	 * 拿到obj的指定域值
	 *
	 * @param obj
	 * @param f
	 * @return DAWN
	 */
	public static Object getFieldValue(Object obj, Field f) {
		boolean access = f.isAccessible();
		f.setAccessible(true);
		Object value = null;
		try {
			value = f.get(obj);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} finally {
			f.setAccessible(access);
		}
		return value;
	}

	/**
	 * 设置obj对象的f域的值为value
	 *
	 * @param obj
	 * @param f
	 * @param value
	 *            DAWN
	 */
	public static void setFieldValue(Object obj, Field f, Object value) {
		if (value == null) {
			return;
		}
		boolean access = f.isAccessible();
		f.setAccessible(true);
		try {
			f.set(obj, value);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} finally {
			f.setAccessible(access);
		}
	}

	/**
	 * clazz是否是包装类
	 *
	 * @param clazz
	 * @return DAWN
	 */
	public static boolean isPackage(Class<?> clazz) {
		try {
			Field f = clazz.getDeclaredField("TYPE");
			if (f != null) {
				return true;
			}
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		}
		return false;
	}

	/**
	 * 将源map中的属性复制到target目标中对应名的字符串域中
	 *
	 * @param sourceMap
	 *            源map
	 * @param target
	 *            目标
	 * @throws Exception
	 *             DAWN
	 */
	public static void copyProperties(Map<String, String> sourceMap, Object target) throws Exception {
		Set<Field> fieldSet = getAllFieldSet(target);
		for (Field f : fieldSet) {
			if (String.class.isAssignableFrom(f.getType())) {
				TransferUtil.setFieldValue(target, f, sourceMap.get(f.getName()));
			}
		}

	}

}
