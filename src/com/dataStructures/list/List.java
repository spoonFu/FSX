package com.dataStructures.list;

import com.dataStructures.OutOfBoundaryException;

/**
 * 表接口
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public interface List {
	// 返回线性表的大小，即数据元素的个数。
	public int getSize();

	// 如果线性表为空返回 true，否则返回 false。
	public boolean isEmpty();

	// 判断线性表是否包含数据元素 e public boolean contains(Object e);
	// 返回数据元素 e 在线性表中的序号
	public int indexOf(Object e);

	// 将数据元素 e 插入到线性表中 i 号位置
	public void insert(int i, Object e) throws OutOfBoundaryException;

	// 将数据元素 e 插入到元素 obj 之前
	public boolean insertBefore(Object obj, Object e);

	// 将数据元素 e 插入到元素 obj 之后
	public boolean insertAfter(Object obj, Object e);

	// 删除线性表中序号为 i 的元素,并返回之
	public Object remove(int i) throws OutOfBoundaryException;

	// 删除线性表中第一个与 e 相同的元素
	public boolean remove(Object e);

	// 替换线性表中序号为 i 的数据元素为 e，返回原数据元素
	public Object replace(int i, Object e) throws OutOfBoundaryException;

	// 返回线性表中序号为 i 的数据元素
	public Object get(int i) throws OutOfBoundaryException;
}
