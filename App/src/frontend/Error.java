package frontend;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Error {

   protected static final String ERR_NOFILE = "Error: File not found. Please reload the file.";
   protected static final String ERR_NODATA = "Error: File data is null. Please reload the file.";
   protected static final String ERR_MALFORMED_URL = "Error Loading Book Image: Malformed URL Exception";
   protected static final String ERR_IO_EXCEPTION = "Error Loading Book Image: IO Exception";
   protected static final String ERR_URI_SYNTAX_EXCEPTION = "Error Loading Book Image: URI Syntax Exception";
   protected static final String ERR_NO_RESULTS = "Search yielded no results.";

public static void createWindow(String errorMessage) {
	   
	   JFrame frame = new JFrame("Error Dialogue");

	   JOptionPane.showMessageDialog(frame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
   } 
}