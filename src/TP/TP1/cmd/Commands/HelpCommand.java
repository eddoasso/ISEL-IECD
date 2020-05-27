package TP.TP1.cmd.Commands;

import TP.TP1.Utilizador;

public class HelpCommand extends AbstractCommand {
	private String format = "%-20s - %s%n";
	private String msgAjuda = String.format(format, "help",
			"lista os comandos");
	private String msgDebug = String.format(format, "areyoumine",
			"funcao debug");
	private String msg = String.format("Comandos disponiveis:%n%n%s%s",
			msgAjuda, msgDebug);

	@Override
	public void execute(Utilizador user, String[] args) {
		user.displayMessageToUser(msg);
	}

	@Override
	protected void initFlags() {
		// TODO Auto-generated method stub
	}

}
