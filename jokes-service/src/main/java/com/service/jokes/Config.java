package com.service.jokes;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.json.JsonObject;

public class Config {

	private static final String HTTP = "http";
	private static final String HOST = "host";
	private static final String PORT = "port";
	private static final int PORT_DEFAULT = 8080;

	private final HttpServerOptions httpOptions;

	public Config(JsonObject config) {
		JsonObject httpConfig = config.getJsonObject(HTTP);
		String host = httpConfig.getString(HOST);
		int port = httpConfig.getInteger(PORT, PORT_DEFAULT);

		List<HttpVersion> alpns = new ArrayList();
		alpns.add(HttpVersion.HTTP_1_1);
		alpns.add(HttpVersion.HTTP_2);

		httpOptions = new HttpServerOptions();
		httpOptions.setAlpnVersions(alpns);
		httpOptions.setHost(host);
		httpOptions.setPort(port);
	}

	HttpServerOptions getHttpOptions() {
		return httpOptions;
	}
}
