package com.github.invmanager.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JOptionPane;

import com.github.invmanager.dal.Items;
import com.github.invmanager.dal.ItemsDAOImpl;
import com.github.invmanager.gui.InventoryGUI;

public class ItemPrediction {
	NumberFormat df = new DecimalFormat("#.0");
	//InventoryGUI gui = new InventoryGUI();
	Items items = new Items();
	ItemsDAOImpl itemsDAO = new ItemsDAOImpl();
	double futureSale = 0;
	double futureSale2 = 0;
	
	public String input = InventoryGUI.message;
	public int pastSale;
	
	public int getItemPastSale() {
		for (Items item : itemsDAO.getItemByName(input)) {
			pastSale = item.getItemQty();
		}
		return pastSale;
		
	}
	
	public double predictFutureSale() {
		LinearRegression.getPrediction();
		futureSale = LinearRegression.pred;
		System.out.println("future sale "+futureSale);
		return futureSale;
	}
	
	public double predictFutureSale2() {
		LinearRegression.getPrediction2();
		futureSale2 = LinearRegression.pred2;
		System.out.println("future sale 2 "+futureSale2);
		return futureSale2;
	}
	
	public void execute() {
		getItemPastSale();
		predictFutureSale();
		predictFutureSale2();
		JOptionPane.showMessageDialog(null, "The item is "+ input + ", and the current quantity is "
																	+ pastSale + ". Sale prediction for next month: "
																	+ df.format(futureSale) + " or "+df.format(futureSale2));
	}

}
