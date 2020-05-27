package TP.TP1.Commands;

import TP.TP1.Utilizador;

public interface AbstractCommand {

	/**
	 * Verifica se todas os argumentos escritos no comando são validas para o
	 * comando em questão
	 * 
	 */
	boolean verifyCorrectness(String[] args);

	/**
	 * Executa um comando
	 * 
	 * @param user
	 *            Para um dado utilizador, cumpre um comando, tendo em conta os
	 *            argumentos passados
	 * @param args
	 *            Argumentos que influenciam a accao do comando
	 */
	public void execute(Utilizador user, String[] args);
	public void initFlags();
}
