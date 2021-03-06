package com.github.invmanager.dal;

import java.util.List;

/*
 * This is the DAO interface for the Users Objects. It provides definitions of the methods used by the UsersDAO Implementation
 * class.
 */
public interface UsersDAO {

	public List<Users> getAllUsers();
	public List<Users> retrieveUser(String uname, String upass);
	public Users getUserByName(String userName);
	public void addUser(Users user);
	public void updateUser(Users user);
	public void deleteUser (String userName);
	
}
