package com.shadow.annos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 管黎明
 *
 *         创建时间:2015年11月4日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Required {
	/**
	 * @author dawn
	 * @return
	 */
	Environment[] value() default { Environment.QUERY };
}
