package com.github.invmanager.util;

import java.util.List;

import javax.swing.JOptionPane;

import com.github.invmanager.dal.Items;
import com.github.invmanager.dal.ItemsDAOImpl;
import com.github.invmanager.gui.InventoryGUI;

public class ItemPrediction {
	
	//InventoryGUI gui = new InventoryGUI();
	Items items = new Items();
	ItemsDAOImpl itemsDAO = new ItemsDAOImpl();
	
	public String input = InventoryGUI.message;
	public int pastSale;
	public int futureSale;
	
	public int getItemPastSale() {
//		for (Items item : itemsDAO.getItemByName(input)) {
//			textArea.append("ID = " + item.getItemID() + ", Name = " + item.getItemName() + ", expiry date = "
//					+ item.getItemExpDate() + ", last restocked = " + item.getitemLastRestocked() + "\n\n");
//		}
		//Items item = (Items) itemsDAO.getItemByName(input);
		//String itemName = itemsDAO.getItemByName(input).toString();
		for (Items item : itemsDAO.getItemByName(input)) {
			pastSale = item.getItemQty();
		}
		
		return pastSale;
		
	}
	
	public int predictFutureSale() {
		return pastSale + 100;
	}
	
	public void execute() {
		getItemPastSale();
		JOptionPane.showMessageDialog(null, "The item is "+ input + ", and last week has been sold "
																	+ pastSale + ". Sale prediction for next week: "
																	+ futureSale);
	}

}
