package com.shadow.quartz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
	public static void main(final String[] args) {
		final ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
		
	}
}
