package com.github.invmanager.dal;

/*
 * This class provides the getter and setter methods for each of the Users_Data table in the database.
 * It provides a link between the Data Access Layer and the rest of the program functionality
 */
public class Users {
	
	private String userName;
	private String password;
	private int isAdmin;
	
//	Users(String userName, String password, int isAdmin){
//		this.userName = userName;
//		this.password = password;
//		this.isAdmin = isAdmin;
//	}
//
//	Users(String userName){
//		this.userName = userName;
//	}
	public void setName(String userName) {
		this.userName = userName;
		
	}

	public void setPassword(String password) {
		this.password = password;
		
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
		
	}

	public String getName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
	public int getIsAdmin() {
		return isAdmin;
	}

}
