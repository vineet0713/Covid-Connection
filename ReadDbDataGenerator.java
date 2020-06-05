// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// ReadDbDataGenerator.java
// This class handles modifying DB data - part of Strategy Pattern

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ReadDbDataGenerator implements DBDataGenerator {
	Connection connection;
	
	private static final ReadDbDataGenerator instance = new ReadDbDataGenerator();

	public static ReadDbDataGenerator getInstance() {
		return instance;
	}
	@Override
	public void generate(String type, Connection connection) {
		// TODO Auto-generated method stub
		try {
			if (type.equals("buyer")) {
				readBuyerDbData(connection);
			} else if (type.equals("supplier")) {
				readSupplierDbData(connection);
			} else if (type.equals("item")) {
				readItemDbData(connection);
			}
		} catch (Exception e) {
			return;
		}

	}
	
	// method to read Buyer Data
		private void readBuyerDbData(Connection connection) {
			Statement stmt;

			try {
				stmt = connection.createStatement();
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
				System.out.println("Error in reading Buyer data " + e.getMessage());
			}

		}

		// Method to read Supplier Data
		private void readSupplierDbData(Connection connection) {
			Statement stmt;
			try {
				Map<String, HashSet<String>> result = new HashMap<>();
				stmt = connection.createStatement();
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
				System.out.println("Error in reading Supplier data " + e.getMessage());
			}

		}

		// method to read Item Data
		private void readItemDbData(Connection connection) {

			Statement stmt;
			try {
				stmt = connection.createStatement();
				HashSet<SupplierResponse> responses = new HashSet<SupplierResponse>();
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
					// TODO: Change this to support multiple supplier responses for an item!
					// parsedItem = new Item(id, buyerName, requestCategory, quantity, location,
					// supplierName, supplierPrice, response);
					if (supplierName != null) {
						responses.add(new SupplierResponse(supplierName, supplierPrice, response));
						//parsedItem = new Item(id, buyerName, requestCategory, quantity, location, responses);
						parsedItem = Item.builder()
										.id(id)
										.buyer(buyerName)
										.category(requestCategory)
										.quantity(quantity)
										.location(location)
										.responses(responses)
										.build();
						System.out.println(id + " , " + buyerName + " , " + requestCategory + " , " + quantity + " , "
								+ location + " , " + supplierName + " , " + supplierPrice + " , " + response);
					} else {
						//parsedItem = new Item(id, buyerName, requestCategory, quantity, location);
						parsedItem = Item.builder()
								.id(id)
								.buyer(buyerName)
								.category(requestCategory)
								.quantity(quantity)
								.location(location)
								.build();
						System.out.println(
								id + " , " + buyerName + " , " + requestCategory + " , " + quantity + " , " + location);
					}

					DataStore.getInstance().addItem(parsedItem);
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error in reading Item data " + e.getMessage());
			}

		}
}