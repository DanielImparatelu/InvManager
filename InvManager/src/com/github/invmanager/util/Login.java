package com.github.invmanager.util;

import com.github.invmanager.dal.Users;
import com.github.invmanager.dal.UsersDAOImpl;

public class Login {

	Users users = new Users();
	UsersDAOImpl usersDAO = new UsersDAOImpl();
	
	public void login(String username, String password) {
		username = users.getName();
		password = users.getPassword();
	}
}
