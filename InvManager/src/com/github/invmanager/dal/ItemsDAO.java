package com.github.invmanager.dal;

import java.util.List;
/*
 * This is the DAO interface for the Items, which provides definitions of the methods used by the OrdersDAOImpl class
 */
public interface ItemsDAO {

	public List<Items> getAllItems();
	public List<Items> getItemById(int itemID);
	public List<Items> getItemByName(String itemName);
	public void addItem(Items item);
	public void updateItems(Items items);
	public void deleteItems (int itemID);
}
