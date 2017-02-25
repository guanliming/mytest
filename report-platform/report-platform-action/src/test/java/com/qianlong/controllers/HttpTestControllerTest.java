package com.qianlong.controllers;

import java.util.ArrayList;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.shadow.BorrowEntity;
import com.shadow.biz.IBorrowBiz;
import com.shadow.controllers.HttpTestController;

public class HttpTestControllerTest extends BaseTest {
	@Autowired
	private  HttpTestController controller ;
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Override
	public Object getTestTarget() {
		return new HttpTestController();
	}

	public void setup() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testA1() {
		final IBorrowBiz biz = mock(IBorrowBiz.class);

		context.checking(new Expectations() {
			{
				oneOf(biz).query(10);
				will(returnValue(new ArrayList<BorrowEntity>()));
			}
		});

		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/http/a1", 42)).andExpect(
					MockMvcResultMatchers.status().isOk());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
	}

}
