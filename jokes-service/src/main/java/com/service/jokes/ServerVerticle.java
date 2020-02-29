package com.service.jokes;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ServerVerticle extends AbstractVerticle {

	static final Logger LOGGER = LoggerFactory.getLogger(ServerVerticle.class.getName());
	private final Joke joke = new Joke("Ana are mere");

	private void createServer() {
		Config config = new Config(config());
		HttpServerOptions options = config.getHttpOptions();
		int port = config.getHttpOptions().getPort();

		vertx.createHttpServer(options) //
				.requestHandler(this::handleRequest) //
				.listen(port, this::handleListener);

	}

	private void handleRequest(HttpServerRequest request) {
		HttpServerResponse response = request.response() //
				.putHeader("content-type", "application/json");

		String json = Json.encode(joke);
		response.end(json);
	}

	private void handleListener(AsyncResult<HttpServer> ar) {
		if (ar.succeeded()) {
			LOGGER.info("Server started");
		} else {
			LOGGER.error("Cannot start server: " + ar.cause());
		}
	}

	@Override
	public void start() throws Exception {
		super.start();

		createServer();
	}

}
