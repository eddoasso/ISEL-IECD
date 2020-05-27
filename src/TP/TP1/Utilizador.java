package TP.TP1;

import java.util.Scanner;

import TP.TP1.cmd.Commands.CommandHandler;

public abstract class Utilizador {
	private CommandHandler commandHandler;
	private static String messagemNaoReconhecida = "Comando nao reconhecido, escrever !help para ajuda";

	public Utilizador() {
		commandHandler = new CommandHandler(this);
	}

	public abstract String quemSouEu();
	public void displayMessageToUser(String msg) {
		// TODO
		System.out.println("mensagem recebida do servidor\n" + msg);
	}
	public String readMessageFromConsole() {
		Scanner sc = new Scanner(System.in);
		System.out.println("input comando ");

		if (sc.hasNextLine()) {
			String rawCommand = sc.nextLine();

			if (rawCommand.startsWith("!")) {
				String[] rawArgs = rawCommand.split(" ");

				// Primeiro argumento indica o comando a utilzar, o resto são
				// argumentos que alteram o seu comportamento
				String comando = rawArgs[0].substring(1).toLowerCase();

				// Copiar os restantes argumentos para o array das flags
				String[] localArgs = new String[rawArgs.length - 1];
				System.arraycopy(rawArgs, 1, localArgs, 0, rawArgs.length - 1);
				if (!commandHandler.processCommand(comando, localArgs)) {
					displayMessageToUser(messagemNaoReconhecida);
				}
			} else {
				displayMessageToUser(messagemNaoReconhecida);
			}

			return rawCommand;
		}
		return "";
	}

	public static void main(String[] args) {
		Utilizador u = new Utilizador() {
			@Override
			public String quemSouEu() {
				return "Classe anonima de Teste";
			}
		};
		while (true) {
			String msg = u.readMessageFromConsole();

		}
	}
}
