package TP.TP1.cmd.Flags;

import TP.TP1.cmd.AbstractCommand;
import TP.TP1.cmd.Commands.AreYouMineCommand;

public class RepeatFlag extends AbstractFlag {

	@Override
	public void execute(AbstractCommand command) {
		if (command instanceof AreYouMineCommand) {
			AreYouMineCommand c = (AreYouMineCommand) command;
			c.incrementRepeat();

		}
	}

}
