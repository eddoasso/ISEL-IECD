package TP.TP1.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import TP.TP1.Server.User.UserDatabase;

public class MainServer implements Runnable {
	public static final int DEFAULTPORTO = 5025;

	private ServerSocket serverSocket;
	private Socket localSocket;
	private Thread thread;
	private EstadoServidor estado;
	private UserDatabase database;

	enum EstadoServidor {
		ACEITAR_LIGACAO, INICIAR_THREAD, FIM, TERMINADO
	}

	public MainServer() {
		this(DEFAULTPORTO);
	}
	public MainServer(int porto) {
		try {
			serverSocket = new ServerSocket(porto);
			database = new UserDatabase();
		} catch (Exception exception) {
			exception.printStackTrace();
			System.exit(0);
		}
		estado = EstadoServidor.ACEITAR_LIGACAO;

		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (estado != EstadoServidor.TERMINADO) {
			switch (estado) {
				case ACEITAR_LIGACAO :
					try {
						localSocket = serverSocket.accept();
						estado = EstadoServidor.INICIAR_THREAD;
					} catch (IOException exception) {
						exception.printStackTrace();
						estado = EstadoServidor.FIM;
					}
					break;
				case INICIAR_THREAD :
					try {
						new LoginThread(localSocket, database);
					} catch (IOException exception1) {
						exception1.printStackTrace();
						estado = EstadoServidor.FIM;
					}
					break;
				case FIM :
					try {
						if (!localSocket.isClosed())
							localSocket.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}

					break;
				case TERMINADO :
					// NAO deve entrar aqui pelo que nao importa
					break;
				default :
					estado = EstadoServidor.FIM;
					break;
			}
		}
	}
	public static void main(String[] args) {
		MainServer server = new MainServer();
		server.run();
	}

}
