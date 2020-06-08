package TP.TP1.cmd;

import java.util.HashMap;

import TP.TP1.Utilizador;
import TP.TP1.cmd.Flags.AbstractFlag;

public abstract class AbstractCommand {

	protected HashMap<String, AbstractFlag> flags;
	protected HashMap<AbstractFlag, Boolean> enabledFlags;

	public AbstractCommand() {
		initFlags();
		enabledFlags = new HashMap<AbstractFlag, Boolean>(3);
	}

	// private LinkedList<Object> argumentos;

	/**
	 * Verifica se todas os argumentos escritos no comando são validas para o
	 * comando em questão. Iniciar flag com '-'. exemplo '-detail'.
	 * 
	 * Este metodo deve ser chamado antes de executar o comando propriamente
	 * 
	 * @param args
	 *            Argumentos.
	 * 
	 *            Caso seja uma flag iniciar com '-' caso este nao esteja
	 *            presente, assume-se um argumento. Argumentos podem ser de
	 *            todos os tipos e cores, podem ou não ser válidos
	 * 
	 *            O argumento assume que está aplicado á flag anterior. Exemplo
	 *            '-repeat 10', significa repetir 10x o comando
	 * 
	 * 
	 */
	protected boolean verifyFlagsAndArgs(String[] args) {
		for (String string : args) {
			if (string.startsWith("-")) {
				String chave = string.substring(1).toLowerCase();
				if (flags.containsKey(chave)) {
					enabledFlags.put(flags.get(chave), Boolean.TRUE);
				} else {
					return false;
				}
			} // TODO lidar com argumentos
		}
		return true;
	}

	/**
	 * Executa um comando
	 * 
	 * @param user
	 *            Para um dado utilizador, cumpre um comando, tendo em conta os
	 *            argumentos passados
	 * @param args
	 *            Argumentos que influenciam a accao do comando
	 */
	public abstract void execute(Utilizador user, String[] args);
	protected abstract void initFlags();
}
