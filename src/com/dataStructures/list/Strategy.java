package com.dataStructures.list;

/**
 * 对象之间的比较策略
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public interface Strategy {
	// 判断两个数据元素是否相等
	public boolean equal(Object obj1, Object obj2);

	/**
	 * 比较两个数据元素的大小 如果 obj1 < obj2 返回-1 如果 obj1 = obj2 返回 0
	 * 
	 * 如果 obj1 > obj2 返回 1
	 */
	public int compare(Object obj1, Object obj2);
}
