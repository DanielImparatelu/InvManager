package ac.uk.invmanager.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.UIManager;
//import javax.swing.AbstractAction;

/*
 * @author Daniel Imparatelu
 * December 2017
 * This is the main interface of the program
 */

public class InventoryGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;


	public void actionPerformed(ActionEvent e) {

		if(e.getSource().equals(addItemByID)) {
			
		}
		
		else if(e.getSource().equals(showAllItems)) {
			
		}
		
		else if(e.getSource().equals(placeOrder)) {
			
		}
	}
	
	JButton placeOrder = new JButton("Place Order");
	JButton addItemByID = new JButton("Add Item");
	JButton showAllItems = new JButton("Show All");
	JButton btnNewButton_3 = new JButton("New button");
	JButton btnNewButton_4 = new JButton("New button");
	JButton btnNewButton_5 = new JButton("New button");
	JButton btnNewButton_6 = new JButton("New button");


		public InventoryGUI() {

			super();
			setSize(new Dimension(800, 600));
			getContentPane().setBackground(Color.LIGHT_GRAY);
			setTitle("Inventory Management");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{114, 310, 0};
			gridBagLayout.rowHeights = new int[]{260, 0, 0};
			gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			getContentPane().setLayout(gridBagLayout);

			JPanel panel = new JPanel();
			panel.setBackground(Color.LIGHT_GRAY);
			panel.setLayout(null);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridy = 0;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.insets = new Insets(10, 10, 10, 10);
			gbc_panel.gridx = 0;
			getContentPane().add(panel, gbc_panel);
			placeOrder.setBackground(SystemColor.control);

			
			placeOrder.setBounds(0, 11, 113, 30);
			panel.add(placeOrder);
			addItemByID.setBackground(SystemColor.control);

			
			addItemByID.setBounds(0, 52, 113, 30);
			panel.add(addItemByID);
			showAllItems.setBackground(SystemColor.control);

			
			showAllItems.setBounds(0, 93, 113, 30);
			panel.add(showAllItems);
			btnNewButton_3.setBackground(SystemColor.control);

			
			btnNewButton_3.setBounds(0, 134, 113, 30);
			panel.add(btnNewButton_3);
			btnNewButton_4.setBackground(SystemColor.control);

			
			btnNewButton_4.setBounds(0, 178, 113, 30);
			panel.add(btnNewButton_4);
			btnNewButton_5.setBackground(SystemColor.control);

			
			btnNewButton_5.setBounds(0, 219, 113, 30);
			panel.add(btnNewButton_5);
			btnNewButton_6.setBackground(SystemColor.control);

			
			btnNewButton_6.setBounds(0, 265, 113, 30);
			panel.add(btnNewButton_6);

			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.insets = new Insets(10, 10, 10, 10);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 1;
			gbc_scrollPane.gridy = 0;
			getContentPane().add(scrollPane, gbc_scrollPane);

			JTextArea textArea = new JTextArea();
			textArea.setBackground(Color.WHITE);
			textArea.setColumns(3);
			textArea.setFont(new Font("Arial", Font.BOLD, 12));
			textArea.setEditable(false);
			scrollPane.setViewportView(textArea);
		}

	
		



	public static void main(String [] args) {
		InventoryGUI gui = new InventoryGUI();
		gui.setVisible(true);
	}
}
