package TP.TP1.Commands;

import java.util.ArrayList;
import java.util.Arrays;

import TP.TP1.Utilizador;

public class HelpCommand implements AbstractCommand {

	@Override
	public boolean verifyCorrectness(String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Utilizador user, String[] args) {
		ArrayList<String> argsList = new ArrayList<>(Arrays.asList(args));
		user.displayMessageToUser("Pediste Ajuda");
	}

	@Override
	public void initFlags() {
		// TODO Auto-generated method stub

	}

}
