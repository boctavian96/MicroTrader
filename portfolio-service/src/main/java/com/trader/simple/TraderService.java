package com.trader.simple;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface TraderService {
	void buyStocks(double amount, Handler<AsyncResult<Void>> resultHandler);

	void sellStocks(double amount, Handler<AsyncResult<Void>> resultHandler);
}
