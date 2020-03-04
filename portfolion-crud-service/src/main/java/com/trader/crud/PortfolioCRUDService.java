package com.trader.crud;

import com.trader.portofolio.Portfolio;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public interface PortfolioCRUDService {

	public void createUser(String id, Handler<AsyncResult<JsonObject>> resultHandler);
	
	public void deleteUser(String id, Handler<AsyncResult<JsonObject>> resultHandler);
	
	public void updateUser(Portfolio user,Handler<AsyncResult<JsonObject>> resultHandler);
	
	public void getUser(Handler<AsyncResult<JsonObject>> resultHandler);
	
}
