// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierFileDataGenerator.java
// This class generates supplier file data (part of Strategy pattern) in the application 'Covid Connection'.

import java.util.HashMap;
import java.util.HashSet;

public class SupplierFileDataGenerator implements FileDataGenerator {
	public String generate() {
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
}