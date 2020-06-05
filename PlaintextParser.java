// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// PlaintextParser.java
// This class parses the plaintext input files and populates the DataStore.

import java.lang.Exception;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;

public class PlaintextParser implements Parser {
	public void readFile(String type) {
		String filename = type + "s.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) {
					continue;
				}
				if (type.equals("buyer")) {
					DataStore.getInstance().addAccount(line, "buyer", false);
				} else if (type.equals("supplier")) {
					parseSupplierSubscriptions(line);
				} else {
					parseItemLine(line);
				}
			}
			br.close();
		} catch (Exception e) {
			return;
		}
	}

	public void writeFile(String type) {
		FileDataGenerator generator;
		if (type.equals("buyer")) {
			generator = BuyerFileDataGenerator.getInstance();
		} else if (type.equals("supplier")) {
			generator = SupplierFileDataGenerator.getInstance();
		} else {
			generator = ItemFileDataGenerator.getInstance();
		}
		String filename = type + "s.txt";
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {
			bw.write(generator.generate());
			bw.close();
		} catch (Exception e) {
			return;
		}
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
		try {
			id = Integer.parseInt(itemData[0]);
		} catch (Exception e) {
			id = -1;
		}
		try {
			quantity = Integer.parseInt(itemData[3]);
		} catch (Exception e) {
			quantity = -1;
		}
		HashSet<SupplierResponse> responses = new HashSet<SupplierResponse>();
		int index = 5;
		while (index < itemData.length) {
			double price;
			try {
				price = Double.parseDouble(itemData[index + 1]);
			} catch (Exception e) {
				price = -1;
			}
			responses.add(new SupplierResponse(itemData[index], price, itemData[index + 2]));
			index += 3;
		}
		DataStore.getInstance().addItem(Item.builder()
				 .id(id)
				 .buyer(itemData[1])
				 .category(itemData[2])
				 .quantity(quantity)
				 .location(itemData[4])
				 .responses(responses)
				 .build());
	}
}