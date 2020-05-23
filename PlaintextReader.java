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
	public void parsePersonFile(String filename, String type) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) { continue; }
				if (type.equals("buyer")) {
					DataStore.getInstance().addAccount(line, "buyer", false);
				} else if (type.equals("supplier")) {
					parseSupplierSubscriptions(line);
				}
			}
			br.close();
		} catch (Exception e) { return; }
	}
	private void parseSupplierSubscriptions(String line) {
		String[] supplierData = line.split(";", 2);
		DataStore.getInstance().addAccount(supplierData[0], "supplier", false);
		HashSet<String> subscriptions = (supplierData[1].isEmpty())
										? new HashSet<String>()
										: new HashSet<String>(Arrays.asList(supplierData[1].split(",")));
		DataStore.getInstance().addSubscriptionsForSupplier(supplierData[0], subscriptions);
	}
	// Keep this method for parsing Equipment and Message data!
	private ArrayList<String> parseLine(String line, char separator) {
		ArrayList<String> entry = new ArrayList<String>();
		StringBuilder currentString = new StringBuilder();
		for (int i = 0; i < line.length(); ++i) {
			char c = line.charAt(i);
			if (c == separator) {
				entry.add(currentString.toString());
				currentString = new StringBuilder();
				continue;
			}
			currentString.append(c);
		}
		entry.add(currentString.toString());
		return entry;
	}
}