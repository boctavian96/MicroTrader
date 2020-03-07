package com.trader.simple;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Portfolio {
	private int id;
	private String name;
	private double stocks;
	
	public Portfolio() {
		this.id = 0;
		this.name = "dummy";
		this.stocks = 0D;
	}
	
	//Selectors
	@JsonProperty
	public int getId() {
		return this.id;
	}
	
	@JsonProperty
	public String getName() {
		return this.name;
	}
	
	@JsonProperty
	public double getStocks() {
		return this.stocks;
	}
}
