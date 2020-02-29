package com.trader.micro;

import java.util.Map;
import java.util.TreeMap;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Used as datamodel for the service.
 * 
 * @author obodnariu
 *
 */
@DataObject(generateConverter = true)
public class Portfolio {

	private Map<String, Integer> shares = new TreeMap();
	private double cash;

	public Portfolio() {
		// TODO: Update this.
	}

	public Portfolio(Portfolio portfolio) {
		this.shares = new TreeMap<>(portfolio.shares);
		this.cash = portfolio.cash;
	}

	public Portfolio(JsonObject json) {
		// PortofolioConverter.fromJson(json, this);
	}

	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();
		// PortofolioConverter.toJson(this, jsonObject);
		return null;
	}

	public Map<String, Integer> getShares() {
		return shares;
	}

	public void setShares(Map<String, Integer> shares) {
		this.shares = shares;
	}

	public double getCash() {
		return cash;
	}

	public Portfolio setCash(double cash) {
		this.cash = cash;
		return this;
	}

	public int getAmount(String name) {
		Integer current = shares.get(name);
		if (current == null) {
			return 0;
		}
		return current;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cash);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((shares == null) ? 0 : shares.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Portfolio other = (Portfolio) obj;
		if (Double.doubleToLongBits(cash) != Double.doubleToLongBits(other.cash))
			return false;
		if (shares == null) {
			if (other.shares != null)
				return false;
		} else if (!shares.equals(other.shares))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Portofolio [shares=" + shares + ", cash=" + cash + "]";
	}

}
