package com.dataStructures.search;

import com.dataStructures.Iterator;
import com.dataStructures.Node;

/**
 * 查找表接口定义
 * 
 * @author 付韶兴
 * @since 2013-1-18
 */
public interface SearchTable {
	// 查询查找表当前的规模
	public int getSize();

	// 判断查找表是否为空
	public boolean isEmpty();

	// 返回查找表中与元素 ele 关键字相同的元素位置；否则，返回 null
	public Node search(Object ele);

	// 返回所有关键字与元素 ele 相同的元素位置
	public Iterator searchAll(Object ele);

	// 按关键字插入元素 ele
	public void insert(Object ele);

	// 若查找表中存在与元素 ele 关键字相同元素，则删除一个并返回；否则，返回 null
	public Object remove(Object ele);
}
