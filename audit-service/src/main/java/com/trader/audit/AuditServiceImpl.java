package com.trader.audit;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class AuditServiceImpl extends AbstractVerticle implements AuditService {

	@Override
	public void auditTransaction(Handler<AsyncResult<JsonObject>> resultHandler) {
		// TODO Auto-generated method stub

	}

}
