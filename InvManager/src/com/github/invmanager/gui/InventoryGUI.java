package com.github.invmanager.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.text.DateFormatter;

import com.github.invmanager.dal.Items;
import com.github.invmanager.dal.ItemsDAOImpl;
import com.github.invmanager.dal.Users;
import com.github.invmanager.dal.UsersDAOImpl;

/*
 * @author Daniel Imparatelu
 * December 2017
 * This is the main interface of the program
 */

public class InventoryGUI extends JFrame implements ActionListener, Runnable {

	//using Sockets to receive the scanned input from the Android application
	//the following code only initialises the components
	private static Socket socket;
	private static ServerSocket serverSocket;
	private static InputStreamReader isr;
	private static BufferedReader br;
	private static String message;

	private static final long serialVersionUID = 1L;

	Items items = new Items();
	ItemsDAOImpl itemsDAO = new ItemsDAOImpl();

	Users users = new Users();
	UsersDAOImpl usersDAO = new UsersDAOImpl();
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource().equals(addItemByID)) {

			mainWindow.setVisible(false);
			addItemWindow.setVisible(true);
		}

		else if(e.getSource().equals(showAllItems)) {
			for(Items item: itemsDAO.getAllItems() ) {
				textArea.append("item: [ID = "+ item.getItemID()+", Name = "+item.getItemName()+", expiry date = "+item.getItemExpDate()+", last restocked = "+item.getitemLastRestocked()+"]"+"\n\n");
			}
		}

		else if(e.getSource().equals(placeOrder)) {

		}

		else if (e.getSource().equals(showAllUsers)) {

			for(Users users: usersDAO.getAllUsers() ) {
				System.out.println("user: [User Name = "+ users.getName()+", Password = "+users.getPassword()+", is admin = "+users.getIsAdmin()+"]");
			}
		}

		else if (e.getSource().equals(getItemByName)) {
			for(Items item: itemsDAO.getItemByName("TEST") ) {
				textArea.append("item: [ID = "+ item.getItemID()+", Name = "+item.getItemName()+", expiry date = "+item.getItemExpDate()+", last restocked = "+item.getitemLastRestocked()+"]"+"\n\n");
			}
		}

		//LOGIN METHODS
		else if (e.getSource().equals(btnLogin) || e.getSource().equals(passwordField) || e.getSource().equals(cardField)) {//login methods
			//overly complicated login method

			String inputUName = userNameField.getText();
			char[] inputCharField = passwordField.getPassword();//get text as a character array
			String inputPassField = new String(inputCharField);//converting the char input from the password field into a string so I can match it against the database value

			char[] inputCard = cardField.getPassword();
			String inputCardField = new String(inputCard);

			if((userNameField.getText().trim().length() == 0) && (passwordField.getPassword().length == 0) && (cardField.getPassword().length == 0)){
				//				lblStatus.setText("Please enter input");
				//				cardField.setText("");
				//				userNameField.setText("");
				//				passwordField.setText("");
			}

			//Username and password login statement
			else {
				for(Users user: usersDAO.retrieveUser(inputUName, inputPassField)) {//for each loop to retrieve results

					String dbUName = user.getName();//retrieves the username and password from the database
					String dbUPass = user.getPassword();

					if(inputUName.equals(dbUName) && (inputPassField.equals(dbUPass))) {//and compares them to the inputs
						startWindow.setVisible(false);
						mainWindow.setVisible(true);
					}
				}
				//Card scan login statement
				if(inputCardField.equals("%_Daniel40Imparatelu30_%")) {
					startWindow.setVisible(false);//hide the login screen
					mainWindow.setVisible(true);//show the main screen
				}
				else {
					lblStatus.setText("Incorrect input");
					cardField.setText("");
					userNameField.setText("");
					passwordField.setText("");
				}
			}
		}

		else if ((e.getSource().equals(textField)) || (e.getSource().equals(btnSearch))) {

			for(Items item: itemsDAO.getItemByName(textField.getText()) ) {

				textArea.append("item: [ID = "+ item.getItemID()+", Name = "+item.getItemName()+", expiry date = "
						+item.getItemExpDate()+", last restocked = "+item.getitemLastRestocked()+"]"+"\n\n");
			}
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
	private JScrollPane scrollPane = new JScrollPane();
	private JTextArea textArea = new JTextArea();
	private final static JPanel startWindow = new JPanel();
	private final static JPanel mainWindow = new JPanel();
	private final JButton btnLogin = new JButton("Login");
	private final JButton btnExit = new JButton("Exit");
	private final static JPasswordField cardField = new JPasswordField();
	private final static JTextField userNameField = new JTextField();
	private final static JPasswordField passwordField = new JPasswordField();
	private final JLabel lblScan = new JLabel("Scan/Swipe Card:");
	private final JLabel lblUsername = new JLabel("Username:");
	private final JLabel lblPassword = new JLabel("Password:");
	private final static JLabel lblStatus = new JLabel("");
	private JTextField textField;
	private final JButton btnSearch = new JButton("Search");
	private final JPanel addItemWindow = new JPanel();
	private final JLabel lblItemId = new JLabel("Item ID");
	private final JLabel lblItemName = new JLabel("Item Name");
	private final JLabel lblQuantity = new JLabel("Quantity");
	private final JLabel lblExpiryDate = new JLabel("Expiry date");
	private final JTextField idField = new JTextField();
	private final JTextField itemNameField = new JTextField();
	private final JTextField itemQtyField = new JTextField();
	 DateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
     DateFormatter df = new DateFormatter(format);
	private final JFormattedTextField itemExpDateField = new JFormattedTextField(df);
	
	private final JButton btnAdd = new JButton("Add");
	private final JButton btnBack = new JButton("Back");
	private final JLabel lblLastRestocked = new JLabel("Last restocked");
	private final JTextField itemLastRestocked = new JTextField();

	

	public InventoryGUI() {

		super();
		itemLastRestocked.setColumns(10);
		itemExpDateField.setColumns(10);
		itemQtyField.setColumns(10);
		itemNameField.setColumns(10);
		idField.setColumns(10);
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
		cardField.addActionListener(this);
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
		passwordField.addActionListener(this);
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
		getContentPane().add(mainWindow, "name_80293382047698");
		GridBagLayout gbl_mainWindow = new GridBagLayout();
		gbl_mainWindow.columnWidths = new int[] {64, 155, 120, 140, 98, 103, 0};
		gbl_mainWindow.rowHeights = new int[]{64, 30, 30, 30, 30, 30, 30, 32, 32, 0, 0, 0, 0};
		gbl_mainWindow.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_mainWindow.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		mainWindow.setLayout(gbl_mainWindow);

		textArea.setBackground(Color.WHITE);
		textArea.setColumns(3);
		textArea.setFont(new Font("Arial", Font.BOLD, 12));
		textArea.setEditable(false);
		scrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 0, 0), new Color(64, 64, 64)));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridheight = 10;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		mainWindow.add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(textArea);

		placeOrder.setBackground(SystemColor.control);
		GridBagConstraints gbc_placeOrder = new GridBagConstraints();
		gbc_placeOrder.fill = GridBagConstraints.BOTH;
		gbc_placeOrder.insets = new Insets(0, 0, 5, 5);
		gbc_placeOrder.gridx = 4;
		gbc_placeOrder.gridy = 1;
		mainWindow.add(placeOrder, gbc_placeOrder);
		addItemByID.setBackground(SystemColor.control);
		addItemByID.addActionListener(this);
		GridBagConstraints gbc_addItemByID = new GridBagConstraints();
		gbc_addItemByID.fill = GridBagConstraints.BOTH;
		gbc_addItemByID.insets = new Insets(0, 0, 5, 5);
		gbc_addItemByID.gridx = 4;
		gbc_addItemByID.gridy = 2;
		mainWindow.add(addItemByID, gbc_addItemByID);
		showAllItems.setBackground(SystemColor.control);
		showAllItems.addActionListener(this);
		GridBagConstraints gbc_showAllItems = new GridBagConstraints();
		gbc_showAllItems.fill = GridBagConstraints.BOTH;
		gbc_showAllItems.insets = new Insets(0, 0, 5, 5);
		gbc_showAllItems.gridx = 4;
		gbc_showAllItems.gridy = 3;
		mainWindow.add(showAllItems, gbc_showAllItems);
		showAllUsers.setBackground(SystemColor.control);
		GridBagConstraints gbc_showAllUsers = new GridBagConstraints();
		gbc_showAllUsers.fill = GridBagConstraints.BOTH;
		gbc_showAllUsers.insets = new Insets(0, 0, 5, 5);
		gbc_showAllUsers.gridx = 4;
		gbc_showAllUsers.gridy = 4;
		showAllUsers.addActionListener(this);
		mainWindow.add(showAllUsers, gbc_showAllUsers);
		getItemByName.setBackground(SystemColor.control);
		getItemByName.addActionListener(this);
		GridBagConstraints gbc_getItemByName = new GridBagConstraints();
		gbc_getItemByName.fill = GridBagConstraints.BOTH;
		gbc_getItemByName.insets = new Insets(0, 0, 5, 5);
		gbc_getItemByName.gridx = 4;
		gbc_getItemByName.gridy = 5;
		mainWindow.add(getItemByName, gbc_getItemByName);
		btnNewButton_5.setBackground(SystemColor.control);
		GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
		gbc_btnNewButton_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton_5.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton_5.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_5.gridx = 4;
		gbc_btnNewButton_5.gridy = 6;
		mainWindow.add(btnNewButton_5, gbc_btnNewButton_5);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 11;
		mainWindow.add(textField, gbc_textField);
		textField.setColumns(10);
		textField.addActionListener(this);

		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 3;
		gbc_btnSearch.gridy = 11;
		btnSearch.addActionListener(this);
		btnSearch.setBackground(Color.WHITE);

		mainWindow.add(btnSearch, gbc_btnSearch);
		addItemWindow.setBackground(Color.LIGHT_GRAY);

		getContentPane().add(addItemWindow, "name_9138862488992");
		GridBagLayout gbl_addItemWindow = new GridBagLayout();
		gbl_addItemWindow.columnWidths = new int[] {120, 60, 118, 120, 120, 120, 111, 0};
		gbl_addItemWindow.rowHeights = new int[] {60, 80, 30, 30, 30, 30, 0, 6, 117, 145, 0};
		gbl_addItemWindow.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_addItemWindow.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		addItemWindow.setLayout(gbl_addItemWindow);
		
		GridBagConstraints gbc_lblItemId = new GridBagConstraints();
		gbc_lblItemId.insets = new Insets(0, 0, 5, 5);
		gbc_lblItemId.gridx = 2;
		gbc_lblItemId.gridy = 2;
		addItemWindow.add(lblItemId, gbc_lblItemId);
		
		GridBagConstraints gbc_idField = new GridBagConstraints();
		gbc_idField.gridwidth = 2;
		gbc_idField.insets = new Insets(0, 0, 5, 5);
		gbc_idField.fill = GridBagConstraints.HORIZONTAL;
		gbc_idField.gridx = 3;
		gbc_idField.gridy = 2;
		addItemWindow.add(idField, gbc_idField);
		
		GridBagConstraints gbc_lblItemName = new GridBagConstraints();
		gbc_lblItemName.insets = new Insets(0, 0, 5, 5);
		gbc_lblItemName.gridx = 2;
		gbc_lblItemName.gridy = 3;
		addItemWindow.add(lblItemName, gbc_lblItemName);
		
		GridBagConstraints gbc_itemNameField = new GridBagConstraints();
		gbc_itemNameField.gridwidth = 2;
		gbc_itemNameField.insets = new Insets(0, 0, 5, 5);
		gbc_itemNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemNameField.gridx = 3;
		gbc_itemNameField.gridy = 3;
		addItemWindow.add(itemNameField, gbc_itemNameField);
		
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 2;
		gbc_lblQuantity.gridy = 4;
		addItemWindow.add(lblQuantity, gbc_lblQuantity);
		
		GridBagConstraints gbc_itemQtyField = new GridBagConstraints();
		gbc_itemQtyField.gridwidth = 2;
		gbc_itemQtyField.insets = new Insets(0, 0, 5, 5);
		gbc_itemQtyField.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemQtyField.gridx = 3;
		gbc_itemQtyField.gridy = 4;
		addItemWindow.add(itemQtyField, gbc_itemQtyField);
		
		GridBagConstraints gbc_lblExpiryDate = new GridBagConstraints();
		gbc_lblExpiryDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblExpiryDate.gridx = 2;
		gbc_lblExpiryDate.gridy = 5;
		addItemWindow.add(lblExpiryDate, gbc_lblExpiryDate);
		
		GridBagConstraints gbc_itemExpDateField = new GridBagConstraints();
		gbc_itemExpDateField.gridwidth = 2;
		gbc_itemExpDateField.insets = new Insets(0, 0, 5, 5);
		gbc_itemExpDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemExpDateField.gridx = 3;
		gbc_itemExpDateField.gridy = 5;
		itemExpDateField.setValue(new Date());
		addItemWindow.add(itemExpDateField, gbc_itemExpDateField);
		
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 3;
		gbc_btnAdd.gridy = 7;
		btnAdd.setBackground(Color.WHITE);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addItemToDb();
			}
			
		});
		
		GridBagConstraints gbc_lblLastRestocked = new GridBagConstraints();
		gbc_lblLastRestocked.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastRestocked.gridx = 2;
		gbc_lblLastRestocked.gridy = 6;
		addItemWindow.add(lblLastRestocked, gbc_lblLastRestocked);
		
		GridBagConstraints gbc_itemLastRestocked = new GridBagConstraints();
		gbc_itemLastRestocked.gridwidth = 2;
		gbc_itemLastRestocked.insets = new Insets(0, 0, 5, 5);
		gbc_itemLastRestocked.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemLastRestocked.gridx = 3;
		gbc_itemLastRestocked.gridy = 6;
		addItemWindow.add(itemLastRestocked, gbc_itemLastRestocked);
		addItemWindow.add(btnAdd, gbc_btnAdd);
		
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 4;
		gbc_btnBack.gridy = 7;
		btnBack.setBackground(Color.WHITE);
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addItemWindow.setVisible(false);
				mainWindow.setVisible(true);
			}
			
		});
		addItemWindow.add(btnBack, gbc_btnBack);
	}
	
	public void addItemToDb() {
		
		int itemID = Integer.valueOf(idField.getText());
		String itemName = itemNameField.getText();
		String itemExpDate = itemExpDateField.getText();
		String itemLastRestocked2 = itemLastRestocked.getText();
		int itemQty = Integer.valueOf(itemQtyField.getText());
		
		Items item = new Items();
		item.setItemID(itemID);
		item.setItemName(itemName);
		item.setItemExpDate(itemExpDate);
		item.setItemLastRestocked(itemLastRestocked2);
		item.setItemQty(itemQty);
		itemsDAO.addItem(item);
	}


	public void run() {//method implemented by the Runnable interface

		try {
			Robot robot = new Robot();//new instance of Robot class to simulate key presses
			do {
				//robot.keyPress(KeyEvent.VK_ENTER);//simulate Enter press to login
				Thread.sleep(500);
			}
			while(!mainWindow.isVisible());//when the main window is visible the thread stops

		} catch (Exception e) {
			System.out.println("Thread interrupted.");
		}
		System.out.println("Thread exiting.");
	}

	public static void main(String [] args) {
		InventoryGUI gui = new InventoryGUI();
		gui.setVisible(true);


		Thread t = new Thread(gui);
		t.start();//start the runnable process

		try//open the server so the Android app can connect
		{
			serverSocket = new ServerSocket(7800);//create a new server on port 7800
			System.out.println("Socket server open");
			while(true) {//infinite loop so that the server stays open
				socket = serverSocket.accept();//wait until a client connects
				isr = new InputStreamReader(socket.getInputStream());//get the value of the scan
				br = new BufferedReader(isr);//read the input using a Buffered Reader
				message = br.readLine();//and convert it to a String object
				System.out.println(message);
				cardField.setText(message);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
}


