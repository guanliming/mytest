package com.shadow.biz.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.shadow.BaseDto;
import com.shadow.annos.Environment;
import com.shadow.annos.Required;

/**
 * @author 管黎明
 *
 *         创建时间:2016年2月25日
 */
public class RequiredUtil {

	/**
	 * 验证obj对象中所有用AuthRequred注解的域是否有值
	 *
	 * @throws IllegalArgumentException
	 * @param obj
	 *            对象 DAWN
	 */
	public static void validate(Object obj, Environment env) {
		Set<Field> fSet = new HashSet<Field>();
		for (Class<?> clazz = obj.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
			fSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		for (Field f : fSet) {
			valiField(obj, f, env);
		}
	}

	@SuppressWarnings("all")
	private static void valiField(Object obj, Field f, Environment env) {
		if (f.isAnnotationPresent(Required.class) && Arrays.asList(f.getAnnotation(Required.class).value()).contains(env)) {
			boolean access = f.isAccessible();
			f.setAccessible(true);
			Object value = null;
			try {
				value = f.get(obj);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			f.setAccessible(access);
			if (String.class.isAssignableFrom(f.getType()) && StringUtils.isBlank((String) value)) {
				throw new RuntimeException(f.getName() + "不能为空");
			} else if (BaseDto.class.isAssignableFrom(f.getType())) {
				RequiredUtil.validate(value, env);
			} else if (List.class.isAssignableFrom(f.getType())) {
				List arr = (List) value;
				if (CollectionUtils.isEmpty(arr)) {
					throw new RuntimeException( f.getName() + "不能为空");
				}
				Iterator it = arr.iterator();
				while (it.hasNext()) {
					RequiredUtil.validate(it.next(), env);
				}
			} else if (f.getType().isArray() && BaseDto.class.isAssignableFrom(f.getType().getComponentType())) {
				BaseDto[] arr = (BaseDto[]) value;
				for (int i = 0; i < arr.length; i++) {
					RequiredUtil.validate(arr[i], env);
				}
			} else if (value == null) {
				throw new RuntimeException(f.getName() + "不能为空");
			}
		}

	}

}
