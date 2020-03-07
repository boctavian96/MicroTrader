package com.trader.audit;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class AuditServiceImpl extends AbstractVerticle implements AuditService {

	private static final int PORT = 9002;
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class.getName());

	private Router router = Router.router(vertx);

	// Routes
	private Route checkTransaction = router.get("/api/audit/:transaction");

	private void handleRoutes() {
		checkTransaction.handler(requestHandler -> {
			requestHandler.response().end("Alles im Ordnung :)");
		});
	}

	@Override
	public void auditTransaction(Handler<AsyncResult<JsonObject>> resultHandler) {

	}

	@Override
	public void start() {
		handleRoutes();
		vertx.createHttpServer().requestHandler(router).listen(PORT);

	}

}
