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
	private int count = 0;// ���������߳�����
	private int maxsize;
	/** �ȴ��������� **/
	private Queue<Callable<T>> taskqueue;
	/** �������е��������� **/
	private List<Future<T>> futures;
	/** ����ɵ��������� **/
	private Queue<Future<T>> futureDoneQueue;
	private boolean shutdown = false;// ���������߳�ѭ��

	public ThreadPool() {
		this(5);
	}

	public ThreadPool(int maxsize) {
		this.maxsize = maxsize;
		taskqueue = new LinkedList<Callable<T>>();
		futures = new ArrayList<Future<T>>();
		futureDoneQueue = new LinkedList<Future<T>>();
		/** ��������߳� **/
		Thread t = new Thread(new PoolMoniter());
		t.start();
	}

	/**
	 * �ύ����
	 * @param task
	 */
	public void submit(Callable<T> task) {
		taskqueue.offer(task);
	}

	/**
	 * �ر��̳߳�
	 */
	public void shutdown() {
		count = 0;
		shutdown = true;
		taskqueue = null;
		futures = null;
		futureDoneQueue = null;
	}

	/**
	 * ������ȡ�����future
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
	 * ��������ɵ�future�������û������򷵻�NULL
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
	 * ��ȡ��ǰ�̳߳�����ִ����������
	 * @return
	 */
	public int getCountInProcess() {
		return count;
	}

	/**
	 * ��ȡ�̳߳��Ŷ���������
	 * @return
	 */
	public int getCountInQueue() {
		if(taskqueue==null)
			return 0;
		return taskqueue.size();
	}

	/**
	 * ��ȡ�̳߳��������߳�����
	 * @return
	 */
	public int getTotleCount() {
		if(taskqueue==null)
			return 0;
		return count+taskqueue.size();
	}

	private void check() throws ExecutionException{
		if(shutdown){
			throw new ExecutionException(new Exception("�̳߳��ѹر�"));
		}
	}
	
	private class PoolMoniter implements Runnable {
		@Override
		public void run() {
			/** ѭ������������� **/
			while (true) {
				if (shutdown)
					break;
				if (count<maxsize&&!taskqueue.isEmpty()) {
					count++;
					// ���̳߳����������ʱ���̳߳ط����߳�
					Callable<T> c = taskqueue.poll();
					FutureTask<T> f = new FutureTask<T>(c);
					futures.add(f);
					Thread t = new Thread(f);
					t.start();
				}
				/****����ִ����ϵ��߳�****/
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
