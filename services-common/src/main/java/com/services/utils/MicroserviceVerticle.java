package com.services.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.servicediscovery.types.HttpEndpoint;

public abstract class MicroserviceVerticle extends AbstractVerticle {

	final static Logger LOGGER = LoggerFactory.getLogger(MicroserviceVerticle.class.getName());

	private ServiceDiscovery discovery;
	private Set<Record> records = new ConcurrentHashSet<>();

	private void createServiceDiscovery() {
		JsonObject config = config();
		ServiceDiscoveryOptions options = new ServiceDiscoveryOptions().setBackendConfiguration(config);
		discovery = ServiceDiscovery.create(vertx, options);
		LOGGER.info("Created the service discovery");
	}

	private void publish(Record record, Handler<AsyncResult<Void>> completion) throws Exception {
		if (discovery == null) {
			start();
		}

		discovery.publish(record, ar -> {
			if (ar.succeeded()) {
				records.add(record);
			}
			completion.handle(ar.map((Void) null));
		});
	}

	private Future<Void> unpublish(Record record) {
		Future<Void> unregisteringFuture = Future.future();
		discovery.unpublish(record.getRegistration(), unregisteringFuture);

		return unregisteringFuture;
	}

	private void stopDiscovery(Future<Void> stopFuture) {
		discovery.close();
		stopFuture.complete();
		LOGGER.info("Stoped the discovery service");
	}

	protected void publishHttpEndpoint(String endpoint, String host, int port, Handler<AsyncResult<Void>> completion)
			throws Exception {
		Record record = HttpEndpoint.createRecord(endpoint, host, port, "/");
		publish(record, completion);
	}

	protected void publishEventBusService(String name, String address, Class<?> serviceClass,
			Handler<AsyncResult<Void>> completionHolder) throws Exception {
		Record record = EventBusService.createRecord(name, address, serviceClass);
		publish(record, completionHolder);
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		super.stop(stopFuture);

		List<Future> futures = records.stream().map(this::unpublish).collect(Collectors.toList());

		if (futures.isEmpty()) {
			stopDiscovery(stopFuture);
		} else {
			// stopServices(futures, stopFuture);
		}
	}

	protected void setDebuger() {
		
	}
	
	protected abstract void createRoutes();

	@Override
	public void start() throws Exception {
		super.start();

		createServiceDiscovery();
	}

}
