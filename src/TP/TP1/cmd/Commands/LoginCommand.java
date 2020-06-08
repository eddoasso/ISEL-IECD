package TP.TP1.cmd.Commands;

import java.util.HashMap;

import TP.TP1.Utilizador;
import TP.TP1.cmd.AbstractCommand;
import TP.TP1.cmd.Flags.AbstractFlag;

public class LoginCommand extends AbstractCommand {
	private String userName;
	private String attempedPass;

	@Override
	public void execute(Utilizador user, String[] args) {
		if (verifyFlagsAndArgs(args)) {
			if (enabledFlags.containsValue(Boolean.TRUE)) {
				// Nao deve existir flags para este comando,
				// nao deve entrar aqui
			}
			
		} else {
			throw new IllegalArgumentException("argumentos nao reconhecidos");
		}

	}

	@Override
	protected void initFlags() {
		flags = new HashMap<String, AbstractFlag>();

	}

}
