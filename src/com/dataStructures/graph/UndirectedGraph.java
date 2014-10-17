package com.dataStructures.graph;

import com.dataStructures.Iterator;

/**
 * 无向图
 * 
 * @author 付韶兴
 * @since 2013-1-18
 */
public class UndirectedGraph extends AbstractGraph {

	public void generateMST() {
		resetVexStatus(); // 重置图中各顶点的状态信息
		resetEdgeType(); // 重置图中各边的类型信息
		Iterator it = getVertex(); // （调用图操作③）
		Vertex v = (Vertex) it.currentItem();// 选第一个顶点作为起点
		v.setToVisited(); // 顶点 v 进入集合 S
		// 初始化顶点集合 S 到 V-S 各顶点的最短横切边
		for (it.first(); !it.isDone(); it.next()) {
			Vertex u = (Vertex) it.currentItem();
			Edge e = edgeFromTo(v, u); // （调用图操作⑦）
			setCrossEdge(u, e); // 设置到达 V-S 中顶点 u 的最短横切边
		}
		for (int t = 1; t < getVexNum(); t++) { // 进行|V|-1 次循环找到|V|-1 条边
			Vertex k = selectMinVertex(it); // 选择轻边在 V-S 中的顶点 k
			k.setToVisited(); // 顶点 k 加入 S
			Edge mst = getCrossEdge(k); // 割(S , V - S) 的轻边
			if (mst != null)
				mst.setToMST(); // 将边加入 MST
			// 以 k 为中间顶点修改 S 到 V-S 中顶点的最短横切边

			Iterator adjIt = adjVertexs(k); // 取出 k 的所有邻接点
			for (adjIt.first(); !adjIt.isDone(); adjIt.next()) {
				Vertex adjV = (Vertex) adjIt.currentItem();
				Edge e = edgeFromTo(k, adjV); // （调用图操作⑦）
				if (e.getWeight() < getCrossWeight(adjV))// 发现到达 adjV 更短的横切边
					setCrossEdge(adjV, e);
			}// for
		}// for(int t=1...
	}

	// 查找轻边在 V-S 中的顶点
	protected Vertex selectMinVertex(Iterator it) {
		Vertex min = null;
		for (it.first(); !it.isDone(); it.next()) {
			Vertex v = (Vertex) it.currentItem();
			if (!v.isVisited()) {
				min = v;
				break;
			}
		}
		for (; !it.isDone(); it.next()) {
			Vertex v = (Vertex) it.currentItem();
			if (!v.isVisited() && getCrossWeight(v) < getCrossWeight(min))
				min = v;
		}
		return min;
	}

	// 获取到达顶点 v 的最小横切边权值
	protected int getCrossWeight(Vertex v) {
		if (getCrossEdge(v) != null)
			return getCrossEdge(v).getWeight();
		else
			return Integer.MAX_VALUE;
	}

	// 获取到达顶点 v 的最小横切边
	protected Edge getCrossEdge(Vertex v) {
		return (Edge) v.getAppObj();
	}

	// 设置到达顶点 v 的最小横切边
	protected void setCrossEdge(Vertex v, Edge e) {
		v.setAppObj(e);
	}

	/****************************************/
}
