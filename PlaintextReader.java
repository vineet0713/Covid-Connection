// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This class parses the input files and populates the DataStore.

import java.lang.Exception;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

// import java.text.SimpleDateFormat;

// import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;

public class PlaintextReader {
	public void parseFile(String filename, String type) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) { continue; }
				if (type.equals("buyer")) {
					DataStore.getInstance().addAccount(line, "buyer", false);
				} else if (type.equals("supplier")) {
					parseSupplierSubscriptions(line);
				} else if (type.equals("item")) {
					parseItemLine(line);
				}
			}
			br.close();
		} catch (Exception e) { return; }
	}
	
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
		Item parsedItem;
		if (itemData.length == 5) {
			parsedItem = new Item(id, itemData[1], itemData[2], quantity, itemData[4]);
		} else {
			double supplierPrice;
			try { supplierPrice = Double.parseDouble(itemData[6]); } catch (Exception e) { supplierPrice = -1; }
			parsedItem = new Item(id, itemData[1], itemData[2], quantity, itemData[4], itemData[5], supplierPrice, itemData[7]);
		}
		DataStore.getInstance().addItem(parsedItem);
	}
}