// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This JPanel displays the supplier portal of the application 'Covid Connection'.

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SupplierPortal extends JPanel {
	private JLabel title;
	private JButton back;
	private JTextArea instructions;
	
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
		
		instructions = new JTextArea("Supplier UI (TODO)");
		instructions.setFont(new Font("Chalkduster", Font.PLAIN, 35));
		instructions.setForeground(Color.DARK_GRAY);
		instructions.setLineWrap(true);
		instructions.setWrapStyleWord(true);
		instructions.setBounds(20, 125, 1240, 545);
		add(instructions);
	}
}	// end of class SupplierPortal