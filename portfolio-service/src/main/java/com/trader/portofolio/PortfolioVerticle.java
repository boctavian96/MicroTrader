package com.trader.portofolio;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.bean.ProxyHelper;
import org.apache.camel.impl.DefaultCamelContext;

import com.trader.micro.PortfolioService;
import com.trader.utils.MicroServiceVerticle;

import io.vertx.camel.CamelBridge;
import io.vertx.camel.CamelBridgeOptions;
import io.vertx.camel.InboundMapping;
import io.vertx.camel.OutboundMapping;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class PortfolioVerticle extends MicroServiceVerticle {

	final static Logger logger = LoggerFactory.getLogger(PortfolioVerticle.class.getName());

	@Override
	public void start() throws Exception {
		super.start();

		// Service Object
		PortfolioServiceImpl service = new PortfolioServiceImpl(vertx, discovery,
				config().getDouble("money", 10000.00));

		// Apache Camel
		CamelContext camel = new DefaultCamelContext();
		CamelBridge
				.create(vertx, new CamelBridgeOptions(camel)
						.addInboundMapping(InboundMapping.fromCamel("direct:stuff").toVertx("eventbus-address"))
						.addOutboundMapping(OutboundMapping.fromVertx("eventbus-address").toCamel("stream:out")))
				.start();

		// Camel endpoint
		Endpoint endpoint = camel.getEndpoint("direct:foo");

		// Register the service
		ProxyHelper.createProxy(endpoint, PortfolioService.class);

		// Publish the service
		publishEventBusService("portofolio", PortfolioService.ADDRESS, PortfolioService.class, ar -> {
			if (ar.failed()) {
				ar.cause().printStackTrace();
			} else {
				logger.info("Portfolio service published: " + ar.succeeded());
			}
		});

	}
}
