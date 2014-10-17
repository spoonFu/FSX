package com.invoke;
public class Test {
	public static void main(String[] args) {
		IService s = (IService) new InvocationHandlerImpl()
				.bind(new ServiceImpl());
		String str = s.fsx();
		System.out.println(str);
	}

}
