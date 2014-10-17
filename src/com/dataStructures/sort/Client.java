package com.dataStructures.sort;

public class Client {
	private Sort sort = new DefaultSort();

	public static void main(String args[]) {
		Client c = new Client();
		Integer[] test1 = { 26, 53, 48, 11, 13, 48, 32, 15, 9, 88, 52, 76, 1, 55, 95, 65, 49, 25, 35, 19, 75 };
		//Integer[] test2 = { 1, 9, 11, 13, 15, 19, 25, 26, 32, 35, 48, 48, 49, 52, 53, 55, 65, 75, 76, 88, 95 };
		//Integer[] test3 = { 95, 88, 76, 75, 65, 55, 53, 52, 49, 48, 48, 35, 32, 26, 25, 19, 15, 13, 11, 9, 1 };
		c.p("乱序");
		// c.sort.insertSort(test1);
		// c.sort.binInsertSort(test1);
		// c.sort.shellSort(test1, new int[] { 5, 3, 1 });
		// c.sort.bubbleSort(test1);
		// c.sort.quickSort(test1, 0, test1.length - 1);
		c.sort.heapSort(test1);
	}

	private void p(String s) {
		System.out.println(s);
	}
}
