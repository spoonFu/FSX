package com.test;

import java.io.IOException;

public class TestNet {
	public static int port = 9999;

	public static void main(String args[]) throws IOException {
		A a = new A();
		a.a2();
		
	}

	public static class A {
		public void a2(){};
	}

}

final class A{
	public void a1(){};
}