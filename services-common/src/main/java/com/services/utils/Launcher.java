package com.services.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Launcher extends io.vertx.core.Launcher {

	private static final String CONFIG_PATH = "conf/config.json";
	static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class.getName());
	private static final String HOST = "127.0.0.1";

	public static void main(String[] args) {
		new Launcher().dispatch(args);
	}

	@Override
	public void beforeStartingVertx(VertxOptions options) {
		options.setClustered(true).setClusterHost(HOST);
	}

	@Override
	public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
		super.beforeDeployingVerticle(deploymentOptions);

		try {
			readConfig(deploymentOptions);
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
	}

	private void defaultOptions(DeploymentOptions options) {
		if (options.getConfig() != null) {
			LOGGER.info("No available options!");
			return;
		}

		options.setConfig(new JsonObject());
	}

	private void readConfig(DeploymentOptions options) throws FileNotFoundException {
		File configFile = new File(CONFIG_PATH);
		JsonObject config = getConfig(configFile);
		options.getConfig().mergeIn(config);
	}

	private JsonObject getConfig(File config) throws FileNotFoundException {
		if (!config.isFile()) {
			LOGGER.info("Config is not a file!");
			return new JsonObject();
		}

		Scanner scanner = new Scanner(config).useDelimiter("\\A");
		String confStr = scanner.next();
		// scanner.close();

		return new JsonObject(confStr);
	}
}
