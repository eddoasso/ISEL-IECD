package TP1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PoemaXML {
	private Document xmlFile;

	public PoemaXML(Document xmlFile) {
		this.xmlFile = xmlFile;
	}

	public PoemaXML(String pathToFile) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(pathToFile);
		this.xmlFile = document;

		xmlFile.getDocumentElement().normalize();

	}

	public String getAllTexto() {
		String text = "";

		NodeList nodes = xmlFile.getElementsByTagName("verso");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			n.normalize();
			text += n.getTextContent() + "\n";
		}

		return text;
	}

	public void getClassificacaoEstrofes() {
		NodeList nodes = xmlFile.getElementsByTagName("estrofe");
		for (int j = 0; j < nodes.getLength(); j++) {
			Node n = nodes.item(j);
			n.normalize();
			int k = 0;
			NodeList children = n.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE)
					k++;
			}
			System.out.println("Estrofe " + j + " tem " + k + " versos.");
		}
	}

	public void acrescentaVerso(int numEstrofe, String s) {
		NodeList nodes = xmlFile.getElementsByTagName("estrofe");
		Node n = nodes.item(numEstrofe);

		Element e = xmlFile.createElement("verso");
		e.appendChild(xmlFile.createTextNode(s));
		n.appendChild(e);
	}

	public void removerVerso(int numEstrofe, int numVerso) {
		Node n = xmlFile.getElementsByTagName("estrofe").item(numEstrofe);
		n.normalize();

		Node v = n.getChildNodes().item(numVerso);
		v.getParentNode().removeChild(v);

	}

	@Override
	public String toString() {
		return "poema XML " + xmlFile.getElementsByTagName("titulo").item(0).getTextContent();
	}

}
