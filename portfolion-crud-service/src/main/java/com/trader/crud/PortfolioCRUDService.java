package com.trader.crud;

public interface PortfolioCRUDService {

	public void createUser(int userId, String userName, double userMoney);

	public void deleteUser(int id);

	public void updateUser(int userId, String userName, double userMoney);

	public void getUser(int id);

}
