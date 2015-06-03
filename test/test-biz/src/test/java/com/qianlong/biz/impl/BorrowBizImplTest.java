package com.qianlong.biz.impl;

import org.jmock.Expectations;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qianlong.BorrowEntity;
import com.qianlong.dao.IBorrowDao;

public class BorrowBizImplTest extends BaseTest {

	// Mockery context = new JUnit4Mockery();
	@Autowired
	private final  BorrowBizImpl bizImpl = new BorrowBizImpl();
//	private final BorrowBizImpl bizImpl = new BorrowBizImpl();

	@Override
	public Object getTestTarget() {
		return bizImpl;
	}

	@Test
	public void testQuery() {
	}

	@Test
	public void testSave() {
	}

	@Test
	public void testUpdateOnAccount() {
		final IBorrowDao dao = mock(IBorrowDao.class,"dao");
		context.checking(new Expectations() {
			{
				oneOf(dao).updateOnAccount(new BorrowEntity());
			}
		});
		bizImpl.updateOnAccount(new BorrowEntity());
	}

}
