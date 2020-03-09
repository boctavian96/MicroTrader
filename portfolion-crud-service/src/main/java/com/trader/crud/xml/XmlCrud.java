package com.trader.crud.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.services.model.Portfolio;

public class XmlCrud {

	private static final String DATABASE_PATH = "//home/obodnariu/git/MicroTrader/database/clients.xml";
	private static final Logger LOGGER = Logger.getLogger(XmlCrud.class.getName());
	private static XmlCrud INSTANCE = null;

	private XmlCrud() {
		// Singleton class.
	}

	public static XmlCrud getInstance() {
		if (INSTANCE == null) {
			return new XmlCrud();
		} else {
			return INSTANCE;
		}
	}

	public List<Portfolio> readDatabase(String xPath) {
		try {
			File inputFile = new File(DATABASE_PATH);
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputFile);

			List<Node> nodes = document.selectNodes(xPath);
			List<Portfolio> portfolios = new ArrayList<>();

			for (Node node : nodes) {
				int userId = Integer.valueOf(node.valueOf("id"));
				String userName = node.valueOf("name");
				double userMoney = Double.valueOf(node.valueOf("stocks"));

				portfolios.add(new Portfolio(userId, userName, userMoney));

			}
			return portfolios;
		} catch (DocumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e.fillInStackTrace());
		}

		return null;
	}

	public boolean deleteUser(String xPathUserId) {
		try {
			File inputFile = new File(DATABASE_PATH);
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputFile);
			document.selectSingleNode(xPathUserId).detach();
			writeDatabase(document);
			return true;
		} catch (DocumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e.fillInStackTrace());
		}
		return false;
	}

	public boolean updateUser(String xPath, String name, String stocks) {
		try {
			File inputFile = new File(DATABASE_PATH);
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputFile);

			List<Node> nodes = document.selectNodes(xPath);
			List<Portfolio> portfolios = new ArrayList<>();

			for (Node node : nodes) {

				Element element = (Element) node;
				Iterator<Element> nameIterator = element.elementIterator("name");
				Iterator<Element> stocksIterator = element.elementIterator("stocks");

				if (name == null) {
					Element nameElement = (Element) nameIterator.next();
					nameElement.setText(name);
				}

				if (stocks == null) {
					Element stocksElement = (Element) stocksIterator.next();
					stocksElement.setText(stocks);
				}

				writeDatabase(document);
				return true;
			}
		} catch (DocumentException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e.fillInStackTrace());
		}
		return false;
	}

	public boolean addNewUser(int id, String username, double stocks) {
		try {
			File inputFile = new File(DATABASE_PATH);
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputFile);

			Element root = document.getRootElement();

			Element clientRoot = DocumentHelper.createElement("client");

			Element clientId = DocumentHelper.createElement("id");
			clientId.setText("4233");

			Element clientName = DocumentHelper.createElement("name");
			clientName.setText(username);

			Element clientStocks = DocumentHelper.createElement("stocks");
			clientStocks.setText(stocks + "");

			clientRoot.add(clientId);
			clientRoot.add(clientName);
			clientRoot.add(clientStocks);

			root.element("clients").add(clientRoot);
			
			writeDatabase(document);
			return true;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return false;
	}

	private void writeDatabase(Document document) {
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer;
			writer = new XMLWriter(new FileWriter(DATABASE_PATH), format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

}
