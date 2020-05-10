import java.lang.Exception;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

// import java.util.ArrayList;

// import java.text.SimpleDateFormat;

public class PlaintextWriter {
	public void writeToPersonFile(String filename, String output) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)))) {
			bw.write(output);
			bw.close();
		} catch (Exception e) {
			return;
		}
	}
}