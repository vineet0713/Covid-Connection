// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This class is a Singleton that stores all of the application data.

import java.util.HashSet;
import java.util.HashMap;

public class DataStore {
	private static DataStore singleInstance = null;
	private PlaintextReader reader;
	private PlaintextWriter writer;
	private String currentUser;
	private HashSet<String> buyers;
	private HashMap<String, HashSet<String>> supplierToSubscriptions;
	private DataStore() {
		reader = new PlaintextReader();
		writer = new PlaintextWriter();
		currentUser = null;
		buyers = new HashSet<String>();
		supplierToSubscriptions = new HashMap<String, HashSet<String>>();
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
		else if (userType.equals("supplier")) { return supplierToSubscriptions.containsKey(username); }
		else { return false; }
	}
	public void addAccount(String username, String userType, boolean writeToFile) {
		if (userType.equals("buyer")) {
			buyers.add(username);
			if (!writeToFile) { return; }
			StringBuilder output = new StringBuilder();
			for (String buyerName : buyers) { output.append(buyerName + "\n"); }
			writer.writeToPersonFile("buyers.txt", output.toString());
		} else if (userType.equals("supplier")) {
			supplierToSubscriptions.put(username, new HashSet<String>());
			if (!writeToFile) { return; }
			writer.writeToPersonFile("suppliers.txt", generateSupplierFileData());
		}
	}
	private String generateSupplierFileData() {
		StringBuilder output = new StringBuilder();
		for (String supplierName : supplierToSubscriptions.keySet()) {
			output.append(supplierName + ";");
			for (String subscription : supplierToSubscriptions.get(supplierName)) {
				if (!subscription.isEmpty()) { output.append(subscription + ","); }
			}
			output.append("\n");
		}
		return output.toString();
	}
	public void addSubscriptionsForSupplier(String supplier, HashSet<String> subscriptions) {
		supplierToSubscriptions.put(supplier, subscriptions);
	}
	public HashSet<String> getSubscriptionsForCurrentUser() {
		return supplierToSubscriptions.get(currentUser);
	}
	public boolean subscriptionExistsForCurrentUser(String subscription) {
		return supplierToSubscriptions.get(currentUser).contains(subscription);
	}
	public void addSubscriptionForCurrentUser(String subscription) {
		supplierToSubscriptions.get(currentUser).add(subscription);
		writer.writeToPersonFile("suppliers.txt", generateSupplierFileData());
	}
	public void removeSubscriptionForCurrentUser(String subscription) {
		supplierToSubscriptions.get(currentUser).remove(subscription);
		writer.writeToPersonFile("suppliers.txt", generateSupplierFileData());
	}
	/*
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Buyers:\n");
		for (String buyerName : buyers) { s.append("\t" + buyerName + ",\n"); }
		s.append("Suppliers:\n");
		for (String supplierName : supplierToSubscriptions.keySet()) {
			s.append("\t" + supplierName + ":");
			for (String subscription : supplierToSubscriptions.get(supplierName)) {
				s.append(subscription + ",");
			}
		}
		return s.toString();
	}
	*/
}