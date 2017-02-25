package com.shadow.quartz;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyJob {
	
	public void executeJob(){
		log.info("定时工作!");
	}
}
