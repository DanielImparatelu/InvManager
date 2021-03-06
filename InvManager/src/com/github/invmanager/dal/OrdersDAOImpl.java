package com.github.invmanager.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAOImpl implements OrdersDAO{
	
	@Override
	public List<Orders> getAllOrders() {//returns all orders in the form of a List object
		
		DataSource ds = new DataSource();
		Connection con = ds.createConnection();//creates the connection to the database using the DataSource object
		PreparedStatement ps = null;//instantiating the PreparedStatement and ResultSet to null so they can be closed easily later
		ResultSet rs = null;
		
		List<Orders> orderList = new ArrayList<Orders>();//creates an array to hold the information
		
		try {
			ps = con.prepareStatement("SELECT * FROM ORDERS_DATA");//SQL query-selects everything from that table
			rs = ps.executeQuery();//executes the above query
			while(rs.next()) {//loops through the resultset and retrieves the data
				Orders orders = new Orders();
				
				orders.setOrderItemID(rs.getInt("Item_ID"));
				orders.setOrderItemQty(rs.getInt("Item_Quantity"));
				orders.setOrderDate(rs.getDate("Order_Date"));
				orders.setOrderStanding(rs.getInt("Order_Standing"));
				
				orderList.add(orders);//and adds it to the arraylist
			}
		}
		catch(SQLException e) {
			e.printStackTrace();//exception handling
		}
		finally {//block responsible for closing the connection and objects used
			try {
				if(con != null) {
					con.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(rs != null) {
					rs.close();
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return orderList;
	}
	
	@Override
	public void addOrder(Orders order) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("INSERT INTO ORDERS_DATA(Item_ID, Item_Quantity, Order_Date, Order_Standing) "
					+"VALUES (?,?,?,?)");//sql PreparedStatement
			ps.setInt(1, order.getOrderItemID());//assigning parameters to the statement
			ps.setInt(2, order.getOrderItemQty());
			ps.setDate(3, order.getOrderDate());
			ps.setInt(4, order.getOrderStanding());
			ps.executeUpdate();
			System.out.println("Data is inserted into the Orders table for current order");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {//closing connections
			try {
				if(ps != null) {
					ps.close();
				}
				if(con != null) {
					con.close();
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public Orders getOrder(int orderID) {
		DataSource ds = new DataSource();//create a new instance of the DataSource class
		Connection con = ds.createConnection();//use the createConnection method defined there to open the connection to the external db
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("SELECT * FROM ORDERS_DATA WHERE Order_ID = ?");//prepare the sql statement
			ps.setInt(1, orderID);
			rs = ps.executeQuery();
			
			while(rs.next()) {//loop through the result set and return the results
				Orders orders = new Orders();
				
				orders.setOrderID(rs.getInt("Order_ID"));
				orders.setOrderItemID(rs.getInt("Item_ID"));
				orders.setOrderItemQty(rs.getInt("Item_Quantity"));
				orders.setOrderDate(rs.getDate("Order_Date"));
				orders.setOrderStanding(rs.getInt("Order_Standing"));
				
				return orders;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		finally {//block responsible for closing the connection and objects used
			try {
				if(con != null) {
					con.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(rs != null) {
					rs.close();
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void updateOrder(Orders order) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("UPDATE ORDERS_DATA SET Order_Standing = ? WHERE Order_ID = ?");
			ps.setInt(1, order.getOrderStanding());
			ps.setInt(2, order.getOrderID());
			ps.executeUpdate();
			System.out.println("Updated order number "+order.getOrderID());
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {//closing connections
			try {
				if(ps != null) {
					ps.close();
				}
				if(con != null) {
					con.close();
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}

	@Override
	public void deleteOrder(int orderID) {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("DELETE FROM ORDERS_DATA WHERE Order_ID = ?");
			ps.setInt(1, orderID);//assigning the parameter to the PreparedStatement
			ps.executeUpdate();//modifying existing data is done using executeUpdate() rather than executeQuery()
			System.out.println("Order number "+orderID+" has been deleted from the database");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		finally {//closing connections
			try {
				if(ps != null) {
					ps.close();
				}
				if(con != null) {
					con.close();
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}

}
