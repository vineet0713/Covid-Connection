// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This is the concrete factory class that creates a PlaintextParser.

import java.util.HashMap;

public class PlaintextParserFactory implements ParserFactory {
	public Parser createParser(HashMap<String, String> typeToFilename) {
		return new PlaintextParser(typeToFilename);
	}
}