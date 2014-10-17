package com.dataStructures.graph;

/**
 * 调用图不支持的操作时(有序/无序)
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public class UnsupportedOperation extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3451855690289175422L;

	public UnsupportedOperation(String err) {
		super(err);
	}
}
