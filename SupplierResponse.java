// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierResponse.java
// This class represents an item in the application 'Covid Connection'.

public class SupplierResponse {
	private String name, response;
	private double price;
	public SupplierResponse(String name, double price, String response) {
		this.name = name;
		this.price = price;
		this.response = response;
	}
	public String getName() { return name; }
	public double getPrice() { return price; }
	public String getResponse() { return response; }
	public void setName(String name) { this.name = name; }
	public void setPrice(double price) { this.price = price; }
	public void setResponse(String response) { this.response = response; }
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		SupplierResponse other = (SupplierResponse)(obj);
		// This won't be an issue because Item has a HashSet of SupplierResponse,
		// and one Item cannot have multiple responses from the same supplier.
		return name.equals(other.getName());
	}
	public int hashCode() { return name.hashCode(); }
	public String toString() {
		return (name + ";" + price + ";" + response + ";");
	}
}