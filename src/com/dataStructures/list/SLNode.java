package com.dataStructures.list;

import com.dataStructures.Node;

/**
 * 单链表节点定义
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public class SLNode implements Node {
	private Object element;
	private SLNode next;

	public SLNode() {
		this(null, null);
	}

	public SLNode(Object ele, SLNode next) {
		this.element = ele;
		this.next = next;
	}

	public SLNode getNext() {
		return next;
	}

	public void setNext(SLNode next) {
		this.next = next;
	}

	/**************** Methods of Node Interface **************/
	public Object getData() {

		return element;
	}

	public void setData(Object obj) {
		element = obj;
	}
}
