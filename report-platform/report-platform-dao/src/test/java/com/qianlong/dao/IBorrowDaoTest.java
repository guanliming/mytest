package com.qianlong.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.shadow.BorrowEntity;
import com.shadow.dao.IBorrowDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test.xml")
@TransactionConfiguration(transactionManager="transactionManager")
@Transactional
public class IBorrowDaoTest {
	@Autowired
	private IBorrowDao borrowDao;

	@Test
	public void testQuery() {
		Assert.assertTrue(borrowDao.query(10)!=null);
	}

	@Test
	public void testSave() {
		final BorrowEntity be = new BorrowEntity();
		be.setBorrowAmount(new BigDecimal(3232));
		be.setBorrowType("a");
		be.setBorrowTime(new Date());
		final long id = borrowDao.save(be);
		final List<BorrowEntity> list =borrowDao.queryById(be.getId());
		Assert.assertTrue(list.size()>0);
	}

	@Test
	public void testUpdateOnAccount() {
//		fail("Not yet implemented");
	}

}
