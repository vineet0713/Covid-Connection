// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This class parses the XML input files and populates the DataStore.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DbParser implements Parser {
	private HashMap<String, String> typeToFilename;
	Connection conn1 = null;
	String url1 = "jdbc:mysql://localhost:3306/covidconnection";
	String user = "root";
	String password = "MySQL@Password!";

	public DbParser(HashMap<String, String> typeToFilename) {
		this.typeToFilename = typeToFilename;
		connectDB();

	}

	public void readFile(String type) {
		System.out.println("Inside DB reader!");
		Statement stmt = null;

		if (type.equals("buyer")) {
			try {
				stmt = conn1.createStatement();
				String sql = "SELECT buyer_id, buyer_name from buyers;";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					// Retrieve by column name
					int buyerId = rs.getInt("buyer_id");
					String buyerName = rs.getString("buyer_name");
					System.out.println(buyerId + "   " + buyerName);
					DataStore.getInstance().addAccount(buyerName, "buyer", false);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (type.equals("supplier")) {

			try {
				Map<String, HashSet<String>> result = new HashMap<>();
				stmt = conn1.createStatement();
				String sql = "select supplier_name, subscription_category from suppliers a, supplier_subcriptions b where a.supplier_id = b.supplier_id; ";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					// Retrieve by column name
					String supplierName = rs.getString("supplier_name");
					String subscriptionCategory = rs.getString("subscription_category");
					if (result.get(supplierName) == null) {
						result.put(supplierName, new HashSet<>());
					}
					result.get(supplierName).add(subscriptionCategory);
					System.out.println(supplierName + "   " + subscriptionCategory);
				}
				result.forEach((supplier, subscriptions) -> {
					DataStore.getInstance().addSubscriptionsForSupplier(supplier, subscriptions);
				});
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (type.equals("item")) {

			try {
				stmt = conn1.createStatement();

				String sql = "select a.request_id,(select buyer_name from buyers where buyer_id = a.buyer_id) buyer_name, a.request_category, a.quantity, a.location,\r\n"
						+ "(select supplier_name from suppliers where supplier_id = d.supplier_id) supplier_name, d.supplier_response, d.supplier_quote\r\n"
						+ "from buyer_requests a left outer join  supplier_responses d on a.request_id = d.request_id;";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					// Retrieve by column name
					Item parsedItem;
					int id = rs.getInt("request_id");
					int quantity = rs.getInt("quantity");
					String buyerName = rs.getString("buyer_name");
					String requestCategory = rs.getString("request_category");
					String supplierName = rs.getString("supplier_name");
					String location = rs.getString("location");
					double supplierPrice = rs.getDouble("supplier_quote");
					String response = rs.getString("supplier_response");
					System.out.println(supplierName + "   " + requestCategory);
					parsedItem = new Item(id, buyerName, requestCategory, quantity, location, supplierName,
							supplierPrice, response);
					DataStore.getInstance().addItem(parsedItem);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void writeFile(String type) {
		System.out.println("inside DB writeFile!");
		try {
			if (type.equals("buyer")) {
				generateBuyerFileData();
			} else if (type.equals("supplier")) {
				generateSupplierFileData();
			}
		} catch (Exception e) {
			return;
		}
	}

	private void generateBuyerFileData() {
		HashSet<String> buyers = DataStore.getInstance().getBuyers();
		Statement stmt;
		PreparedStatement preparedStatement;
		try {
			stmt = conn1.createStatement();
			for (String buyerName : buyers) {
				String sql = "SELECT * from buyers where buyer_name = \"" + buyerName + "\" ;";
				ResultSet rs = stmt.executeQuery(sql);
				if (!rs.next()) {
					System.out.println("Buyer does not exist data");
					String sql2 = "INSERT INTO buyers VALUES (?, ?);";
					preparedStatement = conn1.prepareStatement(sql2);
					preparedStatement.setString(1, null);
					preparedStatement.setString(2, buyerName);
					preparedStatement.executeUpdate();
				} else {
					System.out.println(buyerName + " already exists!");
				}
				rs.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void generateSupplierFileData() {

		HashMap<String, HashSet<String>> supplierToSubscriptions = DataStore.getInstance().getSupplierToSubscriptions();
		Statement stmt;
		PreparedStatement preparedStatement, preparedStatement2;
		try {
			stmt = conn1.createStatement();
			for (String supplierName : supplierToSubscriptions.keySet()) {
				String sql = "SELECT * from suppliers where supplier_name = \"" + supplierName + "\" ;";
				ResultSet rs = stmt.executeQuery(sql);
				if (!rs.next()) {
					System.out.println("Supplier does not exist!");
					String sql2 = "INSERT INTO suppliers VALUES (?, ?);";
					preparedStatement = conn1.prepareStatement(sql2);
					preparedStatement.setString(1, null);
					preparedStatement.setString(2, supplierName);
					preparedStatement.executeUpdate();
				} else {
					System.out.println(supplierName + " already exists!");
				}
				try {
					for (String subscription : supplierToSubscriptions.get(supplierName)) {
						if (!subscription.isEmpty()) {
							String sql3 = "SELECT * from supplier_subcriptions "
									+ "where supplier_id = (select supplier_id from suppliers where supplier_name =\""
									+ supplierName + "\")" + " and subscription_category = \"" + subscription + "\" ;";
							ResultSet rs2 = stmt.executeQuery(sql3);
							if (!rs2.next()) {
								System.out.println(supplierName + " is not subscribed to " + subscription);
								String sql4 = "INSERT INTO supplier_subcriptions VALUES ((select supplier_id from suppliers where supplier_name = ?), ?);";
								preparedStatement2 = conn1.prepareStatement(sql4);
								preparedStatement2.setString(1, supplierName);
								preparedStatement2.setString(2, subscription);
								preparedStatement2.executeUpdate();
							} else {
								System.out.println(supplierName + " is already subscribed to " + subscription);
							}
							rs2.close();
						}

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rs.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void connectDB() {

		try {
			conn1 = DriverManager.getConnection(url1, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (conn1 != null) {
			System.out.println("Connected to the database covidconnection");
		}
	}
}