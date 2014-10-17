package com.invoke;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InvocationHandlerImpl implements InvocationHandler {

	private Object delegate;

	public Object bind(Object delegate) {
		this.delegate = delegate;
		return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
				delegate.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object ret = null;
		if (method.getName().startsWith("fsx")) {
			System.out.println("before");
			ret = method.invoke(this.delegate, args);
			System.out.println("after");
		} else {
			ret = method.invoke(this.delegate, args);
		}
		return ret;
	}

}
