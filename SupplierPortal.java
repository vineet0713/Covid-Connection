// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This JPanel displays the supplier portal of the application 'Covid Connection'.

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SupplierPortal extends JPanel {
	// NOTE: We could centralize this String array! (since it's also being used in BuyerPortal)
	private String[] CATEGORIES = {"[SELECT CATEGORY]", "Masks", "Ventilators", "Pills", "Wheelchairs"};
	private String[] RESPONSES = {"[SELECT RESPONSE]", "Accept", "Deny"};
	
	private JLabel title, manageSubscriptionsLabel;
	private JComboBox subscriptionsDropdown;
	private JButton back, addSubscriptionButton, removeSubscriptionButton, submitButton;
	private JTable subscriptionsTable, notificationsTable;
	private JScrollPane scrollPaneForSubscriptionsTable, scrollPaneForNotificationsTable;
	
	public SupplierPortal() {
		setLayout(null);
		setBackground(Color.WHITE);
		
		back = new JButton("← BACK");
		back.setFont(new Font("Herculanum", Font.PLAIN, 20));
		back.setBounds(20, 20, 100, 50);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "Are you sure you want to exit the Supplier Portal and go back to the main menu?";
				int n = JOptionPane.showConfirmDialog(null, message, "Confirm Exit", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					(CovidConnection.cards).show(CovidConnection.topPanel, "Start Menu");
					(CovidConnection.sm).resetTimer();
				}
			}
		});
		add(back);
		
		title = new JLabel("SUPPLIER PORTAL", JLabel.CENTER);
		title.setFont(new Font("Chalkduster", Font.PLAIN, 75));
		title.setForeground(new Color(128, 0, 128));
		title.setBounds(140, 20, 1000, 75);
		add(title);
		
		manageSubscriptionsLabel = new JLabel("Manage Subscriptions");
		manageSubscriptionsLabel.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		manageSubscriptionsLabel.setBounds(20, 125, 375, 65);
		add(manageSubscriptionsLabel);
		
		subscriptionsDropdown = new JComboBox<String>(CATEGORIES);
		subscriptionsDropdown.setBounds(425, 125, 235, 65);
		add(subscriptionsDropdown);
		
		addSubscriptionButton = new JButton("ADD SUBSCRIPTION");
		addSubscriptionButton.setFont(new Font("Chalkduster", Font.PLAIN, 20));
		addSubscriptionButton.setBounds(700, 130, 250, 45);
		addSubscriptionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedCategory = subscriptionsDropdown.getSelectedItem().toString();
				addSubscription(selectedCategory);
			}
		});
		add(addSubscriptionButton);
		
		removeSubscriptionButton = new JButton("REMOVE SUBSCRIPTION");
		removeSubscriptionButton.setFont(new Font("Chalkduster", Font.PLAIN, 20));
		removeSubscriptionButton.setBounds(960, 130, 300, 45);
		removeSubscriptionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedCategory = subscriptionsDropdown.getSelectedItem().toString();
				removeSubscription(selectedCategory);
			}
		});
		add(removeSubscriptionButton);
		
		subscriptionsTable = new JTable(new DefaultTableModel(new String[]{"My Subscriptions"}, 0) {
			public boolean isCellEditable(int row, int column) { return false; }
		});
		subscriptionsTable.setRowHeight(40);
		subscriptionsTable.getTableHeader().setReorderingAllowed(false);
		subscriptionsTable.getTableHeader().setPreferredSize(new Dimension(600, 75));
		subscriptionsTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 30));
		((DefaultTableCellRenderer)subscriptionsTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		subscriptionsTable.setFont(new Font("Serif", Font.PLAIN, 25));
		
		scrollPaneForSubscriptionsTable = new JScrollPane(subscriptionsTable);
		scrollPaneForSubscriptionsTable.setBounds(20, 225, 600, 455);
		scrollPaneForSubscriptionsTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPaneForSubscriptionsTable);
		
		notificationsTable = new JTable(new DefaultTableModel(new String[]{"My Notifications", "Response"}, 0) {
			public boolean isCellEditable(int row, int column) { return (column == 1); }
		});
		notificationsTable.setRowHeight(40);
		notificationsTable.getTableHeader().setReorderingAllowed(false);
		notificationsTable.getTableHeader().setPreferredSize(new Dimension(600, 75));
		notificationsTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 30));
		((DefaultTableCellRenderer)notificationsTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		notificationsTable.setFont(new Font("Serif", Font.PLAIN, 15));
		notificationsTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox<String>(RESPONSES)));
		
		scrollPaneForNotificationsTable = new JScrollPane(notificationsTable);
		scrollPaneForNotificationsTable.setBounds(660, 225, 600, 375);
		scrollPaneForNotificationsTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPaneForNotificationsTable);
		
		submitButton = new JButton("SUBMIT ALL RESPONSES");
		submitButton.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		submitButton.setForeground(Color.DARK_GRAY);
		submitButton.setBounds(750, 615, 410, 65);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitResponses();
			}
		});
		add(submitButton);
		
		addDummyData();
	}
	
	private void addSubscription(String category) {
		if (category.equals(subscriptionsDropdown.getItemAt(0))) {
			JOptionPane.showMessageDialog(null, "Please select a category.", null, JOptionPane.ERROR_MESSAGE);
			return;
		}
		((DefaultTableModel)subscriptionsTable.getModel()).addRow(new String[]{category});
		subscriptionsDropdown.setSelectedIndex(0);
	}
	
	private void removeSubscription(String category) {
		if (category.equals(subscriptionsDropdown.getItemAt(0))) {
			JOptionPane.showMessageDialog(null, "Please select a category.", null, JOptionPane.ERROR_MESSAGE);
			return;
		}
		DefaultTableModel model = (DefaultTableModel)(subscriptionsTable.getModel());
		for (int row = 0; row < model.getRowCount(); ++row) {
			if (((String)(model.getValueAt(row, 0))).equals(category)) {
				model.removeRow(row);
				break;
			}
		}
		subscriptionsDropdown.setSelectedIndex(0);
	}
	
	private void submitResponses() {
		DefaultTableModel model = (DefaultTableModel)(notificationsTable.getModel());
		ArrayList<Integer> rowsToRemove = new ArrayList<Integer>();
		for (int row = 0; row < model.getRowCount(); ++row) {
			String response = (String)(model.getValueAt(row, 1));
			if (response.equals("Accept")) {
				// TODO: Send response of 'Accept'
				rowsToRemove.add(new Integer(row));
			} else if (response.equals("Deny")) {
				// TODO: Send response of 'Deny'
				rowsToRemove.add(new Integer(row));
			}
		}
		for (int i = 0; i < rowsToRemove.size(); ++i) {
			model.removeRow(rowsToRemove.get(i).intValue() - i);
		}
	}
	
	// TEMPORARY METHOD TO ADD DUMMY DATA TO 2 TABLES
	private void addDummyData() {
		DefaultTableModel subscriptionsTableModel = (DefaultTableModel)(subscriptionsTable.getModel());
		subscriptionsTableModel.addRow(new String[]{"Ventilators"});
		subscriptionsTableModel.addRow(new String[]{"Wheelchairs"});
		
		DefaultTableModel notificationsTableModel = (DefaultTableModel)(notificationsTable.getModel());
		notificationsTableModel.addRow(new String[]{"Wheelchair from buyer2"});
		notificationsTableModel.addRow(new String[]{"Ventilator from buyer1"});
		notificationsTableModel.addRow(new String[]{"Wheelchair from buyer5"});
	}
}	// end of class SupplierPortal