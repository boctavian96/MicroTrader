package com.trader.portofolio;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.camel.builder.xml.ResultHandler;

import com.trader.micro.Portfolio;
import com.trader.micro.PortfolioService;

import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.ServiceDiscovery;

public class PortfolioServiceImpl implements PortfolioService {

	private final Vertx vertx;
	private final Portfolio portfolio;
	private final ServiceDiscovery discovery;

	public PortfolioServiceImpl(Vertx vertx, ServiceDiscovery discovery, double initialCash) {
		super();
		this.vertx = vertx;
		this.portfolio = new Portfolio().setCash(initialCash);
		this.discovery = discovery;
	}

	@Override
	public void getPortofolio(Handler<AsyncResult<Portfolio>> resultHandler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void evaluate(Handler<AsyncResult<Double>> resultHandler) {
		// TODO Auto-generated method stub

	}

	private void sendActionOnTheEventBus(String action, int amount, JsonObject quote, int newAmount) {
		// TODO Auto-generated method stub
	}

	private void computeEvaluation(WebClient client, Handler<AsyncResult<Double>> resultHandler) {
		// We need to call the service for each company we own shares
		List<Future> results = portfolio.getShares().entrySet().stream()
				.map(entry -> getValueForCompany(client, entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());

		// We need to return only when we have all results, for this we create a
		// composite future. The set handler
		// is called when all the futures has been assigned.
		CompositeFuture.all(results).setHandler(ar -> {
			double sum = results.stream().mapToDouble(fut -> (double) fut.result()).sum();
			resultHandler.handle(Future.succeededFuture(sum));
		});
	}

	private Future<Double> getValueForCompany(WebClient client, String company, Double value) {
		Future<Double> promise = Future.future();

		return promise;
	}

	@Override
	public void buy(int amount, JsonObject quote, Handler<AsyncResult<Portfolio>> resultHandler) {
		if (amount <= 0) {
			resultHandler.handle(Future.failedFuture(
					"Cannot buy " + quote.getString("name") + " - the amount must be " + "greater than 0"));
			return;
		}

		if (quote.getInteger("shares") < amount) {
			resultHandler.handle(Future.failedFuture("Cannot buy " + amount + " - not enough "
					+ "stocks on the market (" + quote.getInteger("shares") + ")"));
			return;
		}

		double price = amount * quote.getDouble("ask");
		String name = quote.getString("name");
		// 1) do we have enough money
		if (portfolio.getCash() >= price) {
			// Yes, buy it
			portfolio.setCash(portfolio.getCash() - price);
			int current = portfolio.getAmount(name);
			int newAmount = current + amount;
			portfolio.getShares().put(name, newAmount);
			sendActionOnTheEventBus("BUY", amount, quote, newAmount);
			resultHandler.handle(Future.succeededFuture(portfolio));
		} else {
			resultHandler.handle(Future.failedFuture("Cannot buy " + amount + " of " + name + " - "
					+ "not enough money, " + "need " + price + ", has " + portfolio.getCash()));
		}
	}

	@Override
	public void sell(int amount, JsonObject quote, Handler<AsyncResult<Portfolio>> resultHandler) {
		if (amount <= 0) {
			resultHandler.handle(Future.failedFuture(
					"Cannot sell " + quote.getString("name") + " - the amount must be " + "greater than 0"));
			return;
		}

		double price = amount * quote.getDouble("bid");
		String name = quote.getString("name");
		int current = portfolio.getAmount(name);
		// 1) do we have enough stocks
		if (current >= amount) {
			// Yes, sell it
			int newAmount = current - amount;
			if (newAmount == 0) {
				portfolio.getShares().remove(name);
			} else {
				portfolio.getShares().put(name, newAmount);
			}
			portfolio.setCash(portfolio.getCash() + price);
			sendActionOnTheEventBus("SELL", amount, quote, newAmount);
			resultHandler.handle(Future.succeededFuture(portfolio));
		} else {
			resultHandler.handle(Future.failedFuture(
					"Cannot sell " + amount + " of " + name + " - " + "not enough stocks " + "in portfolio"));
		}

	}

	private static String encode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported encoding");
		}
	}

	public Vertx getVertx() {
		return vertx;
	}

	public Portfolio getPortofolio() {
		return portfolio;
	}

	public ServiceDiscovery getDiscovery() {
		return discovery;
	}

}
