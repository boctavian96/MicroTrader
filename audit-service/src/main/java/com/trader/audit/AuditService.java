package com.trader.audit;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public interface AuditService {

	public void auditTransaction(Handler<AsyncResult<JsonObject>> resultHandler);
}
