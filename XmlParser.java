// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This class parses the XML input files and populates the DataStore.

import java.util.HashMap;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class XmlParser implements Parser {
	private HashMap<String, String> typeToFilename;
	public XmlParser(HashMap<String, String> typeToFilename) {
		this.typeToFilename = typeToFilename;
	}
	public void readFile(String type) {
		System.out.println("METHOD STUB FOR XML READFILE!");
	}
	public void writeFile(String type) {
		System.out.println("METHOD STUB FOR XML WRITEFILE!");
	}
}