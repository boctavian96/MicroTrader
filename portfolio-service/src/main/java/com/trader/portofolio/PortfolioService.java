package com.trader.portofolio;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public interface PortfolioService {

	String PORTFOLIO_SERVICE_NAME = "portfolio.client.name";
	String PORTFOLIO_SERVICE_ADDRESS = "portfolio.client.address";

	void buyStock(Handler<AsyncResult<JsonObject>> resultHandler);

	void sellStock(Handler<AsyncResult<JsonObject>> resultHandler);

	// TODO: MOVE GET AND CREATE TO ANOTHER SERVICE.

	void getUser(Handler<AsyncResult<JsonObject>> resultHandler);

	void createUser(Handler<AsyncResult<JsonObject>> resultHandler);
}
