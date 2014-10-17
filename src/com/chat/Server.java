package com.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private int port = 8888;
	private List<ServerThread> clients = new ArrayList<ServerThread>();
	private int maxSize = 5;

	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void start() throws IOException {
		ServerSocket ss = new ServerSocket(port);
		while (true) {
			ServerThread client = new ServerThread(ss.accept());
			clients.add(client);
			client.start();
		}
	}

	private class ServerThread extends Thread {
		private BufferedReader in;
		private PrintWriter out;
		private String name;
		private String ip;

		public PrintWriter getOut() {
			return out;
		}

		ServerThread(Socket s) throws IOException {
			if (clients.size() > maxSize) {
				notice(s.getInetAddress().getHostName() + "("
						+ s.getInetAddress().getHostAddress()
						+ ")请求加入会话失败，已达最大人数" + maxSize);
				return;
			}
			this.in = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			this.out = new PrintWriter(s.getOutputStream());
			name = s.getInetAddress().getHostName();
			ip = s.getInetAddress().getHostAddress();
			System.out.println(name + "(" + ip + ")加入会话!");
			notice(name + "(" + ip + ")加入会话!");
		}

		@Override
		public void run() {
			try {
				System.out.println("开始");
				while (true) {
					String msg = in.readLine();
					System.out.println(name + "(" + ip + "):\r" + msg + "\r");
					notice(name + "(" + ip + "):\r" + msg + "\r");
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(name + "(" + ip + ")退出!");
				out.close();
				clients.remove(this);
			}
		}

		private void notice(String msg) {
			for (ServerThread client : clients) {
				client.getOut().write(msg);
			}
		}
	}
}
