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

	public void traverse(Node node, String prefix) {
		System.out.println("node: " + node.getNodeName());
		if (node.hasChildNodes()) {
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				traverse(child, prefix + "\t");
			}
		}
	}

	public void traverse() {
		traverse(xmlFile.getDocumentElement(), "\t");
	}

	public String classicText() {
		String text = "";

		NodeList nodes = xmlFile.getElementsByTagName("estrofe");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node n = nodes.item(i);
			text += n.getTextContent();
		}

		return text;
	}

	/*
	 * public void Test() { Node verso =
	 * xmlFile.getElementsByTagName("verso").item(0);
	 * System.out.println(verso.ATTRIBUTE_NODE); }
	 */

	public void getNumVersos() {

		NodeList estrofes = xmlFile.getElementsByTagName("estrofe");

		for (int i = 0; i < estrofes.getLength(); i++) {
			Node estrofe = estrofes.item(i);
			int count = 0;
			NodeList versos = estrofe.getChildNodes();
			for (int j = 0; j < versos.getLength(); j++) {
				Node n = versos.item(j);
				if (n.getNodeType() == Node.ELEMENT_NODE)
					count++;
			}
			;
			System.out.println("A estrofe " + i + " tem " + count + " versos");
		}
	}

	public void AddVersoToEstrofe(int numEstrofe, String verso) {
		NodeList estrofes = xmlFile.getElementsByTagName("estrofe");
		Node n = estrofes.item(numEstrofe);

		Element e = xmlFile.createElement("verso");
		e.appendChild(xmlFile.createTextNode(verso));
		n.appendChild(e);
	}

	public void RemoveEstrofe(int numEstrofe) {
		Node estrofe = xmlFile.getElementsByTagName("estrofe").item(numEstrofe);
		estrofe.getParentNode().removeChild(estrofe);
	}

	public void FindPalavrasInVersos(String s) {
		NodeList versos = xmlFile.getElementsByTagName("verso");
		for (int i = 0; i < versos.getLength(); i++) {
			Node verso = versos.item(i);

			if (verso.getTextContent().contains(s)) {
				int num = i + 1;
				System.out.println("A palavra " + s + " está no verso " + (int) (i + 1));
			}

		}
	}

	@Override
	public String toString() {
		return "poema XML " + xmlFile.getElementsByTagName("titulo").item(0).getTextContent();
	}

}
