// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// BuyerFileDataGenerator.java
// This class generates buyer file data (part of Strategy pattern) in the application 'Covid Connection'.

import java.util.HashSet;

public class BuyerFileDataGenerator implements FileDataGenerator {

	private static final BuyerFileDataGenerator instance = new BuyerFileDataGenerator();

	public static BuyerFileDataGenerator getInstance() {
		return instance;
	}

	public String generate() {
		StringBuilder output = new StringBuilder();
		HashSet<String> buyers = DataStore.getInstance().getBuyers();
		for (String buyerName : buyers) {
			output.append(buyerName + "\n");
		}
		return output.toString();
	}
}