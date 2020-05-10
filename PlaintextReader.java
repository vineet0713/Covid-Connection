// Swetha Valluru, Vineet Joshi
// Professor Rani Mikkilineni
// SupplierPortal.java
// This class parses the input files and populates the DataStore.

import java.lang.Exception;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

// import java.text.SimpleDateFormat;

// import java.util.Date;
import java.util.ArrayList;

public class PlaintextReader {
	public void parsePersonFile(String filename, String type) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) { continue; }
				DataStore.getInstance().addAccount(line, type, false);
			}
			br.close();
		} catch (Exception e) { return; }
	}
	// Keep this method for parsing Equipment and Message data!
	private ArrayList<String> parseLine(String line, char separator) {
		ArrayList<String> entry = new ArrayList<String>();
		StringBuilder currentString = new StringBuilder();
		for (int i = 0; i < line.length(); ++i) {
			char c = line.charAt(i);
			if (c == separator) {
				entry.add(currentString.toString());
				currentString = new StringBuilder();
				continue;
			}
			currentString.append(c);
		}
		entry.add(currentString.toString());
		return entry;
	}
}