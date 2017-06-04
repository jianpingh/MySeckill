package org.myseckill.exception;

//秒杀已关闭异常
public class SeckillClosedException extends SeckillException {

	public SeckillClosedException() {
		super();
		
	}

	public SeckillClosedException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public SeckillClosedException(String message) {
		super(message);
		
	}

	public SeckillClosedException(Throwable cause) {
		super(cause);
		
	}

}
