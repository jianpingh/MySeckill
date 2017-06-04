package org.myseckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.myseckill.entity.Seckill;

//操作秒杀商品表，主要是增删改查
public interface SeckillDao {
    
	//删：根据秒杀的商品ID减少对应ID的商品数量
	//参数使用@Param为参数值赋予一个名字，对应xml中sql语句的{参数名}
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	//查：根据id查找相应商品记录，返回一个实体类
	Seckill queryById(long seckillId);
	
	//查：分页查询
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
	
	//使用存储过程执行秒杀
	void killByProcedure(Map<String,Object> paramMap);
}
