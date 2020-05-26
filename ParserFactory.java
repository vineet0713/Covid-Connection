// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This is the factory interface for creating a Parser.

import java.util.HashMap;

public interface ParserFactory {
	public abstract Parser createParser(HashMap<String, String> typeToFilename);
}