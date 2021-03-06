package com.github.invmanager.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UsersDAOImpl implements UsersDAO {
	/*
	 * This class is responsible for managing the generic CRUD operations on the Users table in the database
	 */

	@Override
	public List<Users> getAllUsers() {//returns all users in the form of a List object
	
		DataSource ds = new DataSource();
		Connection con = ds.createConnection();//creates the connection to the database using the DataSource object
		PreparedStatement ps = null;//instantiating the PreparedStatement and ResultSet to null so they can be closed easily later
		ResultSet rs = null;
		
		List<Users> userList = new ArrayList<Users>();//creates an array to hold the information
		
		try {
			ps = con.prepareStatement("SELECT * FROM USERS_DATA");//SQL query-selects everything from that table
			rs = ps.executeQuery();//executes the above query
			while(rs.next()) {//loops through the resultset and retrieves the data
				Users users = new Users();
				
				users.setName(rs.getString("User_Name"));
				users.setPassword(rs.getString("User_Password"));
				users.setIsAdmin(rs.getInt("User_Is_Admin"));
				
				userList.add(users);//and adds it to the arraylist
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
		return userList;
	}

	@Override
	public List<Users> retrieveUser(String uname, String upass){
		
		DataSource ds = new DataSource();
		Connection con = ds.createConnection();//creates the connection to the database using the DataSource object
		PreparedStatement ps = null;//instantiating the PreparedStatement and ResultSet to null so they can be closed easily later
		ResultSet rs = null;
		
		List<Users> userList = new ArrayList<Users>();//creates an array to hold the information
		
		try {
			ps = con.prepareStatement("SELECT User_Name, User_Password, User_Is_Admin FROM USERS_DATA WHERE User_Name=? AND User_Password=?");//SQL query-selects everything from that table
			ps.setString(1, uname);
			ps.setString(2, upass);
			rs = ps.executeQuery();//executes the above query
			while(rs.next()) {//loops through the resultset and retrieves the data
				Users users = new Users();
				
				users.setName(rs.getString("User_Name"));
				users.setPassword(rs.getString("User_Password"));
				users.setIsAdmin(rs.getInt("User_Is_Admin"));
				
				userList.add(users);//and adds it to the arraylist
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
		return userList;
	}
	
	@Override
	public void updateUser(Users user) {

	}

	@Override
	public void deleteUser(String userName) {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("DELETE FROM USERS_DATA WHERE User_Name = ?");
			ps.setString(1, userName);//assigning the parameter to the PreparedStatement
			ps.executeUpdate();//modifying existing data is done using executeUpdate() rather than executeQuery()
			System.out.println("User "+userName+" has been deleted");
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
	public void addUser(Users user) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			DataSource ds = new DataSource();
			con = ds.createConnection();
			ps = con.prepareStatement("INSERT INTO USERS_DATA(User_Name, User_Password, User_Is_Admin) "
					+"VALUES (?,?,?)");//sql statement
			ps.setString(1, user.getName());//assigning parameters to the statement
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getIsAdmin());
			ps.executeUpdate();
			System.out.println("Data is inserted into the Users table for user "+user.getName());
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
	public Users getUserByName(String userName) 	 {
		DataSource ds = new DataSource();//create a new instance of the DataSource class
		Connection con = ds.createConnection();//use the createConnection method defined there to open the connection to the external db
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("SELECT * FROM USERS_DATA WHERE User_Name = ?");//prepare the sql statement
			ps.setString(1, userName);
			rs = ps.executeQuery();
			
			while(rs.next()) {//loop through the result set and return the results
				Users users = new Users();
				
				users.setName(rs.getString("User_Name"));
				users.setPassword(rs.getString("User_Password"));
				users.setIsAdmin(rs.getInt("User_Is_Admin"));
				
				return users;
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


}
