// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This class is a Singleton that stores all of the application data.

import java.util.HashSet;

public class DataStore {
	private static DataStore singleInstance = null;
	private PlaintextReader reader;
	private PlaintextWriter writer;
	private String currentUser;
	private HashSet<String> buyers, suppliers;
	private DataStore() {
		reader = new PlaintextReader();
		writer = new PlaintextWriter();
		currentUser = null;
		buyers = new HashSet<String>();
		suppliers = new HashSet<String>();
	}
	public static DataStore getInstance() {
		if (singleInstance == null) { singleInstance = new DataStore(); }
		return singleInstance;
	}
	public void readAllFileData() {
		reader.parsePersonFile("buyers.txt", "buyer");
		reader.parsePersonFile("suppliers.txt", "supplier");
	}
	public String getCurrentUser() { return currentUser; }
	public void setCurrentUser(String newUser) { currentUser = newUser; }
	public boolean accountExists(String username, String userType) {
		if (userType.equals("buyer")) { return buyers.contains(username); }
		else if (userType.equals("supplier")) { return suppliers.contains(username); }
		else { return false; }
	}
	public void addAccount(String username, String userType, boolean writeToFile) {
		StringBuilder output = new StringBuilder();
		if (userType.equals("buyer")) {
			buyers.add(username);
			if (!writeToFile) { return; }
			for (String buyerName : buyers) { output.append(buyerName + "\n"); }
			writer.writeToPersonFile("buyers.txt", output.toString());
		} else if (userType.equals("supplier")) {
			suppliers.add(username);
			if (!writeToFile) { return; }
			for (String supplierName : suppliers) { output.append(supplierName + "\n"); }
			writer.writeToPersonFile("suppliers.txt", output.toString());
		}
	}
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Buyers:\n");
		for (String buyerName : buyers) { s.append("\t" + buyerName + ",\n"); }
		s.append("Suppliers:\n");
		for (String supplierName : suppliers) { s.append("\t" + supplierName + ",\n"); }
		return s.toString();
	}
}