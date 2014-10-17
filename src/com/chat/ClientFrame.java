package com.chat;

import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JFrame;

public class ClientFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public javax.swing.JButton close;
	public javax.swing.JButton send;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	public javax.swing.JTextArea msgShow;
	public javax.swing.JTextArea msgEdit;

	ClientFrame() {
		initComponents();
	}

	private void initComponents() {
		setVisible(true);
		jScrollPane1 = new javax.swing.JScrollPane();
		msgShow = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		msgEdit = new javax.swing.JTextArea();
		close = new javax.swing.JButton();
		send = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		msgShow.setColumns(20);
		msgShow.setRows(5);
		jScrollPane1.setViewportView(msgShow);

		msgEdit.setColumns(20);
		msgEdit.setRows(5);
		jScrollPane2.setViewportView(msgEdit);

		close.setText("关闭");
		send.setText("发送");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		ParallelGroup parallelGroup = layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addComponent(close).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(send)).addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE);
		
		SequentialGroup sequentialGroup = layout.createSequentialGroup().addContainerGap().addGroup(parallelGroup).addContainerGap();

		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(sequentialGroup));

		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(send).addComponent(close))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
	}
}
