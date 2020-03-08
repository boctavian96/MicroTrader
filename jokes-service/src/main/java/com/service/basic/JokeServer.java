package com.service.basic;

import io.vertx.core.Vertx;

public class JokeServer {
	public static void main(String[] args) {

		int port;
		if (args.length == 0) {
			port = 9000;
		} else {
			port = Integer.valueOf(args[0]);
		}

		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new JokeServiceImpl(port));
	}
}
