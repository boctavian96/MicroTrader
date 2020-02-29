package com.trader.utils;

import java.util.Set;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.EventBusService;

public class MicroServiceVerticle extends AbstractVerticle {

	protected ServiceDiscovery discovery;
	protected Set<Record> registeredRecords = new ConcurrentHashSet();

	public void publishEventBusService(String name, String address, Class<?> serviceClass,
			Handler<AsyncResult<Void>> completionHandler) {
		Record record = EventBusService.createRecord(name, address, serviceClass);
		publish(record, completionHandler);
	}

	protected void publish(Record record, Handler<AsyncResult<Void>> completionHandler) {
		if (discovery == null) {
			try {
				start();
			} catch (Exception e) {
				throw new RuntimeException("Cannot create discovery service");
			}
		}
	}
}
