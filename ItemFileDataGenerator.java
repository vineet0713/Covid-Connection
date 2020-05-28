// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// ItemFileDataGenerator.java
// This class generates item file data (part of Strategy pattern) in the application 'Covid Connection'.

import java.util.HashSet;

public class ItemFileDataGenerator implements FileDataGenerator {
	public String generate() {
		StringBuilder output = new StringBuilder();
		HashSet<Item> items = DataStore.getInstance().getItems();
		for (Item item : items) { output.append(item + "\n"); }
		return output.toString();
	}
}