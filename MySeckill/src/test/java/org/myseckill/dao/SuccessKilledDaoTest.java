package org.myseckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.myseckill.dao.SuccessKilledDao;
import org.myseckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {
	@Autowired
	SuccessKilledDao successKilledDao;
	
	
	public void testInsertSuccessKilled(){
		int insertCount = successKilledDao.insertSuccessKilled(1L, 15764210366L);
		System.out.println(insertCount);
	}
	
	@Test
	public void testQueryByIdWithSeckill(){
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1, 15764210366L);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}

}
