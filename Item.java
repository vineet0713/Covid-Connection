// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// Item.java
// This class represents an item in the application 'Covid Connection' - Builder Pattern.

import java.util.HashSet;

public class Item {
	private int id, quantity;
	private String buyer, category, location;
	private HashSet<SupplierResponse> responses;
	
public Item(Builder builder) {
		// TODO Auto-generated constructor stub
	this.id = builder.id;
	this.quantity = builder.quantity;
	this.buyer = builder.buyer;
	this.category = builder.category;
	this.location = builder.location;
	this.responses = builder.responses;
	}
//	public Item(int id, String buyer, String category, int quantity, String location) {
//		this.id = id;
//		this.buyer = buyer;
//		this.category = category;
//		this.quantity = quantity;
//		this.location = location;
//		this.responses = new HashSet<SupplierResponse>();
//	}
//	public Item(int id, String buyer, String category, int quantity, String location, HashSet<SupplierResponse> responses) {
//		this.id = id;
//		this.buyer = buyer;
//		this.category = category;
//		this.quantity = quantity;
//		this.location = location;
//		this.responses = responses;
//	}
	public int getId() { return id; }
	public String getBuyer() { return buyer; }
	public String getCategory() { return category; }
	public int getQuantity() { return quantity; }
	public String getLocation() { return location; }
	public HashSet<SupplierResponse> getResponses() { return responses; }
	public void addResponse(SupplierResponse response) { responses.add(response); }
	public boolean responsesExist() { return !responses.isEmpty(); }
	public boolean supplierHasResponse(String supplierName) {
		for (SupplierResponse response : responses) {
			if (response.getName().equals(supplierName)) { return true; }
		}
		return false;
	}
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		Item other = (Item)(obj);
		return id == other.getId();
	}
	public int hashCode() { return Integer.hashCode(id); }
	
	public static Item.Builder builder() {
		return new Item.Builder();
	}

	public static class Builder {
		private int id, quantity;
		private String buyer, category, location;
		private HashSet<SupplierResponse> responses = new HashSet<SupplierResponse>();

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder quantity(int quantity) {
			this.quantity = quantity;
			return this;
		}

		public Builder category(String category) {
			this.category = category;
			return this;
		}

		public Builder buyer(String buyer) {
			this.buyer = buyer;
			return this;
		}

		public Builder location(String location) {
			this.location = location;
			return this;
		}

		public Builder responses(HashSet<SupplierResponse> responses) {
			this.responses = responses;
			return this;
		}

		public Item build() {
			return new Item(this);
		}
	}

	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(id + ";");
		s.append(buyer + ";");
		s.append(category + ";");
		s.append(quantity + ";");
		s.append(location + ";");
		for (SupplierResponse response : responses) { s.append(response); }
		return s.toString();
	}
}