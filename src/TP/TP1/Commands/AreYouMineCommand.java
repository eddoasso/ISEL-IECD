package TP.TP1.Commands;

import java.util.HashMap;

import TP.TP1.Utilizador;

public class AreYouMineCommand implements AbstractCommand {
	private HashMap<String, AbstractFlags> flags;

	public AreYouMineCommand() {
		initFlags();
	}

	@Override
	public boolean verifyCorrectness(String[] args) {
		for (String string : args) {
			// é uma flag
			if (string.startsWith("-")) {
				String chave = string.substring(1).toLowerCase();
				if (flags.containsKey(chave)) {
					flags.get(chave).execute();
				}
			}
		}
		return true;
	}

	@Override
	public void execute(Utilizador user, String[] args) {
		if (verifyCorrectness(args)) {
			user.displayMessageToUser(
					"I'm yours is: " + flags.get("toogle").getFlagStatus());
		}
	}

	@Override
	public void initFlags() {
		flags = new HashMap<String, AbstractFlags>();
		flags.put("toogle", new AbstractFlags() {
			boolean on = true;
			@Override
			public boolean getFlagStatus() {
				return this.on;
			}

			@Override
			public void execute() {
				on = !on;
			}
		});

	}

}
