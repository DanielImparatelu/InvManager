package com.github.invmanager.dal;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.datasources.*;
/*
 * This class manages the connection to the external data source using the Apache DBCP Library
 * This connection pooling method is used in order to avoid acquiring physical connections each time data is requested,
 * for performance reasons.
 * 
 * Using a database driver to connect to an Internet server is bad practice, and should never be used, as I just found out
 * It causes latency and cannot deal with potential problems such as the server being down
 * Instead I should have opted for a SOAP or RESTful web service to communicate with the server
 * But I found this out late in the project and did not have time to redesign the whole DAL
 * Which is why the program is a lot slower than it should be
 */
public class DataSource {
/*
 * instantiate the objects used
 */
	Connection connection = null;
	BasicDataSource bds = new BasicDataSource();
	
	public DataSource() {
		/*
		 * construct the data source object with the SQL driver used, URL address of the database,
		 * username and password as parameters
		 */
//		bds.setDriverClassName("com.mysql.jdbc.Driver");
//		bds.setUrl("jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2216024");
//		bds.setUsername("sql2216024");
//		bds.setPassword("dN7%qI2%");
		bds.setDriverClassName("org.sqlite.JDBC");
		bds.setUrl("jdbc:sqlite:./../test.db");

		//Class.forName("org.sqlite.JDBC");//loads the jdbc driver into the driver manager
		//con = DriverManager.getConnection("jdbc:sqlite:./../rm1.db");
		//initialise();
	}
	
	public Connection createConnection() {
		
		Connection con = null;
		
		try {
			if(connection != null) {//if another connection object is open it will not create a new one
				System.out.println("Cant create new connection");
			}
			else {
				con = bds.getConnection();//creates a new connection
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
