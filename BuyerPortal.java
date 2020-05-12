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
	private String[] CATEGORIES = {"Masks", "Ventilators", "Pills", "Wheelchairs"};
	
	private JLabel titleLabel;
	private JButton backButton, addEquipmentButton, submitButton;
	private JTable dataTable;
	private JScrollPane scrollPaneForTable;
	
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
		addEquipmentButton.setBounds(150, 125, 425, 65);
		addEquipmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)(dataTable.getModel());
				model.addRow(new Object[]{"Click to Select Category", "Enter Quantity", "Enter Place"});
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
				DefaultTableModel model = (DefaultTableModel)(dataTable.getModel());
				int numberOfRows = model.getRowCount();
				for (int i = 0; i < numberOfRows; ++i) { model.removeRow(0); }
			}
		});
		add(submitButton);
		
		dataTable = new JTable(new DefaultTableModel(new Object[]{"Category", "Quantity", "Place"}, 0));
		dataTable.setRowHeight(50);
		dataTable.getTableHeader().setReorderingAllowed(false);
		dataTable.getTableHeader().setPreferredSize(new Dimension(1200, 75));
		dataTable.getTableHeader().setFont(new Font("Serif", Font.BOLD, 25));
		dataTable.setFont(new Font("Serif", Font.PLAIN, 20));
		dataTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JComboBox<String>(CATEGORIES)));
		
		scrollPaneForTable = new JScrollPane(dataTable);
		scrollPaneForTable.setBounds(40, 200, 1200, 450);
		scrollPaneForTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPaneForTable);
	}
	
	private void traverseTableData() {
		DefaultTableModel model = (DefaultTableModel)(dataTable.getModel());
		for (int row = 0; row < model.getRowCount(); ++row) {
			for (int col = 0; col < model.getColumnCount(); ++col) {
				String val = (String)(model.getValueAt(row, col));
				System.out.print(val + ", ");
			}
			System.out.println();
		}
	}
}	// end of class BuyerPortal