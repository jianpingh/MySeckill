package org.myseckill.dao;

import org.apache.ibatis.annotations.Param;
import org.myseckill.entity.Seckill;
import org.myseckill.entity.SuccessKilled;

public interface SuccessKilledDao {
	
	//增：增加一条秒杀成功的记录，传入秒杀的商品ID以及进行秒杀操作的手机号
	int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	//查：查找某用户对某商品的秒杀记录
	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);

}
