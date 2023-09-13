package frontend;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Error {

   public static void createWindow(String errorMessage) {    
      JFrame frame = new JFrame("Swing Tester");

      JOptionPane.showMessageDialog(frame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
   } 
}