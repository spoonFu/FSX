package com.dataStructures;

/**
 * 汉诺塔递归解决方案
 * 
 * @author 付韶兴
 * @since 2013-1-16
 */
public class Hanio {
	public static void main(String args[]) {
		Hanio t = new Hanio();
		t.hanio(10, 'x', 'y', 'z');
	}

	public void hanio(int n, char x, char y, char z) {
		if (n == 1)
			move(x, n, z);
		else {
			hanio(n - 1, x, z, y);
			move(x, n, z);
			hanio(n - 1, y, x, z);
		}
	}

	private void move(char x, int n, char y) {
		System.out.println("Move " + n + " from " + x + " to " + y);
	}
}
