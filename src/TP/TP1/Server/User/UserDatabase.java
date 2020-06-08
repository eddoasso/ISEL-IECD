package TP.TP1.Server.User;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UserDatabase {
	private static final String filePath = "src\\TP\\TP1\\Server\\Files\\USER_DATABASE.xml";
	private static final String XSDPath = "src\\TP\\TP1\\Server\\Files\\user_database.XSD";

	private File file;
	private NodeList utilizadores;

	/**
	 * Lê e escreve no ficheiro XML correspondente
	 * 
	 * @throws Exception
	 */
	public UserDatabase() throws Exception {

		file = new File(filePath);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();
		utilizadores = doc.getElementsByTagName("utilizador");

		if (!validateXMLSchema(XSDPath, filePath)) {
			throw new Exception(
					"ERRO NO FICHEIRO USER_DATABASE. Nao validou com XSD");
		}
	}

	public UserInfo getUser(String inputUser) {
		for (int i = 0; i < utilizadores.getLength(); i++) {
			Node userElem = utilizadores.item(i);
			if (userElem.getNodeType() == Node.ELEMENT_NODE) {
				NodeList la = userElem.getChildNodes();

				for (int j = 0; j < la.getLength(); j++) {
					Node item = la.item(j);
					if (item.getNodeType() == Node.ELEMENT_NODE) {
						if (item.getNodeName().equals("username")) {
							String elemName = item.getTextContent().strip();

							if (elemName.equals(inputUser)) {
								// get password hash
								Node passNode = getNextSibling(item);
								// get user salt
								Node saltNode = getNextSibling(passNode);

								String hash = passNode.getTextContent().strip();
								String salt = saltNode.getTextContent().strip();
								return new UserInfo(elemName, hash, salt);
							}
						}
					}
				}
			}
		}
		return null;
	}

	private Node getNextSibling(Node current) {
		Node sibling = current.getNextSibling();
		while (sibling != null) {
			if (sibling.getNodeType() == Node.ELEMENT_NODE) {
				return sibling;
			}
			sibling = sibling.getNextSibling();
		}
		return null;

	}
	public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
		try {
			SchemaFactory factory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdPath));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
		} catch (IOException | SAXException e) {
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
		return true;
	}
	// Para testar esta classe, descomentar
	public static void main(String[] args) {
		try {
			UserDatabase database = new UserDatabase();
			System.out.println(database.getUser("porfirio"));
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
}
