package com.service.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	private List<String> jokes = new ArrayList<>();
	private String[] rawJokes = {

			"What lies at the bottom of the ocean and twitches? A nervous wreck.",
			"Just read a few facts about frogs. They were ribbiting.",
			"Did you hear the news? FedEx and UPS are merging. They’re going to go by the name Fed-Up from now on.",
			"I'll tell you what often gets over looked... garden fences.",
			"Someone broke into my house last night and stole my limbo trophy. How low can you go?",
			"I applied to be a doorman but didn't get the job due to lack of experience. That surprised me, I thought it was an entry level position.",
			"Why did Mozart kill all his chickens?\n"
					+ "Because when he asked them who the best composer was, they'd all say \"Bach bach bach!\" ",
			"What do you get when you cross a snowman with a vampire? Frostbite." };

	private Router router = Router.router(vertx);

	// Routes
	private Route docRoute = router.get("/");
	private Route messageRoute = router.get("/api/message");
	private Route helloRoute = router.get("/api/hello");
	private Route getJokeId = router.get("/api/message/:jokeId");
	private Route addAJokeRoute = router.put("/api/:jokeId/:someJoke");

	public JokeServiceImpl() {
		jokes = Arrays.asList(rawJokes);
	}

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

		getJokeId.handler(requestHandler -> {
			int jokeId = Integer.valueOf(requestHandler.request().getParam("jokeId"));

			if (jokeId < 1 || jokeId - 1 > rawJokes.length) {
				LOGGER.error("No id for this joke...");
				requestHandler.response().end("Joke doesnt exist");
			} else {
				requestHandler.response().end(Json.encode(getJoke(jokeId)));
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
		int jokeIndex = random.nextInt(rawJokes.length - 1);

		return rawJokes[jokeIndex];
	}

	public String getJoke(int id) {
		return rawJokes[id - 1];
	}

	@Override
	public void start() {
		createRoutes();
		LOGGER.info("Joke service is up at: " + PORT);
		vertx.createHttpServer().requestHandler(router).listen(PORT);
	}
}
