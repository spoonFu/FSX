package com.dataStructures.graph;

import com.dataStructures.Node;
import com.dataStructures.list.LinkedList;
import com.dataStructures.list.LinkedListDLNode;


/**
 * 双链式存储结构中顶点的定义
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public class Vertex {
	private Object info; // 顶点信息
	private LinkedList adjacentEdges; // 顶点的邻接边表
	private LinkedList reAdjacentEdges; // 顶点的逆邻接边表，无向图时为空
	private boolean visited; // 访问状态

	private Node vexPosition; // 顶点在顶点表中的位置
	private int graphType; // 顶点所在图的类型
	private Object application; // 应用。如求最短路径时为 Path，求关键路径时为 Vtime

	// 构造方法:在图 G 中引入一个新顶点
	public Vertex(Graph g, Object info) {
		this.info = info;
		adjacentEdges = new LinkedListDLNode();
		reAdjacentEdges = new LinkedListDLNode();
		visited = false;
		graphType = g.getType();
		vexPosition = g.insert(this);
	}

	// 辅助方法:判断顶点所在图的类型
	private boolean isUnDiGraphNode() {
		return graphType == Graph.UndirectedGraph;
	}

	// 获取或设置顶点信息
	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	// 与顶点的度相关的方法
	public int getDeg() {
		if (isUnDiGraphNode())
			return adjacentEdges.getSize(); // 无向图顶点的(出/入)度为邻接边表规模
		else
			return getOutDeg() + getInDeg(); // 有向图顶点的度为出度与入度之和
	}

	public int getOutDeg() {
		return adjacentEdges.getSize(); // 有(无)向图顶点的出度为邻接表规模
	}

	public int getInDeg() {
		if (isUnDiGraphNode())
			return adjacentEdges.getSize(); // 无向图顶点的入度就是它的度
		else
			return reAdjacentEdges.getSize(); // 有向图顶点入度为逆邻接表的规模
	}

	// 获取与顶点关联的边
	public LinkedList getAdjacentEdges() {
		return adjacentEdges;
	}

	public LinkedList getReAdjacentEdges() {
		if (isUnDiGraphNode())
			return adjacentEdges; // 无向图顶点的逆邻接边表就是其邻接边表

		else

			return reAdjacentEdges;

	}

	// 取顶点在所属图顶点集中的位置
	public Node getVexPosition() {
		return vexPosition;
	}

	// 与顶点访问状态相关方法
	public boolean isVisited() {
		return visited;
	}

	public void setToVisited() {
		visited = true;
	}

	public void setToUnvisited() {
		visited = false;
	}

	// 取或设置顶点应用信息
	protected Object getAppObj() {
		return application;
	}

	protected void setAppObj(Object app) {
		application = app;
	}

	// 重置顶点状态信息
	public void resetStatus() {
		visited = false;
		application = null;
	}
}
