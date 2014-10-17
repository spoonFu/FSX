package com.chat;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private ClientFrame frame = new ClientFrame();
	private BufferedReader in;
	private PrintWriter out;

	Client() {
		try {
			Socket s = new Socket("127.0.0.1", 8888);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void launchFrame(String s) {
		frame.validate();
		frame.setTitle(s);
		frame.setBounds(200, 200, 350, 400);
		frame.setMinimumSize(new Dimension(350, 400));
		ClientThread t = new ClientThread();
		t.start();
	}

	public static void main(String[] args) {
		Client c = new Client();
		c.setAction();
		c.launchFrame("CHAT");
	}

	public void setAction() {
		frame.close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		frame.send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.msgEdit.setText("");
				out.write(frame.msgEdit.getText());
			}
		});
	}

	private class ClientThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					String s = in.readLine();
					frame.msgShow.setText(frame.msgShow.getText() + s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
