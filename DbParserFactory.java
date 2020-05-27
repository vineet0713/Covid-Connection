// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This is the concrete factory class that creates an XmlParser.

import java.util.HashMap;

public class DbParserFactory implements ParserFactory {
	private static final DbParserFactory instance = new DbParserFactory();

	  public static DbParserFactory getInstance() {
	    return instance;
	  }
	public Parser createParser(HashMap<String, String> typeToFilename) {
		return new DbParser(typeToFilename);
	}
}