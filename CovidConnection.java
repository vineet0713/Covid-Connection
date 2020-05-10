// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// CovidConnection.java
// This program is the main entry point into the application 'Covid Connection'.

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CardLayout;

public class CovidConnection {
	public static StartMenu sm;
	public static BuyerPortal bp;
	public static SupplierPortal sp;
	public static JPanel topPanel;
	public static CardLayout cards;
	
	public static void main(String[] args) {
		CovidConnection gui = new CovidConnection();
		gui.run();
	}
	
	// initializes all JPanel/JFrame instances and runs the application:
	public void run() {
		DataStore.getInstance().readAllFileData();
		
		sm = new StartMenu();
		bp = new BuyerPortal();
		sp = new SupplierPortal();
		
		topPanel = new JPanel();
		cards = new CardLayout();
		topPanel.setLayout(cards);
		topPanel.add(sm, "Start Menu");
		topPanel.add(bp, "Buyer Portal");
		topPanel.add(sp, "Supplier Portal");
		
		JFrame window = new JFrame();
		window.setContentPane(topPanel);
		window.setSize(1280, 720);
		window.setTitle("Covid Connection");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}	// end of class CovidConnection