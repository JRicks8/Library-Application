package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import Backend.BookLists;

public class App {

	private JFrame frmLibraryApp;
	private JTextField searchField;
	
	private File booksDataFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmLibraryApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}
	
	private void initialize() {
		frmLibraryApp = new JFrame();
		frmLibraryApp.getContentPane().setBackground(new Color(192, 192, 192));
		
		JPanel sidebar = new JPanel();
		frmLibraryApp.getContentPane().add(sidebar, BorderLayout.WEST);
		
		searchField = new JTextField();
		sidebar.add(searchField);
		searchField.setColumns(10);
		
		JList<?> bookList = new JList<Object>();
		sidebar.add(bookList);
		
		final JPanel header = new JPanel();
		frmLibraryApp.getContentPane().add(header, BorderLayout.NORTH);
		
		JButton btnImportData = new JButton("Load Data");
		btnImportData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (booksDataFile != null && booksDataFile.exists()) {
					System.out.println("Request Received.");
					
					//books.AddBooksFromFile(booksDataFile.getAbsolutePath());
				} else {
					// popup error window
					Error.createWindow("ERR: Selected file is null or cannot be found. Please re-select the file.");
				}
			}
		});
		
		final JLabel lblSelectedFile = new JLabel("File Selected: None");
		header.add(lblSelectedFile);
		
		JButton btnBrowseFiles = new JButton("Browse");
		btnBrowseFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileFilter() {
					public String getDescription() {
						return "CSV Files (*.csv)";
					}
					
					public boolean accept(File f) {
						if (f.isDirectory()) {
							return true;
						} else {
							return f.getName().toLowerCase().endsWith(".csv");
						}
					}
				});
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(header);
				if (result == JFileChooser.APPROVE_OPTION) {
					bookData = fileChooser.getSelectedFile();
					lblSelectedFile.setText("File Selected: " + bookData.getName());
					System.out.println("Selected file: " + bookData.getAbsolutePath());
				}
			}
		});
		header.add(btnBrowseFiles);
		header.add(btnImportData);
		
		JScrollPane content = new JScrollPane();
		frmLibraryApp.getContentPane().add(content, BorderLayout.CENTER);
		frmLibraryApp.setTitle("Library App");
		frmLibraryApp.setBounds(100, 100, 498, 567);
		frmLibraryApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
