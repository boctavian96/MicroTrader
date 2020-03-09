package com.trader.crud;

import com.trader.crud.xml.XmlCrud;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class PortfolioCRUDServiceImpl extends AbstractVerticle implements PortfolioCRUDService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioCRUDServiceImpl.class.getName());

	private int port;
	private Router router = Router.router(vertx);

	private Route createUser = router.post("/api/db/create/:userId/:userName/:userMoney");
	private Route deleteUser = router.delete("/api/db/delete/:userId");
	private Route updateUser = router.put("/api/db/update/:userId/:userName/:userMoney");
	private Route getUser = router.get("/api/db/get/:userId");

	public PortfolioCRUDServiceImpl(int port) {
		this.port = port;
	}

	private void createRoutes() {
		createUser.handler(requestHandler -> {
			String userId = requestHandler.get("userId");
			String userName = requestHandler.get("userName");
			String userMoney = requestHandler.get("userMoney");

			createUser(Integer.valueOf(userId), userName, Double.valueOf(userMoney));

			requestHandler.response().end();
		});

		deleteUser.handler(requestHandler -> {
			String userId = requestHandler.get("userId");

			deleteUser(Integer.valueOf(userId));

			requestHandler.response().end();
		});

		updateUser.handler(requestHandler -> {
			String userId = requestHandler.get("userId");
			String userName = requestHandler.get("userName");
			String userMoney = requestHandler.get("userMoney");

			updateUser(Integer.valueOf(userId), userName, Double.valueOf(userMoney));

			requestHandler.response().end();
		});

		getUser.handler(requestHandler -> {
			String userId = requestHandler.get("userId");

			getUser(Integer.valueOf(userId));

			requestHandler.response().end();
		});
	}

	@Override
	public void createUser(int id, String userName, double userMoney) {
		XmlCrud db = XmlCrud.getInstance();
		db.addNewUser(id, userName, userMoney);
	}

	@Override
	public void deleteUser(int id) {
		XmlCrud db = XmlCrud.getInstance();
		String xPath = "/database/clients/client[" + id + "]";
		db.deleteUser(xPath);
	}

	@Override
	public void updateUser(int userId, String userName, double userMoney) {
		XmlCrud db = XmlCrud.getInstance();
		String xPath = "/database/clients/client[" + userId + "]";
		db.updateUser(xPath, userName, userMoney);
	}

	@Override
	public void getUser(int id) {
		XmlCrud db = XmlCrud.getInstance();
		String xPath = "/database/clients/client[" + id + "]";

		db.readDatabase(xPath);
	}

	@Override
	public void start() {
		createRoutes();
		vertx.createHttpServer().requestHandler(router).listen(port);
		LOGGER.info("Service is up at: " + port);
	}

}
