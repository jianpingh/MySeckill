package org.myseckill.service;

import java.util.List;

import org.myseckill.dto.Exposer;
import org.myseckill.dto.SeckillExecution;
import org.myseckill.entity.Seckill;
import org.myseckill.exception.RepeatKillException;
import org.myseckill.exception.SeckillClosedException;
import org.myseckill.exception.SeckillException;

public interface SeckillService {

	//查询所有
	List<Seckill> getSeckillList();
	//根据ID查询
	Seckill getById(long seckillId);	
	//暴露秒杀网页地址
	Exposer exportSeckillUrl(long seckillId);
	
	//md5用于与内部md5做比较，防止用于篡改url进行秒杀
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillClosedException;
	
	// 执行秒杀操作by存储过程
	SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5);
}
