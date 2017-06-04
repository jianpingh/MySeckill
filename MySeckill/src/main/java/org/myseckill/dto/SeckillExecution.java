package org.myseckill.dto;

import org.myseckill.entity.SuccessKilled;
import org.myseckill.enums.SeckillStateEnum;

//封装秒杀后信息
public class SeckillExecution {

	private long seckillId;
	private int state;
	private String stateInfo;
	private SuccessKilled successKilled;
	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}
	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}
	//秒杀成功的构造函数
	public SeckillExecution(long seckillId, SeckillStateEnum success,SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = success.getState();
		this.stateInfo = success.getStateInfo();
		this.successKilled = successKilled;
	}
	//秒杀失败的构造函数
	public SeckillExecution(long seckillId, SeckillStateEnum success) {
		super();
		this.seckillId = seckillId;
		this.state = success.getState();
		this.stateInfo = success.getStateInfo();
	}		
}
