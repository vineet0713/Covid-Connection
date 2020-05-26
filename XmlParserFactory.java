// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This is the concrete factory class that creates an XmlParser.

import java.util.HashMap;

public class XmlParserFactory implements ParserFactory {
	public Parser createParser(HashMap<String, String> typeToFilename) {
		return new XmlParser(typeToFilename);
	}
}