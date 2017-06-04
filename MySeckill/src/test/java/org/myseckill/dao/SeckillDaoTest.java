package org.myseckill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RunAs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.myseckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {
	//要测试SeckillDao，则定义这样一个对象，并由spring自动注入
	@Autowired
    private SeckillDao seckillDao;

	
	public void testQueryById(){
		long id = 1;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill);
	}
	
	
	public void testQueryAll(){
		List<Seckill> list = seckillDao.queryAll(0,100);
		for (Seckill seckill:list) {
			System.out.println(seckill);
		}
	}
	
	@Test
	public void testReduceNumber(){
		int updateCount = seckillDao.reduceNumber(1, new Date());
		System.out.println(updateCount);
	}	
}
