package TP1;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

class Teste {
	public static void main(String[] args) {

		PoemaXML poema;

		try {

			System.out.println("oi");
			poema = new PoemaXML("poema.xml");
			System.out.println("poema " + poema.toString());
			System.out.println("Texto\n" + poema.getAllTexto());

			System.out.println("Classificacao estrofes");
			poema.getClassificacaoEstrofes();

			System.out.println("Novo Poema");
			poema.acrescentaVerso(1, "É O FIM DO MUNDO");
			System.out.println(poema.getAllTexto());

			System.out.println("Poema Original");
			poema.getClassificacaoEstrofes();
			poema.removerVerso(1, 3);
			poema.getClassificacaoEstrofes();
			System.out.println(poema.getAllTexto());

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}
