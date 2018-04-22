package com.github.invmanager.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.github.invmanager.dal.Items;
import com.github.invmanager.dal.ItemsDAOImpl;
import com.github.invmanager.dal.Users;
import com.github.invmanager.dal.UsersDAOImpl;
import com.github.invmanager.util.ItemPrediction;

/*
 * @author Daniel Imparatelu
 * December 2017
 * This is the main interface of the program
 */

public class InventoryGUI extends JFrame implements ActionListener {

	// using Sockets to receive the scanned input from the Android application
	// the following code only initialises the components
	private static Socket socket;
	private static ServerSocket serverSocket;
	private static InputStreamReader isr;
	private static BufferedReader br;
	public static String message;

	private static final long serialVersionUID = 1L;

	Items items = new Items();
	static ItemsDAOImpl itemsDAO = new ItemsDAOImpl();

	Users users = new Users();
	UsersDAOImpl usersDAO = new UsersDAOImpl();

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(addItemByID)) {

			mainWindow.setVisible(false);
			addItemWindow.setVisible(true);
		}

		else if (e.getSource().equals(showAllItems)) {
			textArea.setText("");
			for (Items item : itemsDAO.getAllItems()) {

				textArea.append("ITEM: " + "\n" + "ID: " + item.getItemID() + "\n"+
						"Name: " + item.getItemName() +"\n"+ "Quantity: "+item.getItemQty()+ "\n\n\n");
			}
		}

		else if (e.getSource().equals(placeOrder)) {

		}

		else if (e.getSource().equals(showAllUsers)) {

			textArea.setText("");
			for (Users users : usersDAO.getAllUsers()) {
				String password = users.getPassword();
				textArea.append("User Name = " + users.getName() + ", Password = " + password.replaceAll("\\S", "*")
				+ ", is admin = " + users.getIsAdmin());
			}
		}

		// LOGIN METHODS
		else if (e.getSource().equals(btnLogin) || e.getSource().equals(passwordField)
				|| e.getSource().equals(cardField)) {// login methods
			// overly complicated login method

			String inputUName = userNameField.getText();
			char[] inputCharField = passwordField.getPassword();// get text as a character array
			String inputPassField = new String(inputCharField);// converting the char input from the password field into
			// a string so I can match it against the database value

			char[] inputCard = cardField.getPassword();
			String inputCardField = new String(inputCard);

			if ((userNameField.getText().trim().length() == 0) && (passwordField.getPassword().length == 0)
					&& (cardField.getPassword().length == 0)) {
				// lblStatus.setText("Please enter input");
				// cardField.setText("");
				// userNameField.setText("");
				// passwordField.setText("");
			}

			// Username and password login statement
			else {
				for (Users user : usersDAO.retrieveUser(inputUName, inputPassField)) {// for each loop to retrieve
					// results
					String dbUName = user.getName();// retrieves the username and password from the database
					String dbUPass = user.getPassword();

					if (inputUName.equals(dbUName) && (inputPassField.equals(dbUPass))) {// and compares them to the
						// inputs
						startWindow.setVisible(false);
						mainWindow.setVisible(true);
					}
				}
				// Card scan login statement
				if (inputCardField.equals("password")) {
					startWindow.setVisible(false);// hide the login screen
					mainWindow.setVisible(true);// show the main screen
				} else {
					lblStatus.setText("Incorrect input");
					cardField.setText("");
					userNameField.setText("");
					passwordField.setText("");
				}
			}
		}

		else if ((e.getSource().equals(textField)) || (e.getSource().equals(btnSearch))) {

			textArea.setText("");
			for (Items item : itemsDAO.getItemByName(textField.getText())) {
				textArea.append("ITEM: " + "\n" + "ID: " + item.getItemID() + "\n"+
						"Name: " + item.getItemName() +"\n"+ "Quantity: "+item.getItemQty()+ "\n\n\n");
			}
		}

		else if (e.getSource().equals(btnExit)) {
			System.exit(0);
		}

	}

	private JButton placeOrder = new JButton("Place Order");
	private JButton addItemByID = new JButton("Add Item");
	private JButton showAllItems = new JButton("Show All Items");
	private JButton showAllUsers = new JButton("Show Users");
	private JButton receiptWindowBtn = new JButton("Receipt Window");
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
	private final static JTextField idField = new JTextField();
	private final static JTextField itemNameField = new JTextField();
	private final static JTextField itemQtyField = new JTextField();
	private final static JFormattedTextField itemExpDateField = new JFormattedTextField();
	private final JButton btnAdd = new JButton("Add");
	private final JButton btnBack = new JButton("Back");
	private final static JTextField itemLastRestocked = new JTextField();
	private final static JPanel receiptWindow = new JPanel();
	private final static JTextArea receiptText = new JTextArea();
	private final static JButton btnReceipt = new JButton("Receipt");
	private final JScrollPane receiptScrollPane = new JScrollPane();
	private final JLabel lblReceiptWindow = new JLabel("                                  Receipt Window");
	private final JButton btnBack_1 = new JButton("Back");

	static String scannedItemName;
	static int scannedItemQty;
	static String scannedItemID;
	private final JLabel lblWelcome = new JLabel("    Inventory Manager");
	private final JLabel lblAddItems = new JLabel("        Add Items");
	private final static JLabel lblInfo = new JLabel("");
	private final JLabel lblInfo2 = new JLabel("");

	public InventoryGUI() {
		// auto-generated constructor
		// a lot of code because of GridBagLayout

		super();
		
		itemLastRestocked.setColumns(10);
		itemExpDateField.setColumns(10);
		userNameField.setColumns(10);
		setSize(new Dimension(800, 600));
		getContentPane().setBackground(new Color(33, 182, 168));
		setTitle("Inventory Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new CardLayout(0, 0));
		startWindow.setBackground(new Color(16, 174, 238));

		getContentPane().add(startWindow, "name_80350339024294");
		GridBagLayout gbl_startWindow = new GridBagLayout();
		gbl_startWindow.columnWidths = new int[] {80, 80, 90, 150, 150, 100, 100};
		gbl_startWindow.rowHeights = new int[] { 80, 100, 61, 60, 60, 60, 100, 0 };
		gbl_startWindow.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_startWindow.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		startWindow.setLayout(gbl_startWindow);
		
		GridBagConstraints gbc_lblWelcome = new GridBagConstraints();
		gbc_lblWelcome.fill = GridBagConstraints.BOTH;
		lblWelcome.setFont(new Font("Times New Roman", Font.PLAIN, 31));
		lblWelcome.setOpaque(true);
		lblWelcome.setBackground(new Color(63,81,181));
		lblWelcome.setForeground(Color.WHITE);
		gbc_lblWelcome.gridwidth = 2;
		gbc_lblWelcome.insets = new Insets(0, 0, 5, 5);
		gbc_lblWelcome.gridx = 3;
		gbc_lblWelcome.gridy = 1;
		startWindow.add(lblWelcome, gbc_lblWelcome);

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
		btnLogin.setBackground(new Color(63, 81, 181));
		btnLogin.addActionListener(this);
		startWindow.add(btnLogin, gbc_btnLogin);

		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.fill = GridBagConstraints.BOTH;
		gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		gbc_btnExit.gridx = 4;
		gbc_btnExit.gridy = 5;
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(new Color(63,81,181));
		btnExit.addActionListener(this);
		startWindow.add(btnExit, gbc_btnExit);

		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.gridwidth = 2;
		gbc_lblStatus.insets = new Insets(0, 0, 0, 5);
		gbc_lblStatus.gridx = 3;
		gbc_lblStatus.gridy = 6;
		startWindow.add(lblStatus, gbc_lblStatus);

		mainWindow.setBackground(new Color(16, 174, 238));
		getContentPane().add(mainWindow, "name_80293382047698");
		GridBagLayout gbl_mainWindow = new GridBagLayout();
		gbl_mainWindow.columnWidths = new int[] {80, 40, 155, 120, 170, 100, 80, 40, 0};
		gbl_mainWindow.rowHeights = new int[] { 64, 30, 30, 30, 30, 30, 30, 32, 32, 0, 30, 30, 30, 0 };
		gbl_mainWindow.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_mainWindow.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		mainWindow.setLayout(gbl_mainWindow);

		textArea.setBackground(Color.WHITE);
		textArea.setColumns(3);
		textArea.setFont(new Font("Arial", Font.BOLD, 12));
		textArea.setEditable(false);
		scrollPane
		.setViewportBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 0, 0), new Color(64, 64, 64)));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridheight = 10;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		mainWindow.add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(textArea);
		placeOrder.setForeground(Color.WHITE);
		placeOrder.setToolTipText("Not implemented yet");

		placeOrder.setBackground(new Color(63,81,181));
		GridBagConstraints gbc_placeOrder = new GridBagConstraints();
		gbc_placeOrder.fill = GridBagConstraints.BOTH;
		gbc_placeOrder.insets = new Insets(0, 0, 5, 5);
		gbc_placeOrder.gridx = 5;
		gbc_placeOrder.gridy = 1;
		mainWindow.add(placeOrder, gbc_placeOrder);
		addItemByID.setForeground(Color.WHITE);
		addItemByID.setBackground(new Color(63,81,181));
		addItemByID.addActionListener(this);
		GridBagConstraints gbc_addItemByID = new GridBagConstraints();
		gbc_addItemByID.fill = GridBagConstraints.BOTH;
		gbc_addItemByID.insets = new Insets(0, 0, 5, 5);
		gbc_addItemByID.gridx = 5;
		gbc_addItemByID.gridy = 2;
		mainWindow.add(addItemByID, gbc_addItemByID);
		showAllItems.setForeground(Color.WHITE);
		showAllItems.setBackground(new Color(63,81,181));
		showAllItems.addActionListener(this);
		GridBagConstraints gbc_showAllItems = new GridBagConstraints();
		gbc_showAllItems.fill = GridBagConstraints.BOTH;
		gbc_showAllItems.insets = new Insets(0, 0, 5, 5);
		gbc_showAllItems.gridx = 5;
		gbc_showAllItems.gridy = 3;
		mainWindow.add(showAllItems, gbc_showAllItems);
		showAllUsers.setForeground(Color.WHITE);
		showAllUsers.setBackground(new Color(63,81,181));
		GridBagConstraints gbc_showAllUsers = new GridBagConstraints();
		gbc_showAllUsers.fill = GridBagConstraints.BOTH;
		gbc_showAllUsers.insets = new Insets(0, 0, 5, 5);
		gbc_showAllUsers.gridx = 5;
		gbc_showAllUsers.gridy = 4;
		showAllUsers.addActionListener(this);
		mainWindow.add(showAllUsers, gbc_showAllUsers);
		receiptWindowBtn.setForeground(Color.WHITE);

		receiptWindowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.setVisible(false);
				receiptWindow.setVisible(true);
			}
		});
		receiptWindowBtn.setBackground(new Color(63,81,181));
		GridBagConstraints gbc_receiptWindowBtn = new GridBagConstraints();
		gbc_receiptWindowBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_receiptWindowBtn.anchor = GridBagConstraints.NORTH;
		gbc_receiptWindowBtn.insets = new Insets(0, 0, 5, 5);
		gbc_receiptWindowBtn.gridx = 5;
		gbc_receiptWindowBtn.gridy = 5;
		mainWindow.add(receiptWindowBtn, gbc_receiptWindowBtn);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 11;
		mainWindow.add(textField, gbc_textField);
		textField.setColumns(10);
		textField.addActionListener(this);

		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 4;
		gbc_btnSearch.gridy = 11;
		btnSearch.setForeground(Color.WHITE);
		btnSearch.addActionListener(this);
		btnSearch.setBackground(new Color(63,81,181));

		mainWindow.add(btnSearch, gbc_btnSearch);
		
		GridBagConstraints gbc_lblInfo = new GridBagConstraints();
		gbc_lblInfo.gridwidth = 3;
		gbc_lblInfo.insets = new Insets(0, 0, 0, 5);
		gbc_lblInfo.gridx = 2;
		gbc_lblInfo.gridy = 12;
		mainWindow.add(lblInfo, gbc_lblInfo);
		addItemWindow.setBackground(new Color(16, 174, 238));

		getContentPane().add(addItemWindow, "name_9138862488992");
		GridBagLayout gbl_addItemWindow = new GridBagLayout();
		gbl_addItemWindow.columnWidths = new int[] { 120, 60, 118, 120, 120, 120, 111, 0 };
		gbl_addItemWindow.rowHeights = new int[] { 60, 80, 70, 30, 30, 30, 30, 30, 99, 104, 0 };
		gbl_addItemWindow.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_addItemWindow.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		addItemWindow.setLayout(gbl_addItemWindow);
		
		GridBagConstraints gbc_lblAddItems = new GridBagConstraints();
		lblAddItems.setBackground(new Color(63,81,181));
		gbc_lblAddItems.fill = GridBagConstraints.BOTH;
		gbc_lblAddItems.gridwidth = 2;
		gbc_lblAddItems.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddItems.gridx = 3;
		gbc_lblAddItems.gridy = 1;
		lblAddItems.setForeground(Color.WHITE);
		lblAddItems.setFont(new Font("Times New Roman", Font.PLAIN, 31));
		lblAddItems.setOpaque(true);
		addItemWindow.add(lblAddItems, gbc_lblAddItems);

		GridBagConstraints gbc_lblItemId = new GridBagConstraints();
		gbc_lblItemId.insets = new Insets(0, 0, 5, 5);
		gbc_lblItemId.gridx = 2;
		gbc_lblItemId.gridy = 3;
		addItemWindow.add(lblItemId, gbc_lblItemId);
		idField.setColumns(10);

		GridBagConstraints gbc_idField = new GridBagConstraints();
		gbc_idField.gridwidth = 2;
		gbc_idField.insets = new Insets(0, 0, 5, 5);
		gbc_idField.fill = GridBagConstraints.HORIZONTAL;
		gbc_idField.gridx = 3;
		gbc_idField.gridy = 3;
		addItemWindow.add(idField, gbc_idField);


		GridBagConstraints gbc_lblItemName = new GridBagConstraints();
		gbc_lblItemName.insets = new Insets(0, 0, 5, 5);
		gbc_lblItemName.gridx = 2;
		gbc_lblItemName.gridy = 4;
		addItemWindow.add(lblItemName, gbc_lblItemName);
		itemNameField.setColumns(10);

		GridBagConstraints gbc_itemNameField = new GridBagConstraints();
		gbc_itemNameField.gridwidth = 2;
		gbc_itemNameField.insets = new Insets(0, 0, 5, 5);
		gbc_itemNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemNameField.gridx = 3;
		gbc_itemNameField.gridy = 4;
		addItemWindow.add(itemNameField, gbc_itemNameField);

		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantity.gridx = 2;
		gbc_lblQuantity.gridy = 5;
		addItemWindow.add(lblQuantity, gbc_lblQuantity);
		itemQtyField.setColumns(10);

		GridBagConstraints gbc_itemQtyField = new GridBagConstraints();
		gbc_itemQtyField.gridwidth = 2;
		gbc_itemQtyField.insets = new Insets(0, 0, 5, 5);
		gbc_itemQtyField.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemQtyField.gridx = 3;
		gbc_itemQtyField.gridy = 5;
		addItemWindow.add(itemQtyField, gbc_itemQtyField);
		
				GridBagConstraints gbc_btnAdd = new GridBagConstraints();
				gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
				gbc_btnAdd.gridx = 3;
				gbc_btnAdd.gridy = 6;
				btnAdd.setForeground(Color.WHITE);
				btnAdd.setBackground(new Color(63,81,181));
				btnAdd.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							addItemToDb();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});
				addItemWindow.add(btnAdd, gbc_btnAdd);
		
				GridBagConstraints gbc_btnBack = new GridBagConstraints();
				gbc_btnBack.fill = GridBagConstraints.HORIZONTAL;
				gbc_btnBack.insets = new Insets(0, 0, 5, 5);
				gbc_btnBack.gridx = 4;
				gbc_btnBack.gridy = 6;
				btnBack.setForeground(Color.WHITE);
				btnBack.setBackground(new Color(63,81,181));
				btnBack.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						addItemWindow.setVisible(false);
						mainWindow.setVisible(true);
					}

				});
				addItemWindow.add(btnBack, gbc_btnBack);
				
				GridBagConstraints gbc_lblInfo2 = new GridBagConstraints();
				gbc_lblInfo2.gridwidth = 2;
				gbc_lblInfo2.insets = new Insets(0, 0, 5, 5);
				gbc_lblInfo2.gridx = 3;
				gbc_lblInfo2.gridy = 7;
				addItemWindow.add(lblInfo2, gbc_lblInfo2);
		receiptWindow.setBackground(new Color(16,174,238));

		getContentPane().add(receiptWindow, "name_75929262704408");
		GridBagLayout gbl_receiptWindow = new GridBagLayout();
		gbl_receiptWindow.columnWidths = new int[] {100, 100, 383, 100, 100, 0};
		gbl_receiptWindow.rowHeights = new int[] { 30, 39, 0, 30, 30, 39, 0 };
		gbl_receiptWindow.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_receiptWindow.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		receiptWindow.setLayout(gbl_receiptWindow);

		GridBagConstraints gbc_lblReceiptWindow = new GridBagConstraints();
		gbc_lblReceiptWindow.fill = GridBagConstraints.BOTH;
		lblReceiptWindow.setBackground(new Color(63,81,181));
		gbc_lblReceiptWindow.insets = new Insets(0, 0, 5, 5);
		gbc_lblReceiptWindow.gridx = 2;
		gbc_lblReceiptWindow.gridy = 1;
		lblReceiptWindow.setOpaque(true);
		lblReceiptWindow.setForeground(Color.WHITE);
		lblReceiptWindow.setFont(new Font("Arial", Font.PLAIN, 15));
		receiptWindow.add(lblReceiptWindow, gbc_lblReceiptWindow);

		GridBagConstraints gbc_receiptScrollPane = new GridBagConstraints();
		gbc_receiptScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_receiptScrollPane.fill = GridBagConstraints.BOTH;
		gbc_receiptScrollPane.gridx = 2;
		gbc_receiptScrollPane.gridy = 2;
		receiptWindow.add(receiptScrollPane, gbc_receiptScrollPane);

		receiptScrollPane.setViewportView(receiptText);

		GridBagConstraints gbc_receiptText = new GridBagConstraints();
		gbc_receiptText.gridwidth = 2;
		gbc_receiptText.insets = new Insets(0, 0, 5, 5);
		gbc_receiptText.fill = GridBagConstraints.BOTH;
		gbc_receiptText.gridx = 1;
		gbc_receiptText.gridy = 1;
		receiptText.setEditable(false);
		// receiptWindow.add(receiptText, gbc_receiptText);
		// receiptScrollPane).setViewportView(receiptText);

		GridBagConstraints gbc_btnReceipt = new GridBagConstraints();
		gbc_btnReceipt.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnReceipt.insets = new Insets(0, 0, 5, 5);
		gbc_btnReceipt.gridx = 2;
		gbc_btnReceipt.gridy = 3;
		btnReceipt.setForeground(Color.WHITE);
		btnReceipt.setBackground(new Color(63,81,181));
		btnReceipt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				receiptText.setText("");
				JOptionPane.showMessageDialog(null, "Receipt Printed");
				//itemsDAO.deleteItems(itemID);

			}

		});



		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 2;
		gbc_table.gridy = 2;
		//	receiptWindow.add(table, gbc_table);

		receiptWindow.add(btnReceipt, gbc_btnReceipt);

		GridBagConstraints gbc_btnBack_1 = new GridBagConstraints();
		btnBack_1.setForeground(Color.WHITE);
		btnBack_1.setBackground(new Color(63,81,181));
		gbc_btnBack_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBack_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack_1.gridx = 2;
		gbc_btnBack_1.gridy = 4;
		btnBack_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				receiptWindow.setVisible(false);
				mainWindow.setVisible(true);
			}

		});
		receiptWindow.add(btnBack_1, gbc_btnBack_1);
	}

	public void addItemToDb() throws Exception {//manually add items to database

		String itemID = idField.getText();
		String itemName = itemNameField.getText();
		int itemQty = Integer.valueOf(itemQtyField.getText());

		Items item = new Items();
		item.setItemID(itemID);
		item.setItemName(itemName);
		item.setItemQty(itemQty);
		itemsDAO.addItem(item);
		idField.setText("");
		itemNameField.setText("");
		itemQtyField.setText("");
		lblInfo2.setText("Item added");
	}

	public static void main(String[] args) {
		InventoryGUI gui = new InventoryGUI();

		gui.setVisible(true);
		Items scannedItem = new Items();

		try// open the server so the Android app can connect
		{
			serverSocket = new ServerSocket(7800);// create a new server on port 7800
			System.out.println("Socket server open");
			while (true) {

				/*
				 * Use Java NMAP nmap4j.sourceforge.net API to check what ports are open on local
				 * connection and return java objects use instead of hardcoded port
				 */
				socket = serverSocket.accept();// wait until a client connects

				isr = new InputStreamReader(socket.getInputStream());// get the value of the scan
				br = new BufferedReader(isr);// read the input using a Buffered Reader
				message = br.readLine();// and convert it to a String object

				final String tempid = message;//this is the message received from the Android app

				//add / update items
				if (message.matches("[0-9]+")) {// check if the message is a number(barcode) and not any other text
					scannedItem.setItemID(tempid);//the first message received is the barcode number
				}
				else {

					if (message.equals("password")) {//login statement
						cardField.setText(message);
						startWindow.setVisible(false);// hide the login screen
						mainWindow.setVisible(true);// show the main screen
					}
					else {
						ItemPrediction p = new ItemPrediction();
						//p.execute();
						//maybe make a button which shows the prediction of the last item scanned
						scannedItem.setItemName(tempid);//the second message received is the item name, assuming it was found in the database
						if(itemsDAO.getItemById(tempid) == false && itemsDAO.getItemByName(tempid).isEmpty()) {//if the item does not exist in the local database
							itemsDAO.addItem(scannedItem);//it gets added to it
							lblInfo.setText("Item has been added");
						}
						else{//if it does exist
							itemsDAO.updateItems(scannedItem);//the quantity of it gets incremented by 1
							lblInfo.setText("Item has been updated");
							
						}
					}
				}

				if(receiptWindow.isVisible()){
					// The following code removes items from the db as long as the receipt window is open
					receiptText.append(message+"\n");
					int qty = itemsDAO.getQtyById(scannedItem.getItemID());//get the quantity of a product by its id

					if(qty<=1) {
						itemsDAO.deleteItems(scannedItem.getItemID());//delete item by id
					}
					else if (qty>1){
						itemsDAO.updateItemsRemoved(scannedItem.getItemID());//remove 1 item from the quantity
					}
					else {
						System.out.println("err");
					}
				}
				else if(mainWindow.isVisible()) {
					
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
