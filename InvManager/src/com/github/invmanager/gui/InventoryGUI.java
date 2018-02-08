package com.github.invmanager.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.github.invmanager.dal.Items;
import com.github.invmanager.dal.ItemsDAOImpl;
import com.github.invmanager.dal.Users;
import com.github.invmanager.dal.UsersDAOImpl;

/*
 * @author Daniel Imparatelu
 * December 2017
 * This is the main interface of the program
 */

public class InventoryGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	Items items = new Items();
	ItemsDAOImpl itemsDAO = new ItemsDAOImpl();

	Users users = new Users();
	UsersDAOImpl usersDAO = new UsersDAOImpl();
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource().equals(addItemByID)) {
			
			itemsDAO.addItem(new Items(006, "TEST213", 10, "2019-02-03", "2018-02-01"));
		}
		
		else if(e.getSource().equals(showAllItems)) {
			for(Items item: itemsDAO.getAllItems() ) {
				textArea.append("item: [ID = "+ item.getItemID()+", Name = "+item.getItemName()+", expiry date = "+item.getItemExpDate()+", last restocked = "+item.getitemLastRestocked()+"]"+"\n\n");
			}
		}
		
		else if(e.getSource().equals(placeOrder)) {
			
		}
		
		else if (e.getSource().equals(showAllUsers)) {
			
		}
		
		else if (e.getSource().equals(getItemByName)) {
			for(Items item: itemsDAO.getItemByName("TEST") ) {
				textArea.append("item: [ID = "+ item.getItemID()+", Name = "+item.getItemName()+", expiry date = "+item.getItemExpDate()+", last restocked = "+item.getitemLastRestocked()+"]"+"\n\n");
			}
		}
		
		else if (e.getSource().equals(btnLogin)) {
			String inputUName = userNameField.getText();
			char[] inputCharField = passwordField.getPassword();	
			String inputPassField = new String(inputCharField);//converting the char input from the password field into a string so I can match it against the database value
			
			
			String dbUName = users.getName();
			String dbUPass = users.getPassword();
			
			usersDAO.retrieveUser(inputUName, inputPassField);
			
			if(inputUName.equals(dbUName) && (inputPassField.equals(dbUPass))) {
				startWindow.setVisible(false);
				mainWindow.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "Username or password incorrect");
			}
			
