// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// DataStore.java
// This class is a Singleton that stores all of the application data.

import java.util.HashSet;
import java.util.HashMap;

public class DataStore {
	private static DataStore singleInstance = null;
	private Parser parser;
	private String currentUser;
	private HashSet<String> buyers;
	private HashMap<String, HashSet<String>> supplierToSubscriptions;
	private HashSet<Item> items;
	private DataStore() {
		currentUser = null;
		buyers = new HashSet<String>();
		supplierToSubscriptions = new HashMap<String, HashSet<String>>();
		items = new HashSet<Item>();
	}
	public void setParser(Parser parser) { this.parser = parser; }
	public HashSet<String> getBuyers() { return buyers; }
	public HashMap<String, HashSet<String>> getSupplierToSubscriptions() { return supplierToSubscriptions; }
	public HashSet<Item> getItems() { return items; }
	
	public static DataStore getInstance() {
		if (singleInstance == null) { singleInstance = new DataStore(); }
		return singleInstance;
	}
	
	public void readBuyerData() {
		buyers.clear();
		parser.readFile("buyer");
	}
	public void readSupplierData() {
		supplierToSubscriptions.clear();
		parser.readFile("supplier");
	}
	public void readItemData() {
		items.clear();
		parser.readFile("item");
	}
	
	public String getCurrentUser() { return currentUser; }
	public void setCurrentUser(String newUser) { currentUser = newUser; }
	
	public boolean accountExists(String username, String userType) {
		if (userType.equals("buyer")) { return buyers.contains(username); }
		else if (userType.equals("supplier")) { return supplierToSubscriptions.containsKey(username); }
		else { return false; }
	}
	public void addAccount(String username, String userType, boolean writeToFile) {
		if (!userType.equals("buyer") && !userType.equals("supplier")) { return; }
		if (userType.equals("buyer")) {
			buyers.add(username);
		} else if (userType.equals("supplier")) {
			supplierToSubscriptions.put(username, new HashSet<String>());
		}
		if (!writeToFile) { return; }
		parser.writeFile(userType);
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
		parser.writeFile("supplier");
	}
	public void removeSubscriptionForCurrentUser(String subscription) {
		supplierToSubscriptions.get(currentUser).remove(subscription);
		parser.writeFile("supplier");
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
			if (subscriptionExistsForCurrentUser(item.getCategory()) && !item.supplierHasResponse(currentUser)) {
				supplierItems.add(item);
			}
		}
		return supplierItems;
	}
	public void addItem(Item item) { items.add(item); }
	public void addItems(HashSet<Item> newItems) {
		items.addAll(newItems);
		parser.writeFile("item");
	}
}