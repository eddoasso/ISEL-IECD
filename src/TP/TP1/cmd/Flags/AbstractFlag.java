package TP.TP1.cmd.Flags;

import java.util.HashMap;

import TP.TP1.cmd.AbstractCommand;

public abstract class AbstractFlag {
	/**
	 * Uma flag altera o comportamento de um comando. Assim executa o comando,
	 * tendo em conta as suas necessidades.
	 * 
	 * @param command
	 *            Comando cujo comportamento vai ser alterado
	 */
	public abstract void execute(AbstractCommand command);

}
