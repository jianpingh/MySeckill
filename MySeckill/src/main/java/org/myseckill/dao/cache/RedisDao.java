package org.myseckill.dao.cache;

import org.myseckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
   private Logger logger = LoggerFactory.getLogger(this.getClass());
   private JedisPool jedisPool;
   
   //构造函数，创建一个连接到ip的port端口的redis池
   public RedisDao(String ip,int port){
	   jedisPool=new JedisPool(ip, port);
   }
   
  //protostuff通过类的schema来进行序列化，所以传入要序列化的类创建一个自定义的schema。
  private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class); 
   //get from cache
   public Seckill getSeckill(long seckillId){
	   try {
		Jedis jedis=jedisPool.getResource();
		try {
			String key = "seckill:"+seckillId;
			//get byte[]——>反序列化得到 object（seckill）。因此，我们需要把Seckill对象序列化
			//实现serializable接口是Java自带的序列化，效果不是很好
			//这里采用第三方的自定义序列化工具类protostuff
			//通过key来查询对象，对象在cache中以序列化形式存在，所以返回的是字节数组
			byte[] bytes=jedis.get(key.getBytes());
			if(bytes!=null){//字节数组非空，则查到了对象
				//创建一个空对象，用于接收转换后的结果
			    Seckill seckill=schema.newMessage();
			    //把字节数组根据schema转换成对象，传到空对象中——反序列化
				ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
				return seckill;
			}
			
		} finally{
			jedis.close();
		}
	} catch (Exception e) {
		logger.error(e.getMessage(),e);
	}
	   return null;
   }
   
   //put into cache
   public String putSeckill(Seckill seckill){
	   //把对象序列化——>把字节数组写入redis
    try {
		Jedis jedis=jedisPool.getResource();
		try{
			String key="seckillId:"+seckill.getSeckillId();
			byte[] bytes=ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
			
			//定义超时缓存
			int timeout=60*60;
			//写入缓存
			String result=jedis.setex(key.getBytes(), timeout, bytes);
			return result;
		}finally{
			jedis.close();
		}
	} catch (Exception e) {
		logger.error(e.getMessage(),e);
	}
	   return null;
   }
}
