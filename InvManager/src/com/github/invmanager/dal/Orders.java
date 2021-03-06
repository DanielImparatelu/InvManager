package com.github.invmanager.dal;

import java.sql.Date;
/*
 * This class provides the getter and setter methods for each of the Orders_Data table in the database.
 * It provides a link between the Data Access Layer and the rest of the program functionality
 */
public class Orders {
	
	private int orderID;
	private int itemID;
	private int itemQty;
	private Date orderDate;
	private int orderStanding;
	
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	public void setOrderItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public void setOrderItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public void setOrderStanding(int orderStanding) {
		this.orderStanding = orderStanding;
	}
	
	public int getOrderID() {
		return orderID;
	}
	
	public int getOrderItemID() {
		return itemID;
	}
	
	public int getOrderItemQty() {
		return itemQty;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public int getOrderStanding() {
		return orderStanding;
	}
}
