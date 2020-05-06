// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// BuyerPortal.java
// This JPanel displays the buyer portal of the application 'Covid Connection'.

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuyerPortal extends JPanel implements ActionListener {
	private String[] CATEGORIES = {"ALL", "Masks", "Ventilators", "Pills", "Wheelchairs"};
	
	private JLabel titleLabel;
	private JButton backButton, searchButton;
	private JTextArea test;
	private JComboBox<String> categoryDropdown;
	private JTextField searchBox;
	
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
		
		categoryDropdown = new JComboBox<String>(CATEGORIES);
		categoryDropdown.setFont(new Font("Chalkduster", Font.PLAIN, 15));
		categoryDropdown.setForeground(Color.DARK_GRAY);
		categoryDropdown.setBounds(20, 125, 250, 50);
		add(categoryDropdown);
		
		searchBox = new JTextField();
		searchBox.setFont(new Font("Chalkduster", Font.PLAIN, 15));
		searchBox.setForeground(Color.DARK_GRAY);
		searchBox.setBounds(300, 125, 835, 50);
		searchBox.addActionListener(this);
		add(searchBox);
		
		searchButton = new JButton("SEARCH");
		searchButton.setFont(new Font("Chalkduster", Font.PLAIN, 15));
		searchButton.setForeground(Color.DARK_GRAY);
		searchButton.setBounds(1160, 125, 100, 50);
		searchButton.addActionListener(this);
		add(searchButton);
		
		test = new JTextArea("TABLE WITH QUERIED DATA (TODO!)");
		test.setFont(new Font("Chalkduster", Font.PLAIN, 35));
		test.setForeground(Color.DARK_GRAY);
		test.setLineWrap(true);
		test.setWrapStyleWord(true);
		test.setBounds(20, 225, 1240, 445);
		add(test);
	}
	
	public void actionPerformed(ActionEvent e) {
		String selectedCategory = categoryDropdown.getSelectedItem().toString();
		String searchQuery = searchBox.getText();
		System.out.println("Search Query: " + selectedCategory + ", " + searchQuery);
	}
}	// end of class BuyerPortal