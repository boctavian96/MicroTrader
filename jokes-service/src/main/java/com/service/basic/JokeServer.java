package com.service.basic;

import io.vertx.core.Vertx;

public class JokeServer {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new JokeServiceImpl());
		//vertx.deployVerticle(new JokeConsumer());
	}
}
