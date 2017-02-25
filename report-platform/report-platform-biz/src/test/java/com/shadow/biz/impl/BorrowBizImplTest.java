package com.shadow.biz.impl;

import org.jmock.Expectations;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.shadow.BorrowEntity;
import com.shadow.biz.IBorrowBiz;
import com.shadow.dao.IBorrowDao;

public class BorrowBizImplTest extends BaseTest {

	@Autowired
	private final  IBorrowBiz bizImpl = new BorrowBizImpl();

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
