package testKS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ThreadClient {

	private String host = "localhost";// 默认连接到本机
	private int port = 6003;// 默认连接到端口8189

	public ThreadClient() {

	}

	// 连接到指定的主机和端口
	public ThreadClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void chat() {
		try {
			// 连接到服务器
			Socket socket = new Socket(host, port);

			try {
				// 读取服务器端传过来信息的DataInputStream
				DataInputStream in = new DataInputStream(
						socket.getInputStream());
				// 向服务器端发送信息的DataOutputStream
				DataOutputStream out = new DataOutputStream(
						socket.getOutputStream());

				// 装饰标准输入流，用于从控制台输入
				// Scanner scanner = new Scanner(System.in);
				int i = 0;
				while (true) {
					i++;
					if (i == 10)
						break;
					// String send = scanner.nextLine();
					String send = "zyl.客户端" + i;
					// System.out.println("客户端：" + send);
					// 把从控制台得到的信息传送给服务器
					out.writeUTF("客户端say：" + send);
					// 读取来自服务器的信息
					/*
					 * String accpet = in.readUTF(); System.out.println(accpet);
					 */
				}

			} finally {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