//			for(Users user: usersDAO.retrieveUser(inputUName, inputPassField)) {//for each loop to retrieve results
//				
//				
//				
//				System.out.println(inputUName);
//				System.out.println(inputPassField);
//				System.out.println(dbUName);
//				System.out.println(dbUPass);
//			
//				if(inputUName.equals(dbUName) && (inputPassField.equals(dbUPass))) {
//					startWindow.setVisible(false);
//					mainWindow.setVisible(true);
//				}
//				
//			}
//			lblStatus.setText("Username or password incorrect");
			//the joptionpane doesnt work because the method called returns all users and some of the inputs dont match
			//and the else loop runs either way, as the whole block is in a for loop
			//JOptionPane.showMessageDialog(null, "Username or password incorrect");
		}
		
		else if(e.getSource().equals(btnExit)){
			System.exit(0);
		}
	}
	
	private JButton placeOrder = new JButton("Place Order");
	private JButton addItemByID = new JButton("Add Item");
	private JButton showAllItems = new JButton("Show All Items");
	private JButton showAllUsers = new JButton("Show Users");
	private JButton getItemByName = new JButton("Get Item By Name");
	private JButton btnNewButton_5 = new JButton("New button");
	private JButton btnNewButton_6 = new JButton("New button");
	private JScrollPane scrollPane = new JScrollPane();
	private JTextArea textArea = new JTextArea();
	private final JPanel startWindow = new JPanel();
	private final JPanel mainWindow = new JPanel();
	private final JButton btnLogin = new JButton("Login");
	private final JButton btnExit = new JButton("Exit");
	private final JPasswordField cardField = new JPasswordField();
	private final JTextField userNameField = new JTextField();
	private final JPasswordField passwordField = new JPasswordField();
	private final JLabel lblScan = new JLabel("Scan/Swipe Card:");
	private final JLabel lblUsername = new JLabel("Username:");
	private final JLabel lblPassword = new JLabel("Password:");
	private final JLabel lblStatus = new JLabel("");


		public InventoryGUI() {

			super();
			userNameField.setColumns(10);
			setSize(new Dimension(800, 600));
			getContentPane().setBackground(Color.LIGHT_GRAY);
			setTitle("Inventory Management");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getContentPane().setLayout(new CardLayout(0, 0));
			startWindow.setBackground(Color.LIGHT_GRAY);
			
			getContentPane().add(startWindow, "name_80350339024294");
			GridBagLayout gbl_startWindow = new GridBagLayout();
			gbl_startWindow.columnWidths = new int[] {100, 110, 90, 120, 120, 110, 100};
			gbl_startWindow.rowHeights = new int[] {100, 100, 61, 60, 60, 60, 100, 100, 0};
			gbl_startWindow.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			gbl_startWindow.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			startWindow.setLayout(gbl_startWindow);
			
			GridBagConstraints gbc_lblScan = new GridBagConstraints();
			gbc_lblScan.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblScan.insets = new Insets(0, 0, 5, 5);
			gbc_lblScan.gridx = 2;
			gbc_lblScan.gridy = 2;
			lblScan.setToolTipText("Scan the Adminstrator card to log in (optional)");
			startWindow.add(lblScan, gbc_lblScan);
			
			GridBagConstraints gbc_cardField = new GridBagConstraints();
			gbc_cardField.gridwidth = 2;
			gbc_cardField.insets = new Insets(0, 0, 5, 5);
			gbc_cardField.fill = GridBagConstraints.HORIZONTAL;
			gbc_cardField.gridx = 3;
			gbc_cardField.gridy = 2;
			startWindow.add(cardField, gbc_cardField);
			
			GridBagConstraints gbc_lblUsername = new GridBagConstraints();
			gbc_lblUsername.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsername.gridx = 2;
			gbc_lblUsername.gridy = 3;
			lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
			startWindow.add(lblUsername, gbc_lblUsername);
			
			GridBagConstraints gbc_userNameField = new GridBagConstraints();
			gbc_userNameField.gridwidth = 2;
			gbc_userNameField.insets = new Insets(0, 0, 5, 5);
			gbc_userNameField.fill = GridBagConstraints.HORIZONTAL;
			gbc_userNameField.gridx = 3;
			gbc_userNameField.gridy = 3;
			startWindow.add(userNameField, gbc_userNameField);
			
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.gridx = 2;
			gbc_lblPassword.gridy = 4;
			lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
			startWindow.add(lblPassword, gbc_lblPassword);
			
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.gridwidth = 2;
			gbc_passwordField.insets = new Insets(0, 0, 5, 5);
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 3;
			gbc_passwordField.gridy = 4;
			startWindow.add(passwordField, gbc_passwordField);
			
			GridBagConstraints gbc_btnLogin = new GridBagConstraints();
			gbc_btnLogin.fill = GridBagConstraints.BOTH;
			gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
			gbc_btnLogin.gridx = 3;
			gbc_btnLogin.gridy = 5;
			btnLogin.setForeground(Color.WHITE);
			btnLogin.setBackground(Color.BLACK);
			btnLogin.addActionListener(this);
			startWindow.add(btnLogin, gbc_btnLogin);
			
			GridBagConstraints gbc_btnExit = new GridBagConstraints();
			gbc_btnExit.fill = GridBagConstraints.BOTH;
			gbc_btnExit.insets = new Insets(0, 0, 5, 5);
			gbc_btnExit.gridx = 4;
			gbc_btnExit.gridy = 5;
			btnExit.setForeground(Color.WHITE);
			btnExit.setBackground(Color.BLACK);
			btnExit.addActionListener(this);
			startWindow.add(btnExit, gbc_btnExit);
			
			GridBagConstraints gbc_lblStatus = new GridBagConstraints();
			gbc_lblStatus.gridwidth = 2;
			gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
			gbc_lblStatus.gridx = 3;
			gbc_lblStatus.gridy = 6;
			startWindow.add(lblStatus, gbc_lblStatus);

			
			mainWindow.setBackground(Color.LIGHT_GRAY);
			mainWindow.setLayout(null);
			getContentPane().add(mainWindow, "name_80293382047698");
			placeOrder.setBackground(SystemColor.control);

			
			placeOrder.setBounds(0, 11, 113, 30);
			mainWindow.add(placeOrder);
			addItemByID.setBackground(SystemColor.control);

			
			addItemByID.setBounds(0, 52, 113, 30);
			addItemByID.addActionListener(this);
			mainWindow.add(addItemByID);
			showAllItems.setBackground(SystemColor.control);

			
			showAllItems.setBounds(0, 93, 113, 30);
			showAllItems.addActionListener(this);
			mainWindow.add(showAllItems);
			showAllUsers.setBackground(SystemColor.control);

			
			showAllUsers.setBounds(0, 134, 113, 30);
			mainWindow.add(showAllUsers);
			getItemByName.setBackground(SystemColor.control);

			
			getItemByName.setBounds(0, 178, 113, 30);
			getItemByName.addActionListener(this);
			mainWindow.add(getItemByName);
			btnNewButton_5.setBackground(SystemColor.control);

			
			btnNewButton_5.setBounds(0, 219, 113, 30);
			mainWindow.add(btnNewButton_5);
			btnNewButton_6.setBackground(SystemColor.control);

			
			btnNewButton_6.setBounds(0, 265, 113, 30);
			mainWindow.add(btnNewButton_6);

			
			textArea.setBackground(Color.WHITE);
			textArea.setColumns(3);
			textArea.setFont(new Font("Arial", Font.BOLD, 12));
			textArea.setEditable(false);
			scrollPane.setBounds(180, 11, 572, 539);
			mainWindow.add(scrollPane);
			scrollPane.setViewportView(textArea);
		}

	
		



	public static void main(String [] args) {
		InventoryGUI gui = new InventoryGUI();
		gui.setVisible(true);
	}
}
