package TP.TP1.cmd.Commands;

import java.util.HashMap;

import TP.TP1.Utilizador;
import TP.TP1.cmd.Flags.AbstractFlag;
import TP.TP1.cmd.Flags.NoRepeatFlag;
import TP.TP1.cmd.Flags.RepeatFlag;

public class AreYouMineCommand extends AbstractCommand {
	private int numRepeats = 1;
	private String response = "yes";

	public AreYouMineCommand() {
		super();
	}

	@Override
	public void execute(Utilizador user, String[] args)
			throws IllegalArgumentException {
		if (verifyFlagsAndArgs(args)) {

			// Se existir uma flag ativa, fazer cenas para alterar o restante
			// comportamento
			if (enabledFlags.containsValue(Boolean.TRUE)) {
				for (AbstractFlag flag : enabledFlags.keySet()) {

					if (enabledFlags.get(flag) == Boolean.TRUE) {
						flag.execute(this);
						enabledFlags.put(flag, Boolean.FALSE);
					}
				}
			}

			for (int i = 0; i < numRepeats; i++) {
				user.displayMessageToUser(response);
			}
		} else {
			throw new IllegalArgumentException("argumentos nao reconhecidos");
		}
	}

	@Override
	protected void initFlags() {
		flags = new HashMap<String, AbstractFlag>();
		flags.put("repeat", new RepeatFlag());
		flags.put("no-repeat", new NoRepeatFlag());
	}

	public void incrementRepeat() {
		numRepeats++;
	}
	public void decrementRepeat() {
		numRepeats--;
	}
}
