// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This class parses the plaintext input files and populates the DataStore.

import java.lang.Exception;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;

public class PlaintextParser implements Parser {
	private HashMap<String, String> typeToFilename;
	public PlaintextParser(HashMap<String, String> typeToFilename) {
		this.typeToFilename = typeToFilename;
	}
	public void readFile(String type) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(typeToFilename.get(type))))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) { continue; }
				if (type.equals("buyer")) { DataStore.getInstance().addAccount(line, "buyer", false); }
				else if (type.equals("supplier")) { parseSupplierSubscriptions(line); }
				else if (type.equals("item")) { parseItemLine(line); }
			}
			br.close();
		} catch (Exception e) { return; }
	}
	public void writeFile(String type) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(typeToFilename.get(type))))) {
			if (type.equals("buyer")) { bw.write(generateBuyerFileData()); }
			else if (type.equals("supplier")) { bw.write(generateSupplierFileData()); }
			else if (type.equals("item")) { bw.write(generateItemFileData()); }
			bw.close();
		} catch (Exception e) { return; }
	}
	
	// Helper functions for the readFile method:
	private void parseSupplierSubscriptions(String line) {
		String[] supplierData = line.split(";", 2);
		HashSet<String> subscriptions = (supplierData[1].isEmpty())
										? new HashSet<String>()
										: new HashSet<String>(Arrays.asList(supplierData[1].split(",")));
		DataStore.getInstance().addSubscriptionsForSupplier(supplierData[0], subscriptions);
	}
	private void parseItemLine(String line) {
		String[] itemData = line.split(";");
		int id, quantity;
		try { id = Integer.parseInt(itemData[0]); } catch (Exception e) { id = -1; }
		try { quantity = Integer.parseInt(itemData[3]); } catch (Exception e) { quantity = -1; }
		HashSet<SupplierResponse> responses = new HashSet<SupplierResponse>();
		int index = 5;
		while (index < itemData.length) {
			double price;
			try { price = Double.parseDouble(itemData[index + 1]); } catch (Exception e) { price = -1; }
			responses.add(new SupplierResponse(itemData[index], price, itemData[index + 2]));
			index += 3;
		}
		DataStore.getInstance().addItem(new Item(id, itemData[1], itemData[2], quantity, itemData[4], responses));
	}
	
	// Helper functions for the writeFile method:
	private String generateBuyerFileData() {
		StringBuilder output = new StringBuilder();
		HashSet<String> buyers = DataStore.getInstance().getBuyers();
		for (String buyerName : buyers) { output.append(buyerName + "\n"); }
		return output.toString();
	}
	private String generateSupplierFileData() {
		StringBuilder output = new StringBuilder();
		HashMap<String, HashSet<String>> supplierToSubscriptions = DataStore.getInstance().getSupplierToSubscriptions();
		for (String supplierName : supplierToSubscriptions.keySet()) {
			output.append(supplierName + ";");
			for (String subscription : supplierToSubscriptions.get(supplierName)) {
				if (!subscription.isEmpty()) { output.append(subscription + ","); }
			}
			output.append("\n");
		}
		return output.toString();
	}
	private String generateItemFileData() {
		StringBuilder output = new StringBuilder();
		HashSet<Item> items = DataStore.getInstance().getItems();
		for (Item item : items) { output.append(item + "\n"); }
		return output.toString();
	}
}