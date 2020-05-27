package TP.TP1.Commands;

import java.util.HashMap;

import TP.TP1.Utilizador;

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
	 * @return true caso o comando exista
	 */
	public boolean processCommand(String base, String[] flags) {
		AbstractCommand c = commandList.get(base);

		if (c != null) {
			c.execute(user, flags);
			return true;
		}

		return false;
	}

}
