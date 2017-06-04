package org.myseckill.dto;

import javax.swing.text.StyledEditorKit.BoldAction;

//封装数据结果，返回给前端
public class SeckillResult<T> {

	private boolean success;
	private T data;
	private String error;
	//成功结果：true,数据
	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}
	//失败结果：false，错误信息
	public SeckillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
}
