package TP.TP1.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ServidorThread implements Runnable {
	private final Socket socket;
	private BufferedReader readFromClient;
	private PrintWriter writeToClient;

	private EstadoServidor estado;

	public ServidorThread(Socket socket) throws IOException {
		this.socket = socket;

		readFromClient = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		writeToClient = new PrintWriter(socket.getOutputStream(), true);
	}

	enum EstadoServidor {
		VALIDARIDENTIDADE, ESPERA_COMANDOS, FIM, TERMINADO
	}

	public void terminar() {
		estado = EstadoServidor.FIM;
	}

	public void run() {
		String rawInput = "";
		while (estado != EstadoServidor.TERMINADO) {

			switch (estado) {
				case VALIDARIDENTIDADE :
					break;
				case ESPERA_COMANDOS :
					try {
						rawInput = readFromClient.readLine();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
					break;
				case FIM :
					break;
				case TERMINADO :
					try {
						readFromClient.close();
						writeToClient.close();
						if (!socket.isClosed()) {
							socket.close();
						}
					} catch (IOException exception) {
						exception.printStackTrace();
					}

					break;

				default :
					break;

			}

		}
	}
}
