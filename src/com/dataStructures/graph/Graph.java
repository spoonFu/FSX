package com.dataStructures.graph;

import com.dataStructures.Iterator;
import com.dataStructures.Node;

/**
 * 图
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public interface Graph {
	/**
	 * 无向图
	 */
	public static final int UndirectedGraph = 0;
	/**
	 * 有向图
	 */
	public static final int DirectedGraph = 1;

	/**
	 * 返回图的类型
	 */
	public int getType();

	/**
	 * 返回图的顶点数
	 * 
	 * @return
	 */
	public int getVexNum();

	/**
	 * 返回图的边数
	 * 
	 * @return
	 */
	public int getEdgeNum();

	/**
	 * 返回图的所有顶点
	 * 
	 * @return
	 */
	public Iterator getVertex();

	/**
	 * 返回图的所有边
	 * 
	 * @return
	 */
	public Iterator getEdge();

	/**
	 * 删除一个顶点 v
	 * 
	 * @param v
	 */
	public void remove(Vertex v);

	/**
	 * 删除一条边 e
	 * 
	 * @param e
	 */
	public void remove(Edge e);

	/**
	 * 添加一个顶点 v
	 * 
	 * @param v
	 * @return
	 */
	public Node insert(Vertex v);

	/**
	 * 添加一条边 e
	 * 
	 * @param e
	 * @return
	 */
	public Node insert(Edge e);

	/**
	 * 判断顶点 u、v 是否邻接，即是否有边从 u 到 v
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	public boolean areAdjacent(Vertex u, Vertex v);

	/**
	 * 返回从 u 指向 v 的边，不存在则返回 null
	 * 
	 * @param u
	 * @param v
	 * @return
	 */
	public Edge edgeFromTo(Vertex u, Vertex v);

	/**
	 * 返回从 u 出发可以直接到达的邻接顶点
	 * 
	 * @param u
	 * @return
	 */
	public Iterator adjVertexs(Vertex u);

	/**
	 * 对图进行深度优先遍历
	 * 
	 * @param v
	 *            顶点
	 * @return
	 */
	public Iterator DFSTraverse(Vertex v);

	/**
	 * 对图进行广度优先遍历
	 * 
	 * @param v
	 *            顶点
	 * @return
	 */
	public Iterator BFSTraverse(Vertex v);

	/**
	 * 求顶点 v 到其他顶点的最短路径<br />
	 * 时间复杂度为点规模的平方
	 * 
	 * @param v
	 * @return
	 */
	public Iterator shortestPath(Vertex v);

	/**
	 * 求无向图的最小生成树,如果是有向图不支持此操作
	 * 
	 * @throws UnsupportedOperation
	 */
	public void generateMST() throws UnsupportedOperation;

	/**
	 * 求有向图的拓扑序列,无向图不支持此操作
	 * 
	 * @return
	 * @throws UnsupportedOperation
	 */
	public Iterator toplogicalSort() throws UnsupportedOperation;

	/**
	 * 求有向无环图的关键路径,无向图不支持此操作
	 * 
	 * @throws UnsupportedOperation
	 */
	public void criticalPath() throws UnsupportedOperation;
}
