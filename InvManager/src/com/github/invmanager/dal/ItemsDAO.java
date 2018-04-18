package com.github.invmanager.dal;

import java.sql.SQLException;
import java.util.List;
/*
 * This is the DAO interface for the Items, which provides definitions of the methods used by the OrdersDAOImpl class
 */
public interface ItemsDAO {

	public List<Items> getAllItems();
	public boolean getItemById(String itemID) throws SQLException;
	public List<Items> getItemByName(String itemName);
	public void addItem(Items item);
	public void updateItems(Items items);
	public void deleteItems (String itemID);
}
