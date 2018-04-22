package com.github.invmanager.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemsDAOImpl implements ItemsDAO {

	@Override
	public List<Items> getAllItems() {
		DataSource ds = new DataSource();
		Connection con = ds.createConnection();//creates the connection to the database using the DataSource object
		PreparedStatement ps = null;//instantiating the PreparedStatement and ResultSet to null so they can be closed easily later
		ResultSet rs = null;

		List<Items> itemList = new ArrayList<Items>();//creates an array to hold the information

		try {
			ps = con.prepareStatement("SELECT * FROM ITEMS_DATA");//SQL query-selects everything from that table
			rs = ps.executeQuery();//executes the above query
			while(rs.next()) {//loops through the resultset and retrieves the data
				Items items = new Items();

				items.setItemID(rs.getString("Item_ID"));
				items.setItemName(rs.getString("Item_Name"));
				items.setItemQty(rs.getInt("Item_Quantity"));
				//items.setItemExpDate(rs.getDate("Item_Exp_Date"));
				//items.setItemLastRestocked(rs.getDate("Item_Last_Restocked"));

				itemList.add(items);//and adds it to the arraylist
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
		return itemList;
	}

	@Override
	public boolean getItemById(String itemID) throws SQLException {
		DataSource ds = new DataSource();//create a new instance of the DataSource class
		Connection con = ds.createConnection();//use the createConnection method defined there to open the connection to the external db
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean exist = false;
		//List<Items> itemList = new ArrayList<Items>();//creates an array to hold the information
		try {
			ps = con.prepareStatement("SELECT * FROM ITEMS_DATA WHERE Item_ID = ?");//prepare the sql statement
			ps.setString(1, itemID);
			try(ResultSet result=ps.executeQuery()) {
				exist = result.next();
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
		return exist;
	}

	@Override
	public List<Items> getItemByName(String itemName) {
		DataSource ds = new DataSource();//create a new instance of the DataSource class
		Connection con = ds.createConnection();//use the createConnection method defined there to open the connection to the external db
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Items> itemList = new ArrayList<Items>();//creates an array to hold the information
		try {
			ps = con.prepareStatement("SELECT * FROM ITEMS_DATA WHERE Item_Name LIKE ?");//prepare the sql statement
			ps.setString(1, "%"+itemName+"%");//SQL Wildcard % searches data that matches the keyword
			rs = ps.executeQuery();

			while(rs.next()) {//loop through the result set and return the results
				Items items = new Items();

				items.setItemID(rs.getString("Item_ID"));
				items.setItemName(rs.getString("Item_Name"));
				items.setItemQty(rs.getInt("Item_Quantity"));
				itemList.add(items);

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
		return itemList;
	}

	@Override
	public void addItem(Items item) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("INSERT INTO ITEMS_DATA(Item_ID, Item_Name, Item_Quantity) "
					+"VALUES (?,?,?)");//sql PreparedStatement
			ps.setString(1, item.getItemID());//assigning parameters to the statement
			ps.setString(2, item.getItemName());
			ps.setInt(3, item.getItemQty()+1);
			ps.executeUpdate();
			System.out.println("Item has been added");
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
	public void updateItems(Items items) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("UPDATE ITEMS_DATA SET Item_Quantity = Item_Quantity + 1 WHERE Item_ID = ?");
			//ps.setInt(1, items.getItemQty());
			ps.setString(1, items.getItemID());
			ps.executeUpdate();
			System.out.println("Updated item");

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
	public void updateItemsRemoved(String itemID) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("UPDATE ITEMS_DATA SET Item_Quantity = Item_Quantity - 1 WHERE Item_ID = ?");
			//ps.setInt(1, items.getItemQty());
			ps.setString(1, itemID);
			ps.executeUpdate();
			System.out.println("Updated item");
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
	public void deleteItems(String itemID) {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("DELETE FROM ITEMS_DATA WHERE Item_ID = ?");
			ps.setString(1, itemID);//assigning the parameter to the PreparedStatement
			ps.executeUpdate();//modifying existing data is done using executeUpdate() rather than executeQuery()
			System.out.println("Item removed from database");
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
	public int getQtyById(String itemID) {//method used to retrieve the quantity of an item correctly
		Connection con = null;
		PreparedStatement ps = null;

		int qty = 0;
		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("SELECT Item_Quantity FROM ITEMS_DATA WHERE Item_ID = ?");
			ps.setString(1, itemID);//assigning the parameter to the PreparedStatement
			//ps.executeUpdate();
			try(ResultSet result=ps.executeQuery()) {//try with resources
				qty = result.getInt(1);//set the returned value
			}
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
		return qty;
	}
}
