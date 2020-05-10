// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// StartMenu.java
// This JPanel is the start menu of the application 'Covid Connection'.

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartMenu extends JPanel implements ActionListener {
	private static final int LEFT_BOUND = 0, RIGHT_BOUND = 250;
	
	private JLabel titleLabel;
	private JButton buyerButton, supplierButton;
	
	private Timer timer;
	
	private int titleXPos, forward;
	
	public StartMenu() {
		setLayout(null);
		setBackground(Color.BLACK);
		
		titleLabel = new JLabel("Covid Connection");
		titleLabel.setFont(new Font("Herculanum", Font.PLAIN, 115));
		titleLabel.setForeground(new Color(216, 161, 107));
		titleLabel.setBounds(titleXPos, 75, 1050, 125);
		add(titleLabel);
		
		buyerButton = new JButton("BUYER PORTAL");
		buyerButton.setFont(new Font("Herculanum", Font.PLAIN, 40));
		buyerButton.setForeground(new Color(86, 47, 14));
		buyerButton.setBounds(420, 365, 350, 90);
		buyerButton.addActionListener(this);
		buyerButton.setActionCommand("Buyer Portal");
		add(buyerButton);
		
		supplierButton = new JButton("SUPPLIER PORTAL");
		supplierButton.setFont(new Font("Herculanum", Font.PLAIN, 40));
		supplierButton.setForeground(new Color(86, 47, 14));
		supplierButton.setBounds(420, 495, 350, 90);
		supplierButton.addActionListener(this);
		supplierButton.setActionCommand("Supplier Portal");
		add(supplierButton);
		
		timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (titleXPos == RIGHT_BOUND) {
					forward = -1;
				} else if (titleXPos == LEFT_BOUND) {
					forward = 1;
				}
				titleXPos += forward;
				titleLabel.setLocation(titleXPos, 75);
			}
		});
		resetTimer();
	}	// end of constructor
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}	// end of public void paintComponent
	
	// based on which button was clicked, switches the JPanel:
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		String userType = command.substring(0, command.indexOf(' ')).toLowerCase();
		String message = "Would you like to create a new " + userType + ", or use an existing " + userType + "?";
		Object[] options = {"Create New", "Use Existing"};
		int n = JOptionPane.showOptionDialog(null, message, null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
		String accountType = "";
		if (n == JOptionPane.YES_OPTION) {
			accountType = "new";
		} else if (n == JOptionPane.NO_OPTION) {
			accountType = "existing";
		} else {
			return;
		}
		String username = JOptionPane.showInputDialog("Please enter your " + accountType + " " + userType + " username.");
		if (username == null) { return; }
		boolean accountAlreadyExists = DataStore.getInstance().accountExists(username, userType);
		if (accountType.equals("existing") && !accountAlreadyExists) {
			message = "The " + userType + " username '" + username + "' does not exist!";
			JOptionPane.showMessageDialog(null, message, "INVALID USERNAME", JOptionPane.ERROR_MESSAGE);
			return;
		} else if (accountType.equals("new")) {
			if (accountAlreadyExists) {
				message = "The " + userType + " username '" + username + "' already exists!";
				JOptionPane.showMessageDialog(null, message, "USERNAME EXISTS", JOptionPane.ERROR_MESSAGE);
				return;
			}
			DataStore.getInstance().addAccount(username, userType, true);
			message = "A " + userType + " with username '" + username + "' has been created!";
			JOptionPane.showMessageDialog(null, message, userType.toUpperCase() + " CREATED", JOptionPane.INFORMATION_MESSAGE);
		}
		DataStore.getInstance().setCurrentUser(username);
		timer.stop();
		(CovidConnection.cards).show(CovidConnection.topPanel, e.getActionCommand());
	}
	
	// resets the animation Timer:
	public void resetTimer() {
		titleXPos = 1;
		forward = 1;
		timer.start();
	}
}	// end of class StartMenu