package com.invoke;

/**
 * 动态代理
 * @author FuShaoxing
 */
public class Test {
	public static void main(String[] args) {
		IService s = (IService) new InvocationHandlerImpl().bind(new ServiceImpl());
		s.fsx();
	}

}
