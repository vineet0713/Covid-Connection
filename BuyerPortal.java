// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// BuyerPortal.java
// This JPanel displays the buyer portal of the application 'Covid Connection'.

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuyerPortal extends JPanel {
	private String[] REQUEST_COLUMNS = {"Category", "Quantity", "Location"};
	private String[] RESPONSE_COLUMNS = {"Category", "Quantity", "Location", "Price", "Supplier"};
	private String[] NEW_ROW = {"Click to Select Category", "Enter Quantity", "Click to Select Location"};
	private String[] CATEGORIES = {"Masks", "Ventilators", "Pills", "Wheelchairs"};
	private String[] LOCATIONS = {"Cupertino", "Santa Clara", "Mountain View", "San Francisco"};
	
	private JLabel titleLabel, requestsLabel, responsesLabel;
	private JButton backButton, addEquipmentButton, submitButton;
	private JTable addEquipmentTable, requestFeedTable, responseFeedTable;
	private JScrollPane scrollPaneForEquipmentTable, scrollPaneForRequestTable, scrollPaneForResponseTable;
	
	public BuyerPortal() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		backButton = new JButton("← BACK");
		backButton.setFont(new Font("Herculanum", Font.PLAIN, 20));
		backButton.setBounds(20, 20, 100, 50);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "Are you sure you want to exit the Buyer Portal and go back to the main menu?";
				int n = JOptionPane.showConfirmDialog(null, message, "Confirm Exit", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					(CovidConnection.cards).show(CovidConnection.topPanel, "Start Menu");
					(CovidConnection.sm).resetTimer();
				}
			}
		});
		add(backButton);
		
		titleLabel = new JLabel("BUYER PORTAL", JLabel.CENTER);
		titleLabel.setFont(new Font("Chalkduster", Font.PLAIN, 75));
		titleLabel.setForeground(new Color(128, 0, 128));
		titleLabel.setBounds(140, 20, 1000, 75);
		add(titleLabel);
		
		addEquipmentButton = new JButton("+ ADD NEW EQUIPMENT");
		addEquipmentButton.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		addEquipmentButton.setForeground(Color.DARK_GRAY);
		addEquipmentButton.setBounds(107, 125, 425, 65);
		addEquipmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)(addEquipmentTable.getModel());
				model.addRow(NEW_ROW);
			}
		});
		add(addEquipmentButton);
		
		submitButton = new JButton("SUBMIT ALL REQUESTS");
		submitButton.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		submitButton.setForeground(Color.DARK_GRAY);
		submitButton.setBounds(120, 615, 400, 65);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				traverseTableData();
				// TODO: Submit the requests!
				DefaultTableModel model = (DefaultTableModel)(addEquipmentTable.getModel());
				int numberOfRows = model.getRowCount();
				for (int i = 0; i < numberOfRows; ++i) { model.removeRow(0); }
			}
		});
		add(submitButton);
		
		addEquipmentTable = new JTable(new DefaultTableModel(REQUEST_COLUMNS, 0));
		addEquipmentTable.setRowHeight(40);
		addEquipmentTable.getTableHeader().setReorderingAllowed(false);
		addEquipmentTable.getTableHeader().setPreferredSize(new Dimension(600, 75));
		addEquipmentTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 30));
		addEquipmentTable.setFont(new Font("Serif", Font.PLAIN, 15));
		addEquipmentTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox<String>(CATEGORIES)));
		addEquipmentTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JComboBox<String>(LOCATIONS)));
		
		scrollPaneForEquipmentTable = new JScrollPane(addEquipmentTable);
		scrollPaneForEquipmentTable.setBounds(20, 200, 600, 395);
		scrollPaneForEquipmentTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPaneForEquipmentTable);
		
		requestsLabel = new JLabel("My Requests", JLabel.CENTER);
		requestsLabel.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		requestsLabel.setBounds(660, 125, 600, 45);
		add(requestsLabel);
		
		requestFeedTable = new JTable(new DefaultTableModel(REQUEST_COLUMNS, 0) {
			public boolean isCellEditable(int row, int column) { return false; }
		});
		requestFeedTable.setRowHeight(40);
		requestFeedTable.getTableHeader().setReorderingAllowed(false);
		requestFeedTable.getTableHeader().setPreferredSize(new Dimension(600, 40));
		requestFeedTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 20));
		requestFeedTable.setFont(new Font("Serif", Font.PLAIN, 15));
		
		scrollPaneForRequestTable = new JScrollPane(requestFeedTable);
		scrollPaneForRequestTable.setBounds(660, 175, 600, 210);
		scrollPaneForRequestTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPaneForRequestTable);
		
		requestsLabel = new JLabel("Supplier Responses", JLabel.CENTER);
		requestsLabel.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		requestsLabel.setBounds(660, 420, 600, 45);
		add(requestsLabel);
		
		responseFeedTable = new JTable(new DefaultTableModel(RESPONSE_COLUMNS, 0) {
			public boolean isCellEditable(int row, int column) { return false; }
		});
		responseFeedTable.setRowHeight(40);
		responseFeedTable.getTableHeader().setReorderingAllowed(false);
		responseFeedTable.getTableHeader().setPreferredSize(new Dimension(600, 40));
		responseFeedTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 20));
		responseFeedTable.setFont(new Font("Serif", Font.PLAIN, 15));
		
		scrollPaneForResponseTable = new JScrollPane(responseFeedTable);
		scrollPaneForResponseTable.setBounds(660, 470, 600, 210);
		scrollPaneForResponseTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPaneForResponseTable);
		
		addDummyData();
	}
	
	// TEMPORARY METHOD TO ADD DUMMY DATA TO 2 TABLES
	private void addDummyData() {
		DefaultTableModel requestModel = (DefaultTableModel)(requestFeedTable.getModel());
		for (int i = 0; i < 10; ++i) {
			requestModel.addRow(new Object[]{"Sample Category", "Sample Quantity", "Sample Location"});
		}
		DefaultTableModel responseModel = (DefaultTableModel)(responseFeedTable.getModel());
		for (int i = 0; i < 5; ++i) {
			responseModel.addRow(new Object[]{"Sample Category", "Sample Quantity", "Sample Location", "Sample Price", "Sample Supplier"});
		}
	}
	
	private void traverseTableData() {
		DefaultTableModel model = (DefaultTableModel)(addEquipmentTable.getModel());
		for (int row = 0; row < model.getRowCount(); ++row) {
			for (int col = 0; col < model.getColumnCount(); ++col) {
				String val = (String)(model.getValueAt(row, col));
				System.out.print(val + ", ");
			}
			System.out.println();
		}
	}
}	// end of class BuyerPortal