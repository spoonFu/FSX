package com.fsx.pool;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ThreadPool {
	static Log log = LogFactory.getLog(ThreadPool.class);
	private int count = 0;// ���������߳�����
	private int maxsize = 5;
	/** bat�������� **/
	private Queue<Callable<Object>> taskqueue;
	private List<Future> futures;
	private boolean pause = false;// ���������߳�ѭ��
	
	public ThreadPool() {
		taskqueue = new LinkedList<Callable<Object>>();
	}
	
	public ThreadPool(int maxsize) {
		taskqueue = new LinkedList<Callable<Object>>();
		this.maxsize = maxsize;
	}

	/**
	 * �ر��̳߳�
	 */
	public void shutdown() {
		taskqueue.clear();
	}
	
	public Future take(){
		
		while(){
			
		}
	}
	
	public Future poll(){
		
	}
	
	
	private class poolmoniter implements Runnable{

		@Override
		public void run() {
			/** ѭ������������� **/
			while (true) {
				if (pause)
					break;
				if (count<maxsize&&!taskqueue.isEmpty()) {
					// ���̳߳����������ʱ���̳߳ط����߳�
					Callable<Object> c = taskqueue.poll();
					FutureTask<Object> f = new FutureTask<Object>(c);
					futures.add(f);
					Thread t = new Thread(f);
					t.start();
					count++;
				}
				/****����ִ����ϵ��߳�****/
				try {
					for(Future f : futures){
						if(!f.isDone())
							continue;
						Object o = f.get();
					}
					if (future!=null){
						count--;
					}
					Thread.sleep(Config.POOL_CYCLE);
				} catch (Exception e1) {
					e1.printStackTrace();
					shutdown();
				}
			}
		
		}
		
	}
}
