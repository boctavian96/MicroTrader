package com.trader.portofolio;

import com.services.utils.MicroserviceVerticle;

import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ServiceBinder;

public class PortfolioVerticle extends MicroserviceVerticle{
	
	static final Logger LOGGER = LoggerFactory.getLogger(PortfolioVerticle.class.getName());
	private ServiceBinder sBinder;
	private MessageConsumer portfolioServiceConsumer;
	
	private void onBusServicePublished(AsyncResult ar) {
		String val = ar.failed() ? ar.cause().getMessage() : "Portfolio Service published: " + ar.succeeded();
		LOGGER.info(val);
	}
	
	@Override
	public void start() throws Exception {
		super.start();
		
		PortfolioService service = new PortfolioServiceImpl();
		
		sBinder = new ServiceBinder(vertx) //
				.setAddress(PortfolioService.PORTFOLIO_SERVICE_ADDRESS);
		portfolioServiceConsumer = sBinder.register(PortfolioService.class, service);
		
		publishEventBusService(PortfolioService.PORTFOLIO_SERVICE_NAME, PortfolioService.PORTFOLIO_SERVICE_ADDRESS, PortfolioService.class, this::onBusServicePublished);
	}
}
