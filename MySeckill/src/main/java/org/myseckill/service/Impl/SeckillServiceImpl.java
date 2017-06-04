package org.myseckill.service.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.myseckill.dao.SeckillDao;
import org.myseckill.dao.SuccessKilledDao;
import org.myseckill.dao.cache.RedisDao;
import org.myseckill.dto.Exposer;
import org.myseckill.dto.SeckillExecution;
import org.myseckill.entity.Seckill;
import org.myseckill.entity.SuccessKilled;
import org.myseckill.exception.RepeatKillException;
import org.myseckill.exception.SeckillClosedException;
import org.myseckill.exception.SeckillException;
import org.myseckill.service.SeckillService;
import org.myseckill.enums.SeckillStateEnum;
import org.myseckill.exception.RepeatKillException;
import org.myseckill.exception.SeckillClosedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
@Transactional
public class SeckillServiceImpl implements SeckillService {
	
	//使用slf4j日志
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	//用于加密的混淆字符串，随机串
   private final String slat="asfdfadsf45qa@$E#iudkgj15=sdf=daf5";
	

	//本例只用了4跳记录，实际项目中可以再定义一个selectall的SQL语句查询所有
	public List<Seckill> getSeckillList() {		
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		
		return seckillDao.queryById(seckillId);
	}

	@Autowired
	private RedisDao redisDao;
	public Exposer exportSeckillUrl(long seckillId) {
		//优化
		//1:先访问redis
		Seckill seckill=redisDao.getSeckill(seckillId);
		if(seckill==null){//缓存中没有
			//2:访问数据库
			seckill=seckillDao.queryById(seckillId);
			if(seckill==null){//没有这个产品的秒杀记录，不进行暴露
				return new Exposer(false, seckillId);
			}
		}else{
			//3:数据库中有，则查出来后放入redis
			redisDao.putSeckill(seckill);
		}
		
		
		
		Date now=new Date();
		Date start=seckill.getStartTime();
		Date end=seckill.getEndTime();
		//若时间非法，不秒杀
		if(now.getTime()<start.getTime() || now.getTime()>end.getTime()){
			return new Exposer(false, seckillId, now.getTime(), start.getTime(), end.getTime());
		}
		//否则，进行秒杀网址暴露		  
		String md5=getMD5(seckillId);      				
		return new Exposer(true, md5, seckillId);
	}

	//用md5加密
	private String getMD5(long seckillId){
		String base=seckillId+"/"+slat;
		String md5=DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	@Override
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillException {
		if(md5==null||!md5.equals(getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");
		}
		//执行秒杀逻辑：减库存+记录购买行为
		try {
			//记录购买行为
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if(insertCount <= 0 ){
				//重复秒杀
				throw new RepeatKillException("seckill repeated");
			}else{
				//减库存,热点商品竞争（高并发点）
				int updateCount = seckillDao.reduceNumber(seckillId, new Date());
				if(updateCount<=0){
					//没有更新到记录,秒杀结束，rollback
					throw new SeckillClosedException("seckill is closed");
				}else{
					//秒杀成功,commit
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
				}
			}
			
		} catch(SeckillClosedException e1){
			throw e1;
		} catch(RepeatKillException e2){
			throw e2;
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			//所有异常转化为运行期异常
			throw new SeckillException("seckill inner error:"+e.getMessage());
		}
	}

	@Override
	public SeckillExecution executeSeckillByProcedure(long seckillId,
			long userPhone, String md5) {
		if(md5==null||!md5.equals(getMD5(seckillId))){
			return new SeckillExecution(seckillId,SeckillStateEnum.DATA_REWRITE);
		}
		
		Date killTime = new Date();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("seckillId", seckillId);
		map.put("phone",userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		
		try {
			seckillDao.killByProcedure(map);
			int result = MapUtils.getInteger(map, "result", -2);//result默认为-2
			if(result==1){
				//秒杀成功
				SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,sk);
			}else{
				return new SeckillExecution(seckillId,SeckillStateEnum.stateOf(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new SeckillExecution(seckillId,SeckillStateEnum.INNER_ERROR);
		}
	}

}
