package TP1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CanalComunicacao extends Thread {
	private ServerSocket socket;
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;

	public CanalComunicacao() throws IOException {
		socket = new ServerSocket(80);
	}

	public void open() throws IOException {
		Socket s = socket.accept();
		inputStream = s.getInputStream();
		objectInputStream = new ObjectInputStream(inputStream);
	}

	public void close() throws IOException {
		inputStream.close();
		objectInputStream.close();
		socket.close();
	}

	public static void main(String[] args) {
		CanalComunicacao cc;
		try {
			cc = new CanalComunicacao();
			System.out.println("Abriu cc");
			cc.open();
			System.out.println("Fechou cc");
			cc.close();
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}

}
