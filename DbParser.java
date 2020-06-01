// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// DbParser.java
// This class parses the DB input/output data and populates the DataStore.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbParser implements Parser {
	Connection connection = null;
	String url1 = "jdbc:mysql://localhost:3306/covidconnection";
	String user = "root";
	String password = "MySQL@Password!";
	DBDataGenerator generator ;


	public DbParser() {
		connectDB();
	}

	// Parser readFile Implementation
	public void readFile(String type) {
		System.out.println("Inside DB reader!");
		generator = ReadDbDataGenerator.getInstance();
		generator.generate(type, connection);
	}

	// Parser writeFile Implementation
	public void writeFile(String type) {
		System.out.println("inside DB writeFile!");
		generator = WriteDbDataGenerator.getInstance();
		generator.generate(type, connection);
	}

	
	// database connection
	public void connectDB() {

		try {
			connection = DriverManager.getConnection(url1, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in connecting to Database" + e.getMessage());
		}
		if (connection != null) {
			System.out.println("Connected to the database covidconnection");
		}
	}
}