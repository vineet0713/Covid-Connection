// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This is the parser interface for reading input files and writing output files.

public interface Parser {
	public abstract void readFile(String type);
	public abstract void writeFile(String type);
}