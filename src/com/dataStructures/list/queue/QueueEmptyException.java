package com.dataStructures.list.queue;

/**
 * 队列为空时出队或取队首元素抛出此异常
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public class QueueEmptyException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5286030751182273067L;

	public QueueEmptyException(String err) {
		super(err);
	}
}
