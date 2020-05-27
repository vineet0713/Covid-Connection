// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This is the concrete factory class that creates a PlaintextParser.

import java.util.HashMap;

public class PlaintextParserFactory implements ParserFactory {

	  private static final PlaintextParserFactory instance = new PlaintextParserFactory();

	  public static PlaintextParserFactory getInstance() {
	    return instance;
	  }
	  
		public Parser createParser(HashMap<String, String> typeToFilename) {
			return new PlaintextParser(typeToFilename);
		}
}