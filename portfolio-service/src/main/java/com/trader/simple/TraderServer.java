package com.trader.simple;

import io.vertx.core.Vertx;

public class TraderServer{

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new TraderServiceImpl());
	}
}
