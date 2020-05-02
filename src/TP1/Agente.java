package TP1;

import java.net.Socket;

public interface Agente {

	public boolean conectar(Socket s);
	public boolean conectar(int port, String ip);
	public boolean lancarAplicacao();
	public boolean escreverSocket();
}
