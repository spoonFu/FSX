package com.invoke;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * @author FuShaoxing
 */
public class InvocationHandlerImpl implements InvocationHandler {

	private Object delegate;

	/**
	 * 返回一个代理类的对象
	 * @param delegate
	 * @return
	 */
	public Object bind(Object delegate) {
		this.delegate = delegate;
		ClassLoader cl = delegate.getClass().getClassLoader();
		Class<?>[] intfce = delegate.getClass().getInterfaces();
		return Proxy.newProxyInstance(cl, intfce, this);
	}

	/**
	 *  生成代理对象被调用方法时 
	 *  method为被调用方法
	 *  args[]为传入参数
	 *  proxy
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object ret = null;
		if (method.getName().startsWith("fsx")) {
			System.out.println("before");
			ret = method.invoke(this.delegate, args);
			System.out.println("after");
		} else {
			ret = method.invoke(this.delegate, args);
		}
		System.out.println(proxy.getClass().getName());
		return ret;
	}

}
