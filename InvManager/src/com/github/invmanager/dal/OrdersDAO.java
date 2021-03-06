package com.github.invmanager.dal;

import java.util.List;
/*
 * THis is the DAO interface for the Orders, which provides definitions of the methods used by the OrdersDAOImpl class
 */
public interface OrdersDAO {

	public List<Orders> getAllOrders();
	public Orders getOrder(int orderID);
	public void addOrder(Orders order);
	public void updateOrder(Orders order);
	public void deleteOrder (int orderID);
	
}
