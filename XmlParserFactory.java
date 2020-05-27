// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This is the concrete factory class that creates an XmlParser.

import java.util.HashMap;

public class XmlParserFactory implements ParserFactory {
	private static final XmlParserFactory instance = new XmlParserFactory();

	  public static XmlParserFactory getInstance() {
	    return instance;
	  }
	public Parser createParser(HashMap<String, String> typeToFilename) {
		return new XmlParser(typeToFilename);
	}
}