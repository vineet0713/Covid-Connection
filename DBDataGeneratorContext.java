// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// ReadDbDataGenerator.java
// This class handles modifying DB data -  part of Strategy Pattern - Context Class

import java.sql.Connection;

public class DBDataGeneratorContext {
	private DBDataGenerator strategy;
	
	public DBDataGeneratorContext(DBDataGenerator strategy){
		this.strategy = strategy;
	}
	
	public void executeStrategy(String type, Connection connection){
		strategy.generate(type, connection);
	}
}