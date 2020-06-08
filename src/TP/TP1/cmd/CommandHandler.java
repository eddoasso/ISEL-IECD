package TP.TP1.cmd;

import java.util.HashMap;

import TP.TP1.Utilizador;
import TP.TP1.cmd.Commands.AreYouMineCommand;
import TP.TP1.cmd.Commands.HelpCommand;

public class CommandHandler {
	private HashMap<String, AbstractCommand> commandList;
	private final Utilizador user;
	public CommandHandler(Utilizador user) {
		commandList = new HashMap<String, AbstractCommand>();
		enableCommands();
		this.user = user;
	}

	private void enableCommands() {
		commandList.put("help", new HelpCommand());
		commandList.put("areyoumine", new AreYouMineCommand());
	}

	/**
	 * Este metodo processa os comandos recebidos no servidor
	 * 
	 * @return true caso o comando exista e possa ser executado com sucesso
	 * 
	 */
	public boolean processCommand(String rawCommand) {
		String[] rawArgs = rawCommand.split(" "); //$NON-NLS-1$

		// Primeiro argumento indica o comando a utilzar, o resto são
		// argumentos que alteram o seu comportamento
		String base = rawArgs[0].substring(1).toLowerCase();

		// Copiar os restantes argumentos para o array das flags
		String[] localArgs = new String[rawArgs.length - 1];
		System.arraycopy(rawArgs, 1, localArgs, 0, rawArgs.length - 1);

		AbstractCommand c = commandList.get(base);

		if (c != null) {
			c.execute(user, localArgs);
			return true;
		}

		return false;
	}

}
