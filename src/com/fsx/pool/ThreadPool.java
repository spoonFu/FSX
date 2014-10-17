package com.fsx.pool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThreadPool<T> {
	static Log log = LogFactory.getLog(ThreadPool.class);
	private final int POOL_CYCLE = 1000;
	private int count = 0;// 正在运行线程数量
	private int maxsize;
	/** 等待任务序列 **/
	private Queue<Callable<T>> taskqueue;
	/** 正在运行的任务序列 **/
	private List<Future<T>> futures;
	/** 已完成的任务序列 **/
	private Queue<Future<T>> futureDoneQueue;
	private boolean shutdown = false;// 用来跳出线程循环

	public ThreadPool() {
		this(5);
	}

	public ThreadPool(int maxsize) {
		this.maxsize = maxsize;
		taskqueue = new LinkedList<Callable<T>>();
		futures = new ArrayList<Future<T>>();
		futureDoneQueue = new LinkedList<Future<T>>();
		/** 启动监控线程 **/
		Thread t = new Thread(new PoolMoniter());
		t.start();
	}

	/**
	 * 提交任务
	 * @param task
	 */
	public void submit(Callable<T> task) {
		taskqueue.offer(task);
	}

	/**
	 * 关闭线程池
	 */
	public void shutdown() {
		count = 0;
		shutdown = true;
		taskqueue = null;
		futures = null;
		futureDoneQueue = null;
	}

	/**
	 * 阻塞获取已完成future
	 * @return
	 * @throws Exception 
	 */
	public Future<T> take() throws ExecutionException {
		check();
		while (futureDoneQueue.size()==0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return futureDoneQueue.poll();
	}

	/**
	 * 返回已完成的future，如果都没有完成则返回NULL
	 * @return
	 * @throws Exception 
	 */
	public Future<T> poll() throws ExecutionException {
		check();
		if (futureDoneQueue.size()==0)
			return null;
		return futureDoneQueue.poll();
	}

	/**
	 * 获取当前线程池正在执行任务数量
	 * @return
	 */
	public int getCountInProcess() {
		return count;
	}

	/**
	 * 获取线程池排队任务数量
	 * @return
	 */
	public int getCountInQueue() {
		if(taskqueue==null)
			return 0;
		return taskqueue.size();
	}

	/**
	 * 获取线程池内所有线程数量
	 * @return
	 */
	public int getTotleCount() {
		if(taskqueue==null)
			return 0;
		return count+taskqueue.size();
	}

	private void check() throws ExecutionException{
		if(shutdown){
			throw new ExecutionException(new Exception("线程池已关闭"));
		}
	}
	
	private class PoolMoniter implements Runnable {
		@Override
		public void run() {
			/** 循环检查池运行情况 **/
			while (true) {
				if (shutdown)
					break;
				if (count<maxsize&&!taskqueue.isEmpty()) {
					count++;
					// 当线程池有任务结束时向线程池放入线程
					Callable<T> c = taskqueue.poll();
					FutureTask<T> f = new FutureTask<T>(c);
					futures.add(f);
					Thread t = new Thread(f);
					t.start();
				}
				/****清理执行完毕的线程****/
				try {
					Iterator<Future<T>> i = futures.iterator();
					while(i.hasNext()){
						Future<T> f = i.next();
						if (!f.isDone())
							continue;
						i.remove();
						futureDoneQueue.offer(f);
						count--;
					}
					Thread.sleep(POOL_CYCLE);
				} catch (Exception e1) {
					e1.printStackTrace();
					shutdown();
				}
			}
		}
	}
}
