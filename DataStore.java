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
	private HashSet<Item> items;
	private DataStore() {
		reader = new PlaintextReader();
		writer = new PlaintextWriter();
		currentUser = null;
		buyers = new HashSet<String>();
		supplierToSubscriptions = new HashMap<String, HashSet<String>>();
		items = new HashSet<Item>();
	}
	
	public static DataStore getInstance() {
		if (singleInstance == null) { singleInstance = new DataStore(); }
		return singleInstance;
	}
	
	public void readBuyerData() {
		buyers.clear();
		reader.parseFile("buyers.txt", "buyer");
	}
	public void readSupplierData() {
		supplierToSubscriptions.clear();
		reader.parseFile("suppliers.txt", "supplier");
	}
	public void readItemData() {
		items.clear();
		reader.parseFile("items.txt", "item");
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
			writer.writeToFile("buyers.txt", output.toString());
		} else if (userType.equals("supplier")) {
			supplierToSubscriptions.put(username, new HashSet<String>());
			if (!writeToFile) { return; }
			writer.writeToFile("suppliers.txt", generateSupplierFileData());
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
		writer.writeToFile("suppliers.txt", generateSupplierFileData());
	}
	public void removeSubscriptionForCurrentUser(String subscription) {
		supplierToSubscriptions.get(currentUser).remove(subscription);
		writer.writeToFile("suppliers.txt", generateSupplierFileData());
	}
	
	public int getNextItemId() {
		int maxId = -1;
		for (Item item : items) { maxId = Math.max(maxId, item.getId()); }
		return maxId + 1;
	}
	public HashSet<Item> getItemsForCurrentBuyer() {
		HashSet<Item> buyerItems = new HashSet<Item>();
		for (Item item : items) {
			if (item.getBuyer().equals(currentUser)) { buyerItems.add(item); }
		}
		return buyerItems;
	}
	public HashSet<Item> getItemsForCurrentSupplier() {
		HashSet<Item> supplierItems = new HashSet<Item>();
		for (Item item : items) {
			if (item.getSupplier() != null) { continue; }
			if (subscriptionExistsForCurrentUser(item.getCategory())) {
				supplierItems.add(item);
			}
		}
		return supplierItems;
	}
	public void addItem(Item item) { items.add(item); }
	public void addItems(HashSet<Item> newItems) {
		items.addAll(newItems);
		writer.writeToFile("items.txt", generateItemFileData());
	}
	private String generateItemFileData() {
		StringBuilder output = new StringBuilder();
		for (Item item : items) { output.append(item + "\n"); }
		return output.toString();
	}
}