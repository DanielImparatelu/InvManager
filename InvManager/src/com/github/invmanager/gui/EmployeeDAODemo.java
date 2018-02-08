package com.github.invmanager.gui;

import com.github.invmanager.dal.Items;
import com.github.invmanager.dal.ItemsDAO;
import com.github.invmanager.dal.ItemsDAOImpl;
import com.github.invmanager.dal.Users;
import com.github.invmanager.dal.UsersDAO;
import com.github.invmanager.dal.UsersDAOImpl;

public class EmployeeDAODemo {

	public static void main(String [] args) {
		
		UsersDAO usersDao = new UsersDAOImpl();
		ItemsDAO itemsDao = new ItemsDAOImpl();
		
		//retrieve all users
		for(Users users: usersDao.getAllUsers() ) {
			System.out.println("user: [User Name = "+ users.getName()+", Password = "+users.getPassword()+", is admin = "+users.getIsAdmin()+"]");
		}
		
//		for(Items items: itemsDao.getAllItems() ) {
//			System.out.println("item: [ID = "+ items.getItemID()+", Name = "+items.getItemName()+", expiry date = "+items.getItemExpDate()+", last restocked = "+items.getitemLastRestocked()+"]");
//		}
		
		//add user
		Users userTest = new Users();
		userTest.setName("test2");
		userTest.setPassword("test2");
		userTest.setIsAdmin(1);
		
//		usersDao.addUser(userTest);
		Items itemTest = new Items();
		itemTest.setItemID(71);
		itemTest.setItemName("test2");
		itemTest.setItemExpDate("2019-02-02");
		itemTest.setItemLastRestocked("2018-01-01");
		//itemsDao.addItem(itemTest);
		//itemsDao.updateItems();
		
		//usersDao.deleteUser("Test UserName2");
	}
}
