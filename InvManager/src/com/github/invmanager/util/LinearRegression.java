package com.github.invmanager.util;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import com.github.invmanager.gui.InventoryGUI;

public class LinearRegression {
	public static double pred = 0;
	static double pred2 = 0;
	static int month;
	static boolean isHoliday = false;
	/*
	 * TODO
	 * adjust the x values to closely match the y values
	 * make dropdown list with month name
	 * in code correspond each month to an x value
	 */
	private static boolean setIsHoliday() {
		
		month = Integer.valueOf(InventoryGUI.textEnterMonth.getText());

		if ((month == 1)||(month == 4)||(month == 9)||(month == 10)||(month == 12)) {
			isHoliday = true;
		}
		else {
			isHoliday = false;
		}
		return isHoliday;
	}

	public static double getPrediction() {
		SimpleRegression simplereg = new SimpleRegression(true);
		month = Integer.valueOf(InventoryGUI.textEnterMonth.getText());
		setIsHoliday();

		// passing data to the model
		// model will be fitted automatically by the class 

		simplereg.addData(new double[][] {
			{18, 190},
			{4, 60},
			{2, 50},
			{12, 100},
			{10, 70},
			{6, 50},
			{8, 50},
			{3, 45},
			{14, 100},
			{16, 120},
			{11, 80},
			{20, 200}
		});
		simplereg.addData(new double[][] {
			{18, 180},
			{4, 70},
			{2, 55},
			{12, 125},
			{10, 78},
			{6, 70},
			{8, 65},
			{3, 55},
			{14, 120},
			{16, 150},
			{11, 90},
			{20, 220}
		});
		simplereg.addData(new double[][] {
			{18, 160},
			{4, 85},
			{2, 70},
			{12, 145},
			{10, 85},
			{6, 90},
			{8, 88},
			{3, 85},
			{14, 130},
			{16, 160},
			{11, 100},
			{20, 230}
		});
//		simplereg.addData(new double[][] {
//			{1.1,190},
//			{1.2,180},
//			{1.3,160},
//			{1.4,150},
//			{1.5,200},
//			{1.6,202},
//		});

		System.out.println("prediction for month "+month+" based on holiday = " + simplereg.predict(month));
		pred = simplereg.predict(month);
		//InventoryGUI.lblSaleProjection.setText(String.valueOf(pred));
		if(isHoliday == true) {
			System.out.println("holiday");
			double salePredictionHoliday = pred +(0.1 *pred);
			pred = pred *(0.1 *pred);
			InventoryGUI.lblSaleProjection.setText(String.valueOf(salePredictionHoliday));
			System.out.println("sph "+salePredictionHoliday);
			return salePredictionHoliday;
		}
		else if(isHoliday == false) {
			System.out.println("not holiday");
			InventoryGUI.lblSaleProjection.setText(String.valueOf(pred));
			System.out.println("pred "+pred);
			return pred;
		}
		else {
			return 0;
		}


	}

}