package TP.TP1.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer implements Runnable {
	public static final int DEFAULTPORTO = 5025;

	private ServerSocket serverSocket;
	private Socket localSocket;
	private Thread thread;
	private EstadoServidor estado;

	public MainServer() {
		this(DEFAULTPORTO);
	}
	public MainServer(int porto) {
		try {
			serverSocket = new ServerSocket(porto);
		} catch (IOException exception) {
			exception.printStackTrace();
			System.exit(0);
		}

		thread = new Thread(this);
		thread.start();
		estado = EstadoServidor.ACEITAR_LIGACAO;
	}

	enum EstadoServidor {
		ACEITAR_LIGACAO, INICIAR_THREAD, FIM, TERMINADO
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
						// TODO Auto-generated catch block
						exception.printStackTrace();
						estado = EstadoServidor.FIM;
					}
					break;
				case INICIAR_THREAD :
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

}
