package TP1;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

class Teste {
	public static void main(String[] args) {

		PoemaXML poema;

		try {

			poema = new PoemaXML("poema.xml");

			System.out.println("Traverse");
			poema.traverse();

			System.out.println(poema.toString());
			poema.classicText();
			/**
			 * System.out.println("Classificacao estrofes");
			 * poema.getClassificacaoEstrofes();
			 * 
			 * System.out.println("Novo Poema"); poema.acrescentaVerso(1, "É O FIM DO
			 * MUNDO"); poema.classicText();
			 * 
			 * System.out.println("Poema Original"); poema.getClassificacaoEstrofes();
			 * poema.removerVerso(1, 3); poema.getClassificacaoEstrofes();
			 * poema.classicText();
			 **/

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}
