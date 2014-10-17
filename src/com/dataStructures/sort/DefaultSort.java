package com.dataStructures.sort;

import com.dataStructures.list.DefaultStrategy;
import com.dataStructures.list.Strategy;

/**
 * 排序
 * 
 * @author 付韶兴
 * @since 2013-1-21
 */
public class DefaultSort implements Sort {
	private Strategy strategy = new DefaultStrategy();

	@Override
	public void insertSort(Object[] r) {
		for (int i = 1; i < r.length; i++) {
			if (strategy.compare(r[i], r[i - 1]) < 0) {
				Object temp = r[i];
				r[i] = r[i - 1];
				int j = i - 2;
				for (; j >= 0 && strategy.compare(r[j], temp) > 0; j--) {
					r[j + 1] = r[j];
				}
				r[j + 1] = temp;
			}
			p(r);
		}
	}

	@Override
	public void binInsertSort(Object[] r) {
		for (int i = 1; i < r.length; i++) {
			Object temp = r[i]; // 保存待插入元素
			int hi = i - 1;
			int lo = 0;// 设置初始区间
			while (lo <= hi) { // 折半确定插入位置
				int mid = (lo + hi) / 2;
				if (strategy.compare(temp, r[mid]) < 0)
					hi = mid - 1;
				else
					lo = mid + 1;
			}
			for (int j = i - 1; j > hi; j--) {
				r[j + 1] = r[j]; // 移动元素
			}
			r[hi + 1] = temp; // 插入元素
			p(r);
		}
	}

	@Override
	public void shellSort(Object[] r, int[] delta) {
		for (int deltaK : delta) {
			shellInsert(r, deltaK);// 根据步长对r进行排序
		}
	}

	/**
	 * 根据步长对r进行排序
	 * 
	 * @param r
	 * @param deltaK
	 */
	private void shellInsert(Object[] r, int deltaK) {
		for (int i = deltaK; i < r.length; i++) {
			if (strategy.compare(r[i], r[i - deltaK]) < 0) {
				Object temp = r[i];
				int j = i - deltaK;
				for (; j >= 0 && strategy.compare(temp, r[j]) < 0; j = j
						- deltaK) {
					r[j + deltaK] = r[j];
				}
				r[j + deltaK] = temp;
			}
			p(r);
		}
	}

	private void p(Object[] r) {
		for (Object o : r) {
			System.out.print(o + "  ");
		}
		System.out.println("\n");
	}

	@Override
	public void bubbleSort(Object[] r) {
		for (int i = r.length - 1; i > 0; i--) {
			int bigN = i;
			for (int j = 0; j < i; j++) {
				if (strategy.compare(r[j], r[bigN]) > 0) {
					bigN = j;
				}
			}
			if (bigN != i) {
				Object temp = r[bigN];
				r[bigN] = r[i];
				r[i] = temp;
				p(r);
			}
		}
	}

	@Override
	public void quickSort(Object[] r, int low, int high) {
		if (low < high) {
			int pa = partition(r, low, high);
			quickSort(r, low, pa - 1);
			quickSort(r, pa + 1, high);
		}
		p(r);
	}

	/**
	 * 使用low位置的元素作为枢轴元素，将r中大于枢轴元素的放在右边，反之放在右边
	 */
	private int partition(Object[] r, int low, int high) {
		Object pivot = r[low]; // 使用 r[low]作为枢轴元素
		while (low < high) { // 从两端交替向内扫描
			while (low < high && strategy.compare(r[high], pivot) >= 0) {
				high--;
			}
			r[low] = r[high]; // 将比 pivot 小的元素移向低端
			p(r);
			while (low < high && strategy.compare(r[low], pivot) <= 0) {
				low++;
			}
			r[high] = r[low]; // 将比 pivot 大的元素移向高端
			p(r);
		}
		r[low] = pivot; // 设置枢轴
		return low; // 返回枢轴元素位置
	}

	@Override
	public void selectSort(Object[] r, int low, int high) {
		for (int k = low; k < high - 1; k++) { // 作 n-1 趟选取
			int min = k;
			for (int i = min + 1; i <= high; i++)
				// 选择关键字最小的元素
				if (strategy.compare(r[i], r[min]) < 0)
					min = i;
			if (k != min) {
				Object temp = r[k]; // 关键字最小的元素与元素 r[k]交换
				r[k] = r[min];
				r[min] = temp;
			}
			p(r);
		}
	}

	@Override
	public void mergeSort(Object[] r, int low, int high) {
		if (low < high) {
			mergeSort(r, low, (high + low) / 2);
			mergeSort(r, (high + low) / 2 + 1, high);
			merge(r, low, (high + low) / 2, high);
		}
	}

	/**
	 * 将两个有序的子序列合并为一个有序序列
	 */
	private void merge(Object[] a, int p, int q, int r) {
		Object[] b = new Object[r - p + 1];
		int s = p;
		int t = q + 1;
		int k = 0;
		while (s <= q && t <= r)
			if (strategy.compare(a[s], a[t]) < 0)
				b[k++] = a[s++];
			else
				b[k++] = a[t++];
		while (s <= q)
			b[k++] = a[s++];
		while (t <= r)
			b[k++] = a[t++];
		for (int i = 0; i < b.length; i++)
			a[p + i] = b[i];
	}

	@Override
	public void heapSort(Object[] r) {
		int n = r.length - 1;
		for (int i = n / 2; i >= 0; i--)
			// 初始化建堆
			heapAdjust(r, i, n);
		for (int i = n; i > 0; i--) { // 不断输出堆顶元素并调整 r[1..i-1]为新堆
			Object temp = r[0]; // 交换堆顶与堆底元素
			r[0] = r[i];
			r[i] = temp;
			heapAdjust(r, 0, i - 1); // 调整
			p(r);
		}
	}

	private void heapAdjust(Object[] r, int low, int high) {
		Object temp = r[low];
		for (int j = 2 * low; j <= high; j = j * 2) { // 沿关键之较大的元素向下进行筛选
			// j 指向关键之较大的元素
			if (j < high && strategy.compare(r[j], r[j + 1]) < 0)
				j++;
			// 若 temp 比其孩子都大，则插入到 low 所指位置
			if (strategy.compare(temp, r[j]) >= 0)
				break;
			r[low] = r[j];
			low = j; // 向下筛选
		}
		r[low] = temp;
	}

}
