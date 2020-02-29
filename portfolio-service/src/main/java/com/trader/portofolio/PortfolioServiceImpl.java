package com.trader.portofolio;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class PortfolioServiceImpl implements PortfolioService {

	@Override
	public void buyStock(Handler<AsyncResult<JsonObject>> resultHandler) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sellStock(Handler<AsyncResult<JsonObject>> resultHandler) {
		// TODO Auto-generated method stub
	}

	@Override
	public void getUser(Handler<AsyncResult<JsonObject>> resultHandler) {
		JsonObject response = new JsonObject();

		response.put("name", "John");
		response.put("money", 100000.00);

		resultHandler.handle(Future.succeededFuture(response));
	}

	@Override
	public void createUser(Handler<AsyncResult<JsonObject>> resultHandler) {
		JsonObject response = new JsonObject() //
				.put("id", "qwery-dvorak-salam");
		resultHandler.handle(Future.succeededFuture(response));
	}

}
