package com.dataStructures;

/**
 * 节点不合法
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public class InvalidNodeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5214100480583296693L;

	public InvalidNodeException(String err) {
		super(err);
	}
}
