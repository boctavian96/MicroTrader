package com.trader.audit;

import com.services.utils.MicroserviceVerticle;

import io.vertx.core.Vertx;

public class AuditServer {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new AuditServiceImpl());
	}
}
