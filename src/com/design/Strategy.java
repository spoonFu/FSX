package com.design;

public class Strategy {
	public static void main(String[] args) {
		Cat.setComparer(new CatWeightComparer());// 添加策略
		Cat[] cs = new Cat[] { new Cat(3, 5), new Cat(2, 3), new Cat(5, 4), new Cat(4, 6), new Cat(1, 2), new Cat(6, 1) };
		Sorter.bubbleSort(cs);
		for (Cat c : cs) {
			System.out.print(c+"  ");
		}
	}
}

interface Comparable<T> {
	int compareTo(T t);
}

interface Comparer<T> {
	int compare(T t1, T t2);
}

class Cat implements Comparable<Cat> {
	int height;
	int weight;
	static Comparer<Cat> comparer;// 比较策略

	public static Comparer<Cat> getComparer() {
		return comparer;
	}

	public static void setComparer(Comparer<Cat> comparer) {
		Cat.comparer = comparer;
	}

	public int getHeight() {
		return height;
	}

	public int getWeight() {
		return weight;
	}

	Cat(int height, int weight) {
		this.height = height;
		this.weight = weight;
	}

	@Override
	public int compareTo(Cat t) {
		return comparer.compare(this, t);
	}

	@Override
	public String toString() {
		return height+"|"+weight;
	}
}

/**
 * 重量比较策略
 * 
 * @author 付韶兴
 * @since 2013-2-18
 */
class CatWeightComparer implements Comparer<Cat> {

	@Override
	public int compare(Cat t1, Cat t2) {
		if (t1.weight>t2.weight)
			return 1;
		if (t1.weight<t2.weight)
			return -1;
		return 0;
	}
}

/**
 * 高度比较策略
 * 
 * @author 付韶兴
 * @since 2013-2-18
 */
class CatHeightComparer implements Comparer<Cat> {

	@Override
	public int compare(Cat t1, Cat t2) {
		if (t1.height>t2.height)
			return 1;
		if (t1.height<t2.height)
			return -1;
		return 0;
	}
}

class Sorter {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void bubbleSort(Comparable[] r) {
		for (int i = r.length-1; i>0; i--) {
			int bigN = i;
			for (int j = 0; j<i; j++) {
				if (r[j].compareTo(r[bigN])>0) {
					bigN = j;
				}
			}
			if (bigN!=i) {
				Comparable temp = r[bigN];
				r[bigN] = r[i];
				r[i] = temp;
			}
		}
	}
}