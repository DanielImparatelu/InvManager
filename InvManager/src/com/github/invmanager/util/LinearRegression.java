package com.github.invmanager.util;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class LinearRegression {

    public static void main(String[] args) {

        // creating regression object, passing true to have intercept term
        SimpleRegression simpleRegression = new SimpleRegression(true);
    	OLSMultipleLinearRegression multipleRegression = new OLSMultipleLinearRegression();
       double[][] data =         {{1,	190},
    	        {2,		60},
    	        {3,		50},
    	        {4,		100},
    	        {5,		70},
    	        {6,		50},
    	        {7,		50},
    	        {8,		45},
    	        {9,		100},
    	        {10,		120},
    	        {11,		80},
    	        {12,		200},
    	        {1,	150},
    	        {2,		80},
    	        {3,		60}};
        // passing data to the model
        // model will be fitted automatically by the class 
//        simpleRegression.addData(new double[][] {
//                {4, 120, 1},
//                {5, 100, 1},
//                {6, 150, 1},
//                {7, 123, 1},
//                {8, 150, 1}
//        });
       simpleRegression.addData(data);

       double[][]x = {{1,190},{2, 60},{3,50},{4,100},{5,70}};
       double[]y = {1,0,0,1,0};
       multipleRegression.newSampleData(y, x);
       multipleRegression.estimateRegressionParameters();
       
       
        // querying for model parameters
        System.out.println("slope = " + simpleRegression.getSlope());
        System.out.println("intercept = " + simpleRegression.getIntercept());

        // trying to run model for unknown data
        System.out.println("prediction for 1.5 = " + simpleRegression.predict(4));
//TODO
        /*
         * Scan an item and new popup window sayint the name of the item and how manytimes its nbeen sold in the past monthj
         * and type in a month to predict for
         * 
         */
    }

}