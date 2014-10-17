package com.design;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式(模仿button事件)
 * 
 * @author 付韶兴
 * @since 2013-2-18
 */
public class Observer {
	public static void main(String[] args) {
		Button b = new Button();
		b.addListener(new MyActionListener());
		b.addListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("button pressed!!*");// 匿名类
			}
		});
		b.pressed();
	}
}

class Button {
	/**
	 * 观察者们
	 */
	List<ActionListener> listeners = new ArrayList<ActionListener>();

	public void pressed() {
		ActionEvent e = new ActionEvent(System.currentTimeMillis(), this);// 构造要传递给观察者的事件
		for (ActionListener listener : listeners) {
			listener.actionPerformed(e);// 调用观察者的相应事件响应方法
		}
	}

	/**
	 * 为button添加观察者
	 * 
	 * @param l
	 */
	public void addListener(ActionListener l) {
		listeners.add(l);
	}
}

/**
 * 事件
 * 
 * @author 付韶兴
 * @since 2013-2-18
 */
class ActionEvent {
	long time;

	Object source;

	ActionEvent(long time, Object source) {
		this.time = time;
		this.source = source;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}
}

/**
 * 观察者接口
 * 
 * @author 付韶兴
 * @since 2013-2-18
 */
interface ActionListener {
	void actionPerformed(ActionEvent e);
}

/**
 * 默认观察者实现
 * 
 * @author 付韶兴
 * @since 2013-2-18
 */
class MyActionListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("button pressed!");
	}
}