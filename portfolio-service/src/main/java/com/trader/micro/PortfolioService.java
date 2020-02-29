package com.trader.micro;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@VertxGen
@ProxyGen
public interface PortfolioService {

	String ADDRESS = "service.portofolio";
	
	String EVENT_ADDRESS = "protofolio";
	
	void getPortofolio(Handler<AsyncResult<Portfolio>> resultHandler);
	
	void buy(int amount, JsonObject quote, Handler<AsyncResult<Portfolio>> resultHandler);
	
	void sell(int amount, JsonObject quote, Handler<AsyncResult<Portfolio>> resultHandler);
	
	void evaluate(Handler<AsyncResult<Double>> resultHandler);
}
