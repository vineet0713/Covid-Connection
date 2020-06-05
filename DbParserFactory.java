// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// DbParserFactory.java
// This is the concrete factory class that creates a DbParser.

public class DbParserFactory implements ParserFactory {
	private static final DbParserFactory instance = new DbParserFactory();

	public static DbParserFactory getInstance() {
		return instance;
	}
	
	public Parser createParser() {
		return new DbParser();
	}
}