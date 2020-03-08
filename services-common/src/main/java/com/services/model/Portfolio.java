package com.services.model;

public class Portfolio {
	private int id;
	private String name;
	private double stocks;

	public Portfolio(int id, String name, double stocks) {
		super();
		this.id = id;
		this.name = name;
		this.stocks = stocks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getStocks() {
		return stocks;
	}

	public void setStocks(double stocks) {
		this.stocks = stocks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(stocks);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(stocks) != Double.doubleToLongBits(other.stocks))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Portfolio [id=" + id + ", name=" + name + ", stocks=" + stocks + "]";
	}

}
