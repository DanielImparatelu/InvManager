package com.github.invmanager.dal;

import java.sql.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.datasources.*;
/*
 * This class manages the connection to the external data source using the Apache DBCP Library
 * This connection pooling method is used in order to avoid acquiring physical connections each time data is requested,
 * for performance reasons.
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
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl("jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2216024");
		bds.setUsername("sql2216024");
		bds.setPassword("dN7%qI2%");
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
