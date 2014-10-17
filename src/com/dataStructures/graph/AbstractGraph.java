package com.dataStructures.graph;

import com.dataStructures.Iterator;
import com.dataStructures.Node;
import com.dataStructures.list.LinkedList;
import com.dataStructures.list.LinkedListDLNode;
import com.dataStructures.list.queue.Queue;
import com.dataStructures.list.queue.QueueSLinked;
import com.dataStructures.list.stack.Stack;
import com.dataStructures.list.stack.StackSLinked;


/**
 * 有向图/无向图公用抽象父类
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public abstract class AbstractGraph implements Graph {
	LinkedList edges;// 所有的边
	LinkedList vertexs;// 所有的顶点

	/** DFSTraverse 输入：顶点 v 输出：图深度优先遍历结果 代码： **/
	public Iterator DFSTraverse(Vertex v) {
		LinkedList traverseSeq = new LinkedListDLNode();// 遍历结果
		resetVexStatus(); // 重置顶点状态
		DFSRecursion(v, traverseSeq); // 从 v 点出发深度优先搜索
		Iterator it = getVertex(); // 从图未曾访问的其他顶点重新搜索（调用图操作③）
		for (it.first(); !it.isDone(); it.next()) {
			Vertex u = (Vertex) it.currentItem();
			if (!u.isVisited())
				DFSRecursion(u, traverseSeq);
		}
		return traverseSeq.elements();
	}

	// 从顶点 v 出发深度优先搜索的递归算法
	private void DFSRecursion(Vertex v, LinkedList list) {
		v.setToVisited(); // 设置顶点 v 为已访问
		list.insertLast(v); // 访问顶点 v
		Iterator it = adjVertexs(v); // 取得顶点 v 的所有邻接点（调用图操作⑧）
		for (it.first(); !it.isDone(); it.next()) {
			Vertex u = (Vertex) it.currentItem();
			if (!u.isVisited())
				DFSRecursion(u, list);
		}
	}

	// 输入：顶点 v，链接表 list 输出：从顶点 v 出发的深度优先搜索 代码：
	// 从顶点 v 出发深度优先搜索的非递归算法
	@SuppressWarnings("unused")
	private void DFS(Vertex v, LinkedList list) {
		Stack s = new StackSLinked();
		s.push(v);
		while (!s.isEmpty()) {
			Vertex u = (Vertex) s.pop(); // 取栈顶元素
			if (!u.isVisited()) { // 如果没有访问过 u.setToVisited(); //访问之
									// list.insertLast(u);
				Iterator it = adjVertexs(u); // 未访问的邻接点入栈（调用图操作⑧）
				for (it.first(); !it.isDone(); it.next()) {
					Vertex adj = (Vertex) it.currentItem();
					if (!adj.isVisited())
						s.push(adj);
				}// for
			}// if
		}// while
	}

	/****************************************/
	/** 输入：顶点 v 输出：图广度优先遍历结果 代码 **/
	public Iterator BFSTraverse(Vertex v) {
		LinkedList traverseSeq = new LinkedListDLNode();// 遍历结果
		resetVexStatus(); // 重置顶点状态
		BFS(v, traverseSeq); // 从 v 点出发广度优先搜索
		Iterator it = getVertex(); // 从图中未访问的顶点重新搜索（调用图操作③）
		for (it.first(); !it.isDone(); it.next()) {
			Vertex u = (Vertex) it.currentItem();

			if (!u.isVisited())
				BFS(u, traverseSeq);
		}
		return traverseSeq.elements();
	}

	private void BFS(Vertex v, LinkedList list) {
		Queue q = new QueueSLinked();
		v.setToVisited(); // 访问顶点 v list.insertLast(v);
		q.enqueue(v); // 顶点 v 入队
		while (!q.isEmpty()) {
			Vertex u = (Vertex) q.dequeue(); // 队首元素出队
			Iterator it = adjVertexs(u); // 访问其未曾访问的邻接点，并入队
			for (it.first(); !it.isDone(); it.next()) {
				Vertex adj = (Vertex) it.currentItem();
				if (!adj.isVisited()) {
					adj.setToVisited();
					list.insertLast(adj);
					q.enqueue(adj);
				}// if
			}// for
		}// while
	}

	/****************************************/
	/**
	 * 重置图中各顶点状态
	 */
	void resetVexStatus() {
		Vertex ver;
		while (!getVertex().isDone()) {
			getVertex().next();
			ver = (Vertex) getVertex().currentItem();
			ver.resetStatus();
		}
	}

	/**
	 * 重置图中各边状态
	 */
	void resetEdgeType() {
		Edge edge;
		while (!getEdge().isDone()) {
			getEdge().next();
			edge = (Edge) getEdge().currentItem();
			edge.resetType();
		}
	}

	/****************************************/
	public Iterator shortestPath(Vertex v) {
		LinkedList sPath = new LinkedListDLNode(); // 所有的最短路径序列
		resetVexStatus(); // 重置图中各顶点的状态信息
		// 初始化，将 v 到各顶点的最短距离初始化为由 v 直接可达的距离
		Iterator it = getVertex(); // （调用图操作③）
		for (it.first(); !it.isDone(); it.next()) {
			Vertex u = (Vertex) it.currentItem();
			int weight = Integer.MAX_VALUE;
			Edge e = edgeFromTo(v, u); // （调用图操作⑦）
			if (e != null)
				weight = e.getWeight();
			if (u == v)
				weight = 0;
			Path p = new Path(weight, v, u);
			setPath(u, p);
		}
		v.setToVisited(); // 顶点 v 进入集合 S
		sPath.insertLast(getPath(v)); // 求得的最短路径进入链接表

		for (int t = 1; t < getVexNum(); t++) { // 进行|V|-1 次循环找到|V|-1 条最短路径
			Vertex k = selectMin(it); // 找 V-S 中 distance 最小的点 k
			k.setToVisited(); // 顶点 k 加入 S
			sPath.insertLast(getPath(k)); // 求得的最短路径进入链接表
			int distK = getDistance(k); // 修正 V-S 中顶点当前最短路径
			Iterator adjIt = adjVertexs(k); // 取出 k 的所有邻接点
			for (adjIt.first(); !adjIt.isDone(); adjIt.next()) {
				Vertex adjV = (Vertex) adjIt.currentItem(); // k 的邻接点 adjV
				Edge e = edgeFromTo(k, adjV); // （调用图操作⑦）
				// 发现更短的路径
				if ((long) distK + (long) e.getWeight() < (long) getDistance(adjV)) {
					setDistance(adjV, distK + e.getWeight());
					amendPathInfo(k, adjV); // 以 k 的路径信息修改 adjV 的路径信息
				}
			}// for
		}// for(int t=1...
		return sPath.elements();
	}

	// 以 k 的路径信息修改 adjV 的路径信息
	private void amendPathInfo(Vertex k, Vertex adjV) {
		adjV.setAppObj(k.getAppObj());
	}

	// 在顶点集合中选择路径距离最小的
	protected Vertex selectMin(Iterator it) {
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
			if (!v.isVisited() && getDistance(v) < getDistance(min))
				min = v;
		}
		return min;
	}

	// 取或设置源点到V的距离
	protected int getDistance(Vertex v) {
		return ((Path) v.getAppObj()).getDistance();
	}

	protected void setDistance(Vertex v, int dis) {
		((Path) v.getAppObj()).setDistance(dis);
	}

	// 取或设置顶点 v的当前最短路径
	protected Path getPath(Vertex v) {
		return (Path) v.getAppObj();
	}

	protected void setPath(Vertex v, Path p) {
		v.setAppObj(p);
	}

	/****************************************/

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getVexNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEdgeNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator getVertex() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getEdge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Vertex v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Edge e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Node insert(Vertex v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node insert(Edge e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean areAdjacent(Vertex u, Vertex v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Edge edgeFromTo(Vertex u, Vertex v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator adjVertexs(Vertex u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generateMST() throws UnsupportedOperation {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator toplogicalSort() throws UnsupportedOperation {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void criticalPath() throws UnsupportedOperation {
		// TODO Auto-generated method stub

	}
}
