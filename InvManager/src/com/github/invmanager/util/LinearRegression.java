package com.github.invmanager.util;


import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.junit.jupiter.api.Test;

public class LinearRegression {
	static double pred = 0;
	static double pred2 = 0;
	@Test
	public void testRegression() {
		System.out.println("TESTING REGRESSION");
		OLSMultipleLinearRegression reg = new OLSMultipleLinearRegression();

		double[][] X = {{1,1},{2,0},{3,0},{4,1},{5,0},{6,0},{7,0},{8,0},{9,1},{10,1},{11,0},{12,1}};

		double[] Y = {190,60,50,100,70,50,50,45,100,120,80,200};
		reg.setNoIntercept(false);
		reg.newSampleData(Y, X);
		double[] beta = reg.estimateRegressionParameters();
		double residualsqsum = reg.calculateResidualSumOfSquares();
		double totalsqsum = reg.calculateTotalSumOfSquares();
		double adjrsq = reg.calculateAdjustedRSquared();
		double regstderr = reg.estimateRegressionStandardError();
		double regandvar = reg.estimateRegressandVariance();
		double[] regparam = reg.estimateRegressionParameters();
		RealMatrix fsd = reg.calculateHat();
		for (double d : beta) {
			System.out.println("D: " + d);
		}
		System.out.println("residual sq sum " + residualsqsum);
		System.out.println("total sq sum " + totalsqsum);
		System.out.println("adjusted r squared " + adjrsq);
		System.out.println("reg std error " + regstderr);
		System.out.println("regress and variance " + regandvar);
		System.out.println(fsd.getData());

		for (double d : regparam) {
			System.out.println("regparam " + d);
		}
		for (double d : beta) {
			System.out.println("beta " + d);
		}

	}

	public static double getPrediction() {
		SimpleRegression simplereg = new SimpleRegression(true);

		// passing data to the model
		// model will be fitted automatically by the class 
		simplereg.addData(new double[][] {
			{1, 190},
			{0, 60},
			{0, 50},
			{1, 100},
			{0, 70},
			{1, 100},
			{1, 120},
			{0, 80},
			{1, 100},
			{1, 120},
			{0, 80},
			{1, 200}
		});

		// querying for model parameters
		System.out.println("slope = " + simplereg.getSlope());
		System.out.println("intercept = " + simplereg.getIntercept());

		// trying to run model for unknown data
		System.out.println("prediction for 1 based on holiday = " + simplereg.predict(1));
		pred = simplereg.predict(1);
		return pred;
	}

	public static double getPrediction2() {
		SimpleRegression simplereg = new SimpleRegression(true);

		// passing data to the model
		// model will be fitted automatically by the class 
		simplereg.addData(new double[][] {
			{1, 190},
			{2, 60},
			{3, 50},
			{4, 100},
			{5, 70},
			{6, 100},
			{7, 120},
			{8, 80},
			{9, 100},
			{10, 120},
			{11, 80},
			{12, 200}
		});
		System.out.println("slope2 = " + simplereg.getSlope());
		System.out.println("intercept2 = " + simplereg.getIntercept());

		// trying to run model for unknown data
		System.out.println("prediction for 1 based on month = " + simplereg.predict(1));
		pred2 = simplereg.predict(1);
		return pred2;
	}


}