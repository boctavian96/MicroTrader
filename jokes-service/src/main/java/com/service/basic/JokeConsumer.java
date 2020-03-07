package com.service.basic;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class JokeConsumer extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(JokeConsumer.class.getName());

	private HttpRequest<String> request;

	@Override
	public void start() {
		request = WebClient.create(vertx).get(9000, "localhost", "/api/message").putHeader("Accept", "application/json")
				.as(BodyCodec.string());

		//vertx.setPeriodic(3000, id -> fetchJoke());
		fetchJoke();
		
	}

	private void fetchJoke() {
		request.send(handler -> {
			if (handler.succeeded()) {
				System.out.println(handler.result().body());
			}

		});
	}

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new JokeConsumer());
		LOGGER.info("Joke consumer is up.");
	}
}
