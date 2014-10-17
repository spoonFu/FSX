package com.dataStructures;

/**
 * 线性表中出现序号越界
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public class OutOfBoundaryException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6830572853903698452L;

	public OutOfBoundaryException(String err) {
		super(err);
	}
}
