package com.dataStructures.list;

/**
 * 默认比较策略
 * 
 * @author 付韶兴
 * @since 2013-1-17
 */
public class DefaultStrategy implements Strategy {

	@Override
	public boolean equal(Object obj1, Object obj2) {
		return obj1.equals(obj2);
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		if (obj1 instanceof Integer && obj2 instanceof Integer) {
			if ((Integer) obj1 > (Integer) obj2) {
				return 1;
			}
			if ((Integer) obj1 < (Integer) obj2) {
				return -1;
			}
			if ((Integer) obj1 == (Integer) obj2) {
				return 0;
			}
		}
		return 0;
	}

}
