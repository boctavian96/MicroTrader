package com.trader.simple;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class TraderServiceImpl extends AbstractVerticle implements TraderService {

	private static final int PORT = 9001;
	private static final Logger LOGGER = LoggerFactory.getLogger(TraderServiceImpl.class.getName());

	Router router = Router.router(vertx);

	// Routes
	Route docRoute = router.get("/");
	Route clientsRoute = router.get("/api/clients");
	Route clientRoute = router.get("/api/:clientId");
	Route addClient = router.put("/api/newClient/:clientId/:clientName/:clientStocks");

	private void createRoutes() {
		docRoute.handler(requestHandler -> {
			requestHandler.response().end("Here should be some docs...");
		});
	}

	@Override
	public void buyStocks(double amount, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sellStocks(double amount, Handler<AsyncResult<Void>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		createRoutes();
		vertx.createHttpServer().requestHandler(router).listen(PORT);
		LOGGER.info("Service is up at: " + PORT);
	}

}
