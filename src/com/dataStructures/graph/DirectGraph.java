package com.dataStructures.graph;

import com.dataStructures.Iterator;
import com.dataStructures.list.LinkedList;
import com.dataStructures.list.LinkedListDLNode;
import com.dataStructures.list.stack.Stack;
import com.dataStructures.list.stack.StackSLinked;


/**
 * 有向图
 * 
 * @author 付韶兴
 * @since 2013-1-18
 */
public class DirectGraph extends AbstractGraph {
	public Iterator toplogicalSort() {
		LinkedList topSeq = new LinkedListDLNode(); // 拓扑序列
		Stack s = new StackSLinked();
		Iterator it = getVertex();
		for (it.first(); !it.isDone(); it.next()) { // 初始化顶点集应用信息
			Vertex v = (Vertex) it.currentItem();
			v.setAppObj(Integer.valueOf(v.getInDeg()));
			if (v.getInDeg() == 0)
				s.push(v);
		}
		while (!s.isEmpty()) {
			Vertex v = (Vertex) s.pop();
			topSeq.insertLast(v); // 生成拓扑序列
			Iterator adjIt = adjVertexs(v); // 对于 v 的每个邻接点入度减 1
			for (adjIt.first(); !adjIt.isDone(); adjIt.next()) {
				Vertex adjV = (Vertex) adjIt.currentItem();
				int in = getTopInDe(adjV) - 1;
				setTopInDe(adjV, in);
				if (in == 0)
					s.push(adjV); // 入度为 0 的顶点入栈
			}// for adjIt
		}// while
		if (topSeq.getSize() < getVexNum())
			return null;
		else
			return topSeq.elements();
	}

	// 取或设置顶点 v 的当前入度
	private int getTopInDe(Vertex v) {
		return ((Integer) v.getAppObj()).intValue();
	}

	private void setTopInDe(Vertex v, int indegree) {
		v.setAppObj(Integer.valueOf(indegree));
	}

	/****************************************/
	public void criticalPath() {
		Iterator it = toplogicalSort();
		resetEdgeType(); // 重置图中各边的类型信息
		if (it == null)
			return;
		LinkedList reTopSeq = new LinkedListDLNode(); // 逆拓扑序列
		for (it.first(); !it.isDone(); it.next()) { // 初始化各点 ve 与 vl，并生成逆拓扑序列
			Vertex v = (Vertex) it.currentItem();
			Vtime time = new Vtime(0, Integer.MAX_VALUE); // ve=0,vl=∞
			v.setAppObj(time);
			reTopSeq.insertFirst(v);
		}
		for (it.first(); !it.isDone(); it.next()) { // 正向拓扑序列求各点 ve
			Vertex v = (Vertex) it.currentItem();
			Iterator adjIt = adjVertexs(v);
			for (adjIt.first(); !adjIt.isDone(); adjIt.next()) {
				Vertex adjV = (Vertex) adjIt.currentItem();
				Edge e = edgeFromTo(v, adjV);
				if (getVE(v) + e.getWeight() > getVE(adjV)) // 更新最早开始时间
					setVE(adjV, getVE(v) + e.getWeight());
			}

		}
		Vertex dest = (Vertex) reTopSeq.first().getData();
		setVL(dest, getVE(dest)); // 设置汇点 vl=ve
		Iterator reIt = reTopSeq.elements();
		for (reIt.first(); !reIt.isDone(); reIt.next()) { // 逆向拓扑序列求各点 vl
			Vertex v = (Vertex) reIt.currentItem();
			Iterator adjIt = adjVertexs(v);
			for (adjIt.first(); !adjIt.isDone(); adjIt.next()) {
				Vertex adjV = (Vertex) adjIt.currentItem();
				Edge e = edgeFromTo(v, adjV);
				if (getVL(v) > getVL(adjV) - e.getWeight()) // 更新最迟开始时间
					setVL(v, getVL(adjV) - e.getWeight());
			}
		}
		Iterator edIt = edges.elements();
		for (edIt.first(); !edIt.isDone(); edIt.next()) { // 求关键活动
			Edge e = (Edge) edIt.currentItem();
			Vertex u = e.getFirstVex();
			Vertex v = e.getSecondVex();
			if (getVE(u) == getVL(v) - e.getWeight())
				e.setToCritical();
		}
	}

	// 取顶点 v 的最早开始时间与最迟开始时间
	private int getVE(Vertex v) {
		return ((Vtime) v.getAppObj()).getVE();
	}

	private int getVL(Vertex v) {
		return ((Vtime) v.getAppObj()).getVL();
	}

	// 设置顶点 v 的最早开始时间与最迟开始时间
	private void setVE(Vertex v, int ve) {
		((Vtime) v.getAppObj()).setVE(ve);
	}

	private void setVL(Vertex v, int vl) {

		((Vtime) v.getAppObj()).setVL(vl);
	}

}
