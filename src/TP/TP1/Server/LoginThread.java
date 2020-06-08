package TP.TP1.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import TP.TP1.Server.Files.Messages;
import TP.TP1.Server.User.UserInfo;
import TP.TP1.Server.User.UserDatabase;

public class LoginThread implements Runnable {
	private Thread thread;

	private final Socket socket;
	private BufferedReader readFromClient;
	private PrintWriter writeToClient;

	private EstadoServidor estado;
	enum EstadoServidor {
		ESPERA_COMANDOS, VALIDARIDENTIDADE, FIM, TERMINADO
	}

	private UserDatabase userDatabase;
	public LoginThread(Socket socket, UserDatabase userDatabase)
			throws IOException {

		this.socket = socket;
		this.userDatabase = userDatabase;

		readFromClient = new BufferedReader(
				new InputStreamReader(this.socket.getInputStream()));
		writeToClient = new PrintWriter(this.socket.getOutputStream(), true);

		thread = new Thread(this);
		thread.start();

		estado = EstadoServidor.ESPERA_COMANDOS;
	}

	public void terminar() {
		estado = EstadoServidor.FIM;
	}

	public void run() {
		String rawInput = Messages.getString("LoginThread.0"); //$NON-NLS-1$
		while (estado != EstadoServidor.TERMINADO) {

			switch (estado) {
				case ESPERA_COMANDOS :
					try {
						writeToClient.printf("%s%n",
								Messages.getString("LoginThread.PedirLogin")); //$NON-NLS-1$
						rawInput = readFromClient.readLine();
						estado = EstadoServidor.VALIDARIDENTIDADE;
					} catch (IOException exception) {
						exception.printStackTrace();
						writeToClient.printf("%s%n",
								Messages.getString("LoginThread.MensagemErro")); //$NON-NLS-1$
						estado = EstadoServidor.FIM;
					}
					break;

				case VALIDARIDENTIDADE :
					String[] input = rawInput.split(" ");
					if (input.length == 2) {
						try {
							if (authenticateUser(input[0], input[1])) {
								// TODO Lancar Thread Professor ou aluno
								estado = EstadoServidor.FIM;
							}
						} catch (Exception exception) {
							exception.printStackTrace();
							estado = EstadoServidor.FIM;
						}
					} else {
						writeToClient.printf("%s%n",
								Messages.getString("LoginThread.ErroNoLogin"));
						estado = EstadoServidor.ESPERA_COMANDOS;
					}
					break;

				case FIM :
					try {
						writeToClient.printf(Messages
								.getString("LoginThread.MensagemSaida"));

						readFromClient.close();
						writeToClient.close();
						if (!socket.isClosed()) {
							socket.close();
						}
					} catch (IOException exception) {
						exception.printStackTrace();
					}
					break;
				case TERMINADO :

					break;

				default :
					break;

			}

		}
	}

	private boolean authenticateUser(String inputUser, String inputPass)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		UserInfo user = userDatabase.getUser(inputUser);
		if (user == null) {
			return false;
		} else {
			String salt = user.getUserSalt();
			String calculatedHash = UserInfo.encriptarPassWord(inputPass, salt);
			if (calculatedHash.equals(user.getUserEncryptedPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}
}
