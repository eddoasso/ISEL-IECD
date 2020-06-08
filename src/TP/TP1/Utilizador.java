package TP.TP1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import TP.TP1.cmd.CommandHandler;

public abstract class Utilizador {
	public static final String serverIP = "localhost";
	public static final int serverPort = 5025;

	private CommandHandler commandHandler;
	private static final String messagemNaoReconhecida = Messages
			.getString("Utilizador.erroComando"); //$NON-NLS-1$

	enum estado {
		INIT, ENVIAR_COMANDOS, LER_COMANDOS;
	}
	private Socket socket;
	private PrintWriter writeToServer;
	private NonblockingBufferedReader readFromServer;

	public Utilizador() throws UnknownHostException, IOException {
		commandHandler = new CommandHandler(this);
		socket = new Socket(serverIP, serverPort);

		writeToServer = new PrintWriter(socket.getOutputStream(), true);
		readFromServer = new NonblockingBufferedReader(new BufferedReader(
				new InputStreamReader(socket.getInputStream())));
	}

	public abstract String quemSouEu();
	public abstract void run();
	public void displayMessageToUser(String msg) {
		// TODO
		System.out.println(
				Messages.getString("Utilizador.mensagemRecebida") + msg); //$NON-NLS-1$
	}

	public void sendMessageToServer(String format, Object... args) {
		writeToServer.printf(format, args);
	}

	/**
	 * Este metodo lê assincronamente mensagens do servidor, logo que estas
	 * estejam disponiveis
	 * 
	 * @return
	 */
	public String readMessageFromServer() {
		try {
			return readFromServer.readLine();
		} catch (IOException e) {
			return "Could not read from server";
		}
	}

	public String readMessageFromConsole() {
		Scanner sc = new Scanner(System.in);
		if (sc.hasNextLine()) {
			String rawCommand = sc.nextLine();

			if (rawCommand.startsWith(
					Messages.getString("Utilizador.inicioComando"))) { //$NON-NLS-1$

				if (!commandHandler.processCommand(rawCommand)) {
					displayMessageToUser(messagemNaoReconhecida);
				}
			} else {
				displayMessageToUser(messagemNaoReconhecida);
			}

			return rawCommand;
		}
		return ""; //$NON-NLS-1$
	}

	public static void main(String[] args) {
		Utilizador u = null;
		try {
			u = new Utilizador() {
				@Override
				public String quemSouEu() {
					return "Classe anonima de Teste"; //$NON-NLS-1$
				}

				@Override
				public void run() {
					while (true) {
						String msg = readMessageFromConsole();

					}
				}
			};
		} catch (IOException exception) {
			System.out.println("Falhou a criar um utilizador");
			exception.printStackTrace();
		}

		u.run();

	}
}

class NonblockingBufferedReader {
	private final BlockingQueue<String> lines = new LinkedBlockingQueue<String>();
	private volatile boolean closed = false;
	private Thread backgroundReaderThread = null;

	public NonblockingBufferedReader(final BufferedReader bufferedReader) {
		backgroundReaderThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (!Thread.interrupted()) {
						String line = bufferedReader.readLine();
						if (line == null) {
							break;
						}
						lines.add(line);
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				} finally {
					closed = true;
				}
			}
		});
		backgroundReaderThread.setDaemon(true);
		backgroundReaderThread.start();
	}

	public String readLine() throws IOException {
		try {
			return closed && lines.isEmpty()
					? null
					: lines.poll(500L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			throw new IOException("The BackgroundReaderThread was interrupted!",
					e);
		}
	}

	public void close() {
		if (backgroundReaderThread != null) {
			backgroundReaderThread.interrupt();
			backgroundReaderThread = null;
		}
	}
}