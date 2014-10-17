package com.fsx;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.fsx.pool.ThreadPool;

public class ThreadPoolTest {
	public static void main(String[] args) {
		ThreadPool<String> pool = new ThreadPool<String>(2);
		pool.submit(new T("thread1", 10));
		pool.submit(new T("thread2", 15));
		pool.submit(new T("thread3", 20));
		pool.submit(new T("thread4", 25));
		pool.submit(new T("thread5", 30));
		pool.submit(new T("thread6", 25));
		pool.submit(new T("thread7", 20));
		pool.submit(new T("thread8", 15));
		pool.submit(new T("thread9", 10));
		for(int i=0;i<9;i++){
			try {
				System.out.println(pool.take().get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
class T implements Callable<String> {
	private String name;
	private int count = 20;

	public T(String name, int count) {
		this.name = name;
		this.count = count;
	}

	@Override
	public String call() {
		while (count>0) {
			System.out.println(name+":"+count);
			count--;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return name + ":done";
	}
}
