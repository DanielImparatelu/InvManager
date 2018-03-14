package com.github.invmanager.dal;

import java.sql.Date;

/*
 * This class provides the getter and setter methods for each of the Items_Data table in the database.
 * It provides a link between the Data Access Layer and the rest of the program functionality
 */
public class Items {

	private int itemID;
	private String itemName;
	private int itemQty;
	private java.sql.Date itemExpDate;
	private java.sql.Date itemLastRestocked;
	
	public Items() {//empty constructor to create object in ItemsDAOImpl
		
	}
	
//	public Items(int itemID, String itemName, int itemQty, Date itemExpDate, Date itemLastRestocked) {//constructor to add new items to the system
//		this.itemID = itemID;
//		this.itemName = itemName;
//		this.itemQty = itemQty;
//		this.itemExpDate = itemExpDate;
//		this.itemLastRestocked = itemLastRestocked;
//	}
	
	public Items(int itemID, String itemName, int itemQty, String itemExpDate, String itemLastRestocked) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.itemQty = itemQty;
		this.itemExpDate = Date.valueOf(itemExpDate);
		this.itemLastRestocked = Date.valueOf(itemLastRestocked);
	}
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	
	public void setItemExpDate(java.sql.Date expDate) {
		this.itemExpDate = expDate;
	}
	
	public void setItemLastRestocked(Date itemLastRestocked) {
		this.itemLastRestocked = itemLastRestocked;
	}
	
	
	public int getItemID() {
		return itemID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public int getItemQty() {
		return itemQty;
	}
	
	public Date getItemExpDate() {
		return itemExpDate;
	}
	
	public Date getitemLastRestocked() {
		return itemLastRestocked;
	}
}


