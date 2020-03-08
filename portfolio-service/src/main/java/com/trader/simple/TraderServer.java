package com.trader.simple;

import io.vertx.core.Vertx;

public class TraderServer {

	public static void main(String[] args) {
		int port;

		if (args.length == 0) {
			port = 9001;
		} else {
			port = Integer.valueOf(args[0]);
		}

		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new TraderServiceImpl());
	}
}
