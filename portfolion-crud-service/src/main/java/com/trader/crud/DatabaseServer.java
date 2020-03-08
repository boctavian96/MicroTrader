package com.trader.crud;

import io.vertx.core.Vertx;

public class DatabaseServer {
	public static void main(String[] args) {
		int port = 9999;
		if(args.length > 0) {
			port = Integer.valueOf(args[0]);
		}
		
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new PortfolioCRUDServiceImpl(port));
	}
}
