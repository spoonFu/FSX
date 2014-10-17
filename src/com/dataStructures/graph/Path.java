package com.dataStructures.graph;

import com.dataStructures.Iterator;
import com.dataStructures.list.LinkedList;
import com.dataStructures.list.LinkedListDLNode;


/**
 * 图中的路径
 * 
 * @author 付韶兴
 * @since 2013-1-18
 */
public class Path {
	private int distance; // 起点与终点的距离
	private Vertex start; // 起点信息
	private Vertex end; // 终点信息
	private LinkedList pathInfo; // 起点到终点途经的顶点序列

	// 构造方法
	public Path() {
		this(Integer.MAX_VALUE, null, null);
	}

	public Path(int distance, Vertex start, Vertex end) {
		this.distance = distance;
		this.start = start;
		this.end = end;
		pathInfo = new LinkedListDLNode();
	}

	// 判断起点与终点之间是否存在路径
	public boolean hasPath() {
		return distance != Integer.MAX_VALUE && start != null && end != null;
	}

	// 求路径长度
	public int pathLength() {

		if (!hasPath())
			return -1;
		else if (start == end)
			return 0;
		else
			return pathInfo.getSize() + 1;
	}

	// get&set methods
	public void setDistance(int dis) {
		distance = dis;
	}

	public void setStart(Vertex v) {
		start = v;
	}

	public void setEnd(Vertex v) {
		end = v;
	}

	public int getDistance() {
		return distance;
	}

	public Vertex getStart() {
		return start;
	}

	public Vertex getEnd() {
		return end;
	}

	public Iterator getPathInfo() {
		return pathInfo.elements();
	}

	// 清空路经信息
	public void clearPathInfo() {
		pathInfo = new LinkedListDLNode();
	}

	// 添加路径信息
	public void addPathInfo(Object info) {
		pathInfo.insertLast(info);
	}
}
