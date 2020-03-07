package com.service.basic;

import java.util.Random;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class JokeServiceImpl extends AbstractVerticle implements JokeService {

	public static final int PORT = 9000;

	private static final Logger LOGGER = LoggerFactory.getLogger(JokeServiceImpl.class.getName());

	String[] jokes = {

			"What lies at the bottom of the ocean and twitches? A nervous wreck.",
			"Just read a few facts about frogs. They were ribbiting.",
			"Did you hear the news? FedEx and UPS are merging. They’re going to go by the name Fed-Up from now on.",
			"I'll tell you what often gets over looked... garden fences.",
			"Someone broke into my house last night and stole my limbo trophy. How low can you go?",
			"I applied to be a doorman but didn't get the job due to lack of experience. That surprised me, I thought it was an entry level position.",
			"Why did Mozart kill all his chickens?\n"
					+ "Because when he asked them who the best composer was, they'd all say \"Bach bach bach!\" ",
			"What do you get when you cross a snowman with a vampire? Frostbite." };

	Router router = Router.router(vertx);

	// Routes
	Route docRoute = router.get("/");
	Route messageRoute = router.get("/api/message");
	Route helloRoute = router.get("/api/hello");
	Route addAJokeRoute = router.put("/api/:jokeId/:someJoke");

	private void createRoutes() {
		docRoute.handler(requestHandler -> {
			requestHandler.response().end("Here should be some documentation...");
		});

		messageRoute.handler(requestHandler -> {
			requestHandler.response().end(Json.encode(getJoke()));
		});

		helloRoute.handler(requestHandler -> {
			requestHandler.response().end("Hello I am vertx");
		});

		addAJokeRoute.handler(requestHandler -> {
			String jokeId = requestHandler.request().getParam("jokeId");
			String joke = requestHandler.request().getParam("someJoke");
			HttpServerResponse response = requestHandler.response();

			if (joke == null || jokeId == null) {
				LOGGER.error("Invalid joke :(");
				sendError(400, response);
			} else {
				
			}

		});

		router.get().handler(StaticHandler.create());
	}

	private void sendError(int statusCode, HttpServerResponse response) {
		response.setStatusCode(statusCode).end();
	}

	@Override
	public String getJoke() {
		Random random = new Random();
		int jokeIndex = random.nextInt(jokes.length - 1);

		return jokes[jokeIndex];
	}

	@Override
	public void start() {
		createRoutes();
		LOGGER.info("Joke service is up at: " + PORT);
		vertx.createHttpServer().requestHandler(router).listen(PORT);
	}
}
