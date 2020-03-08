package com.trader.crud.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.services.model.Portfolio;

public class XmlCrud {

	private static final String DATABASE_PATH = "//home/obodnariu/git/MicroTrader/database/clients.xml";
	private static final Logger LOGGER = Logger.getLogger(XmlCrud.class.getName());
	private static XmlCrud INSTANCE = null;

	private XmlCrud() {

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

			System.out.println("Root element :" + document.getRootElement().getName());

			List<Node> nodes = document.selectNodes("/clientsDatabase/client");
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

	public void deleteUser(String xPath) {

	}

	public void updateUser(String xPath) {

	}

}
