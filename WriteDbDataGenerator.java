// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// ItemFileDataGenerator.java
// WriteDbDataGenerator handles modifying DB data - part of Strategy Pattern

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class WriteDbDataGenerator implements DBDataGenerator {

	Connection connection;
	
	private static final WriteDbDataGenerator instance = new WriteDbDataGenerator();

	public static WriteDbDataGenerator getInstance() {
		return instance;
	}
	
	
	@Override
	public void generate(String type, Connection connection) {
		// TODO Auto-generated method stub
		try {
			if (type.equals("buyer")) {
				generateBuyerDbData(connection);
			} else if (type.equals("supplier")) {
				generateSupplierDbData(connection);
			} else if (type.equals("item")) {
				generateItemDbData(connection);
			}
		} catch (Exception e) {
			return;
		}

	}
	
	// generation of Buyer Data
		private void generateBuyerDbData(Connection connection) {
			System.out.println("inside DB writeFile - generateBuyerFileData!");
			HashSet<String> buyers = DataStore.getInstance().getBuyers();
			Statement stmt;
			PreparedStatement preparedStatement;
			try {
				stmt = connection.createStatement();
				for (String buyerName : buyers) {
					String sql = "SELECT * from buyers where buyer_name = \"" + buyerName + "\" ;";
					ResultSet rs = stmt.executeQuery(sql);
					if (!rs.next()) {
						System.out.println("Buyer does not exist data");
						String sql2 = "INSERT INTO buyers VALUES (?, ?);";
						preparedStatement = connection.prepareStatement(sql2);
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

		// Supplier Data Generation
		private void generateSupplierDbData(Connection connection) {
			System.out.println("inside DB writeFile - generateSupplierFileData!");
			HashMap<String, HashSet<String>> supplierToSubscriptions = DataStore.getInstance().getSupplierToSubscriptions();
			Statement stmt;
			PreparedStatement preparedStatement;
			try {
				stmt = connection.createStatement();
				for (String supplierName : supplierToSubscriptions.keySet()) {
					String sql = "SELECT * from suppliers where supplier_name = \"" + supplierName + "\" ;";
					ResultSet rs = stmt.executeQuery(sql);
					if (!rs.next()) {
						System.out.println("Supplier does not exist!");
						String sql2 = "INSERT INTO suppliers VALUES (?, ?);";
						preparedStatement = connection.prepareStatement(sql2);
						preparedStatement.setString(1, null);
						preparedStatement.setString(2, supplierName);
						preparedStatement.executeUpdate();
					} else {
						System.out.println(supplierName + " already exists!");
					}

					rs.close();
					// adding the supplier subscriptions
					addSupplierSubscription(supplierName, connection);
					// deleting the supplier subscriptions
					removeSupplierSubscription(supplierName, connection);

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error in generating Supplier data " + e.getMessage());
			}

		}

		// add supplier subscription method
		private void addSupplierSubscription(String supplierName, Connection connection) {

			HashSet<String> totalSubscriptions = DataStore.getInstance().getSupplierToSubscriptions().get(supplierName);
			Statement stmt;
			PreparedStatement preparedStatement;
			try {
				stmt = connection.createStatement();
				for (String subscription : totalSubscriptions) {
					if (!subscription.isEmpty()) {

						String sql3 = "SELECT * from supplier_subcriptions "
								+ "where supplier_id = (select supplier_id from suppliers where supplier_name =\""
								+ supplierName + "\")" + " and subscription_category = \"" + subscription + "\" ;";
						ResultSet rs2 = stmt.executeQuery(sql3);
						if (!rs2.next()) {
							System.out.println(supplierName + " is not subscribed to " + subscription);
							String sql = "INSERT INTO supplier_subcriptions VALUES ((select supplier_id from suppliers where supplier_name = ?), ?);";
							preparedStatement = connection.prepareStatement(sql);
							preparedStatement.setString(1, supplierName);
							preparedStatement.setString(2, subscription);
							preparedStatement.executeUpdate();
						} else {
							System.out.println(supplierName + " is already subscribed to " + subscription);
						}
						rs2.close();
					}

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error in adding Supplier Subscription " + e.getMessage());
			}

		}

		// remove supplier subscription method
		private void removeSupplierSubscription(String supplierName, Connection connection) {

			HashSet<String> totalSubscriptions = DataStore.getInstance().getSupplierToSubscriptions().get(supplierName);
			String inClause = totalSubscriptions.stream().map(subscription -> "\"" + subscription + "\"")
					.collect(Collectors.joining(","));
			System.out.println(supplierName + " is subscribed to  " + inClause);

			Statement stmt;
			PreparedStatement preparedStatement;
			try {
				stmt = connection.createStatement();
				String sql = "SELECT supplier_id,subscription_category  from supplier_subcriptions where supplier_id = (select supplier_id from suppliers where "
						+ "supplier_name = \"" + supplierName + "\") and subscription_category not in ( " + inClause + ");";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {

					int suppName = rs.getInt("supplier_id");
					String supplierCategory = rs.getString("subscription_category");
					System.out.println("deleting the" + supplierName + " subcription to " + supplierCategory);
					String sql2 = "DELETE from supplier_subcriptions where supplier_id = ? and subscription_category = ?";
					preparedStatement = connection.prepareStatement(sql2);
					preparedStatement.setInt(1, suppName);
					preparedStatement.setString(2, supplierCategory);
					preparedStatement.executeUpdate();
				}
				rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error in removing Supplier Subscription " + e.getMessage());
			}
		}

		// generate Item data
		private void generateItemDbData(Connection connection) {
			System.out.println("Inside generation of Item data");
			// call to adding buyer Requests
			addBuyerRequests(connection);
			// call to adding supplier responses to buyer requests
			addSuplierResponses(connection);
		}

		private void addBuyerRequests(Connection connection) {
			HashSet<Item> items = DataStore.getInstance().getItems();
			System.out.println(items);
			Statement stmt;
			PreparedStatement preparedStatement;
			try {
				stmt = connection.createStatement();
				for (Item item : items) {
					int id = item.getId();
					String buyerName = item.getBuyer();
					String category = item.getCategory();
					String location = item.getLocation();
					int quantity = item.getQuantity();
					String sql = "select * from buyer_requests where request_id =" + id + ";";
					ResultSet rs = stmt.executeQuery(sql);
					if (!rs.next()) {
						System.out.println(buyerName + " made a new request " + id);
						String sql1 = "INSERT INTO buyer_requests VALUES (?,?,?,(select buyer_id from buyers where buyer_name = ?),?);";
						preparedStatement = connection.prepareStatement(sql1);
						preparedStatement.setInt(1, id);
						preparedStatement.setString(2, category);
						preparedStatement.setInt(3, quantity);
						preparedStatement.setString(4, buyerName);
						preparedStatement.setString(5, location);
						preparedStatement.executeUpdate();
					} else {
						System.out.println(buyerName + "'s request " + id + " already exists in DB");
					}
					rs.close();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error in generating Item data" + e.getMessage());
			}
		}

		// method to add the supplier responses to the buyer requests
		private void addSuplierResponses(Connection connection) {
			HashSet<Item> items = DataStore.getInstance().getItems();
			System.out.println(items);
			Statement stmt;
			PreparedStatement preparedStatement;
			try {
				stmt = connection.createStatement();
				for (Item item : items) {
					HashSet<SupplierResponse> responses = item.getResponses();
					int id = item.getId();
					for (SupplierResponse response : responses) {

						String sql = "select * from supplier_responses where request_id =" + id + " "
								+ "and supplier_id = (select supplier_id from suppliers where supplier_name = \""
								+ response.getName() + "\");";
						ResultSet rs = stmt.executeQuery(sql);
						if (!rs.next()) {

							String supplierName = response.getName();
							String supplierResponse = response.getResponse();
							Double price = response.getPrice();
							String sql1 = "INSERT INTO supplier_responses VALUES (?,(select supplier_id from suppliers where supplier_name = ?),?,?);";
							preparedStatement = connection.prepareStatement(sql1);
							preparedStatement.setInt(1, id);
							preparedStatement.setString(2, supplierName);
							preparedStatement.setString(3, supplierResponse);
							preparedStatement.setDouble(4, price);
							preparedStatement.executeUpdate();
							System.out.println(response.getName() + " gave a new response to request " + id
									+ " : Added to supplier_responses");
						} else {
							System.out.println(response.getName() + " has already responded to request " + id);
						}
						rs.close();
					}

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error in generating Item data" + e.getMessage());
			}
		}
}