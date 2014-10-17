package com.dataStructures.list.stack;

/**
 * 堆栈为空时出栈或取栈顶元素抛出此异常
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public class StackEmptyException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7287765579342053676L;

	public StackEmptyException(String err) {
		super(err);
	}
}
