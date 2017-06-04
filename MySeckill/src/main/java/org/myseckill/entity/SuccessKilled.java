package org.myseckill.entity;

import java.util.Date;

public class SuccessKilled {
    private long SeckillId;
    
    private long userPhone;
    
    private int state;
    
    private Date createTime;
    
    //映射关系：多对一
    private Seckill seckill;

	public long getSeckillId() {
		return SeckillId;
	}

	public void setSeckillId(long seckillId) {
		SeckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	@Override
	public String toString() {
		return "SuccessKilled [SeckillId=" + SeckillId + ", userPhone="
				+ userPhone + ", state=" + state + ", createTime=" + createTime
				+ ", seckill=" + seckill + "]";
	}
    
}
