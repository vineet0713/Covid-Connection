// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// BuyerPortal.java
// This JPanel displays the buyer portal of the application 'Covid Connection'.

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuyerPortal extends JPanel {
	private String[] CATEGORIES = {"Masks", "Ventilators", "Pills", "Wheelchairs"};
	
	private JLabel titleLabel;
	private JButton backButton, addEquipmentButton, submitButton;
	private JTable table;
	private JScrollPane scrollPane;
	// private JComboBox<String> categoryDropdown;
	
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
		
		/*
		categoryDropdown = new JComboBox<String>(CATEGORIES);
		categoryDropdown.setFont(new Font("Chalkduster", Font.PLAIN, 15));
		categoryDropdown.setForeground(Color.DARK_GRAY);
		categoryDropdown.setBounds(20, 125, 250, 50);
		add(categoryDropdown);
		*/
		
		addEquipmentButton = new JButton("+ ADD NEW EQUIPMENT");
		addEquipmentButton.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		addEquipmentButton.setForeground(Color.DARK_GRAY);
		addEquipmentButton.setBounds(150, 125, 425, 65);
		addEquipmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)(table.getModel());
				model.addRow(new Object[]{"New Category", "New Quantity", "New Place"});
			}
		});
		add(addEquipmentButton);
		
		submitButton = new JButton("SUBMIT ALL REQUESTS");
		submitButton.setFont(new Font("Chalkduster", Font.PLAIN, 30));
		submitButton.setForeground(Color.DARK_GRAY);
		submitButton.setBounds(725, 125, 400, 65);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				traverseTableData();
				// TODO: Submit the requests!
				DefaultTableModel model = (DefaultTableModel)(table.getModel());
				int numberOfRows = model.getRowCount();
				for (int i = 0; i < numberOfRows; ++i) { model.removeRow(0); }
			}
		});
		add(submitButton);
		
		table = new JTable(new DefaultTableModel(new Object[]{"Category", "Quantity", "Place"}, 0));
		table.setRowHeight(50);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setPreferredSize(new Dimension(1200, 75));
		table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 25));
		table.setFont(new Font("Serif", Font.PLAIN, 20));
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(40, 200, 1200, 450);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
	}
	
	private void traverseTableData() {
		DefaultTableModel model = (DefaultTableModel)(table.getModel());
		for (int row = 0; row < model.getRowCount(); ++row) {
			for (int col = 0; col < model.getColumnCount(); ++col) {
				System.out.print(model.getValueAt(row, col) + ", ");
			}
			System.out.println();
		}
	}
}	// end of class BuyerPortal