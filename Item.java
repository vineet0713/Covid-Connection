// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// Item.java
// This class represents an item in the application 'Covid Connection'.

public class Item {
	private int id, quantity;
	private String buyer, category, location, supplier, supplierResponse;
	private double supplierPrice;
	public Item(int id, String buyer, String category, int quantity, String location) {
		this.id = id;
		this.buyer = buyer;
		this.category = category;
		this.quantity = quantity;
		this.location = location;
	}
	public Item(int id, String buyer, String category, int quantity, String location, String supplier, double supplierPrice, String supplierResponse) {
		this.id = id;
		this.buyer = buyer;
		this.category = category;
		this.quantity = quantity;
		this.location = location;
		this.supplier = supplier;
		this.supplierPrice = supplierPrice;
		this.supplierResponse = supplierResponse;
	}
	public int getId() { return id; }
	public String getBuyer() { return buyer; }
	public String getCategory() { return category; }
	public int getQuantity() { return quantity; }
	public String getLocation() { return location; }
	public String getSupplier() { return supplier; }
	public double getSupplierPrice() { return supplierPrice; }
	public String getSupplierResponse() { return supplierResponse; }
	public void setSupplier(String supplier) { this.supplier = supplier; }
	public void setSupplierPrice(double supplierPrice) { this.supplierPrice = supplierPrice; }
	public void setSupplierResponse(String supplierResponse) { this.supplierResponse = supplierResponse; }
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		Item other = (Item)(obj);
		return id == other.getId();
	}
	public int hashCode() { return Integer.hashCode(id); }
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(id + ";");
		s.append(buyer + ";");
		s.append(category + ";");
		s.append(quantity + ";");
		s.append(location + ";");
		if (supplier != null) {
			s.append(supplier + ";");
			s.append(supplierPrice + ";");
			s.append(supplierResponse + ";");
		}
		return s.toString();
	}
}