package com.service.jokes;

import com.trader.portofolio.PortfolioService;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class ServerVerticle extends AbstractVerticle {

	static final Logger LOGGER = LoggerFactory.getLogger(ServerVerticle.class.getName());
	private final Joke joke = new Joke("What kind of dog lives in a particle accelerator? A Fermilabrador Retriever.");
	private PortfolioService portfolioService;

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

		// GET and POST METHODS

		switch (request.method()) {
		case GET:
			userGet(response);
			break;
		case POST:
			userCreate(request, response);
			break;
		default:
			String json = Json.encode(joke);
			response.end(json);
		}
		/*
		 * String json = Json.encode(joke); response.end(json);
		 */
	}

	/*
	 * Only for testing
	 */
	private void userGet(HttpServerResponse response) {
		portfolioService.getUser(ar -> userGetResponse(ar, response));
	}

	/*
	 * Only for testing
	 */
	private void userGetResponse(AsyncResult<JsonObject> ar, HttpServerResponse response) {
		if (ar.succeeded()) {
			JsonObject json = ar.result();

			response.setStatusCode(200) //
					.end(json.toString());
		} else {
			// ERROR.
			response.setStatusCode(404).end();
		}
	}

	private void userCreate(HttpServerRequest request, HttpServerResponse response) {
		String userId = request.params().get("userId");
		portfolioService.createUser(userId, ar -> userCreateResponse(ar, response));
	}

	private void userCreateResponse(AsyncResult<JsonObject> ar, HttpServerResponse response) {
		if (ar.succeeded()) {
			JsonObject json = ar.result();

			response.setStatusCode(201).end(json.toString());
		} else {
			response.setStatusCode(422).end();
		}
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

		portfolioService = new ServiceProxyBuilder(vertx) //
				.setAddress(PortfolioService.PORTFOLIO_SERVICE_ADDRESS) //
				.build(PortfolioService.class);
	}

}
