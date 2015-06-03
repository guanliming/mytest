package com.qianlong.biz.impl;

import java.lang.reflect.Field;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test.xml")
public abstract class BaseTest {

	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	/**
	 * @param currentClass	当前class对象
	 * @param typeToMock	待mock class对象
	 * @param proxy	待mock class对象的代理
	 * @param flag	标记：是否根据field名字取
	 * @param name	field名字
	 * @return
	 */
	private <T> boolean reflectSetter(final Class<?> currentClass,final Class<T> typeToMock, final T proxy, final boolean flag, final String name) {
		for (final Field f : currentClass.getDeclaredFields()) {
			if (f.getType().isAssignableFrom(typeToMock) && (flag ? f.getName().equals(name) : true)) {
				try {
					final boolean access = f.isAccessible();
					f.setAccessible(true);
					f.set(getTestTarget(), proxy);
					f.setAccessible(access);
					return true;
				} catch (final IllegalArgumentException e) {
					e.printStackTrace();
				} catch (final IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * @param typeToMock mock对象
	 * @return	mock对象的代理
	 */
	public final <T> T mock(final Class<T> typeToMock) {
		final T result = context.mock(typeToMock);
		reflectSetter(getTestTarget().getClass(),typeToMock, result, false, null);
		return result;
	}

	/**
	 * @param typeToMock mock对象
	 * @param name mock对象在测试类中的field名
	 * @return	mock对象的代理
	 */
	public final <T> T mock(final Class<T> typeToMock, final String name) {
		final T result = context.mock(typeToMock);
		for(Class<?> current=getTestTarget().getClass();current.getSuperclass()!=null;current=current.getSuperclass()) {
			if(reflectSetter(current,typeToMock,result,true,name)) {
				return result;
			}
		}
		throw new RuntimeException("待mock对象无效:测试目标中不存在名字为["+name+"]的field");
	}

	/**
	 * @return	测试目标
	 */
	public abstract Object getTestTarget();

}
