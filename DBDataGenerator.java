// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// DBDataGenerator.java
// Interface to generate DB Data (read/write) - A strategy Pattern

import java.sql.Connection;

public interface DBDataGenerator {
	public void generate(String type, Connection connection);
}