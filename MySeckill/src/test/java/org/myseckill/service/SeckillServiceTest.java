package org.myseckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.myseckill.dto.Exposer;
import org.myseckill.dto.SeckillExecution;
import org.myseckill.entity.Seckill;
import org.myseckill.exception.RepeatKillException;
import org.myseckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService;
	
	
	public void testGetById(){
		Seckill seckill = seckillService.getById(1);
		logger.info("seckill={}", seckill);
	}
	
	public void testGetSeckillList(){
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}", list);
	}
	
	//集成测试代码完整逻辑，注意代码的可重复性
	
	public void testSeckillLogic(){
		long id = 1;
		long userPhone = 15764210010l;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		if(exposer.isExposed()){
			logger.info("exposer={}", exposer);
			String md5 = exposer.getMd5();
			try {
				SeckillExecution execution = seckillService.executeSeckill(id, userPhone, md5);
				logger.info("execution={}", execution);
			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			} catch (SeckillException e) {
				logger.error(e.getMessage());
			}
		}else{
			logger.warn("exposer={}",exposer);
		}
		
		
	}
	@Test
	public void testExecuteSeckill(){
		long id = 1;
		long userPhone = 15764210010L;
		String md5 = "83d3477e08a3a3affcd7e07e78a1d832";
		try {
			SeckillExecution execution = seckillService.executeSeckill(id, userPhone, md5);
			logger.info("execution={}", execution);
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillException e) {
			logger.error(e.getMessage());
		}
	}
	
		
}


