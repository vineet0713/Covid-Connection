// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// CovidConnection.java
// This program is the main entry point into the application 'Covid Connection'.

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.CardLayout;

import java.lang.Exception;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.HashMap;

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
	
	public void run() {
		try { readConfiguration("configuration.txt"); }
		catch (Exception e) { System.out.println("Invalid configuration."); return; }
		
		DataStore.getInstance().readBuyerData();
		DataStore.getInstance().readSupplierData();
		DataStore.getInstance().readItemData();
		
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
	
	private void readConfiguration(String filename) throws Exception {
		ParserFactory parserFactory = null;
		HashMap<String, String> typeToFilename = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.startsWith("//")) { continue; }
			String fileType = line.toLowerCase();
			if (fileType.equals("plaintext")) {
				parserFactory = new PlaintextParserFactory();
				typeToFilename.put("buyer", "buyers.txt");
				typeToFilename.put("supplier", "suppliers.txt");
				typeToFilename.put("item", "items.txt");
				break;
			} else if (fileType.equals("xml")) {
				parserFactory = new XmlParserFactory();
				typeToFilename.put("buyer", "[INSERT XML FILENAME]");
				typeToFilename.put("supplier", "[INSERT XML FILENAME]");
				typeToFilename.put("item", "[INSERT XML FILENAME]");
				break;
			} else {
				throw new Exception();
			}
		}
		br.close();
		DataStore.getInstance().setParser(parserFactory.createParser(typeToFilename));
	}
}	// end of class CovidConnection