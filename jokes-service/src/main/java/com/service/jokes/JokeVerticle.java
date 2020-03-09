package com.service.jokes;

import com.services.utils.MicroserviceVerticle;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

public class JokeVerticle extends MicroserviceVerticle {

	private void deployServer(JsonObject config) {
		String className = ServerVerticle.class.getName();
		DeploymentOptions options = new DeploymentOptions().setConfig(config);

		vertx.deployVerticle(className, options);
	}

	private void publishEndpoint(JsonObject config) throws Exception {
		Config configuration = new Config(config);
		String host = configuration.getHttpOptions().getHost();
		int port = configuration.getHttpOptions().getPort();

		publishHttpEndpoint("jokes", host, port, this::publishHandler);
	}

	private void publishHandler(AsyncResult<Void> ar) {
		if (ar.failed()) {
			ar.cause().printStackTrace();
		} else {
			System.out.println("REST: " + ar.succeeded());
		}
	}

	@Override
	public void start() throws Exception {
		super.start();

		JsonObject configObj = config();
		deployServer(configObj);
		publishEndpoint(configObj);
	}

	@Override
	protected void createRoutes() {
		// TODO Auto-generated method stub

	}
}
