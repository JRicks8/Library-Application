package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import Backend.BookArrayList;
import Backend.Book;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;
import javax.swing.JDesktopPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.MatteBorder;

public class App implements ListSelectionListener {

	private JFrame frmLibraryApp;
	
	private File booksDataFile;
	private BookArrayList booksData = new BookArrayList();
	private JTextField searchField;
	private DefaultListModel<Book> listMod;
	private JList<Book> searchResults;
	private JTable bookInfoTable;
	private JLabel lblBookTitle;
	private JLabel lblBookImage;

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
		
		final JPanel header = new JPanel();
		frmLibraryApp.getContentPane().add(header, BorderLayout.NORTH);
		
		// Import Data button
		JButton btnImportData = new JButton("Load Data");
		btnImportData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (booksDataFile != null && booksDataFile.exists()) {
					System.out.println("Request Received.");
					
					booksData = new BookArrayList();
					booksData.AddBooksFromFile(booksDataFile.getAbsolutePath());
					System.out.println("Done!");
					OnLoadNewFile();
				} else {
					// popup error window
					Error.createWindow("ERR: Selected file is null or cannot be found. Please re-select the file.");
				}
			}
		});
		
		final JLabel lblSelectedFile = new JLabel("File Selected: None");
		header.add(lblSelectedFile);
		
		// file picker browser
		JButton btnBrowseFiles = new JButton("Browse");
		btnBrowseFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				// make the file filter for CSV files
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
					booksDataFile = fileChooser.getSelectedFile();
					lblSelectedFile.setText("File Selected: " + booksDataFile.getName());
					System.out.println("Selected file: " + booksDataFile.getAbsolutePath());
				}
			}
		});
		header.add(btnBrowseFiles);
		header.add(btnImportData);
		
		JPanel sidebar = new JPanel();
		frmLibraryApp.getContentPane().add(sidebar, BorderLayout.WEST);
		
		searchField = new JTextField();
		searchField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_sidebar = new GroupLayout(sidebar);
		gl_sidebar.setHorizontalGroup(
			gl_sidebar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_sidebar.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_sidebar.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
						.addComponent(searchField, Alignment.TRAILING, 147, 147, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_sidebar.setVerticalGroup(
			gl_sidebar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_sidebar.createSequentialGroup()
					.addGap(7)
					.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		// make the JList for search results
		// make the list modifier. This is where we add the elements of the list
		listMod = new DefaultListModel<Book>();
		// make the actual list
		searchResults = new JList<Book>(listMod);
		// make custom renderer for cells so that I can make it a list of books instead of text (easier to reference)
		ListCellRenderer<Book> listRender = new ListCellRenderer<Book>() {
			@Override
			public Component getListCellRendererComponent(
					JList<? extends Book> list, Book value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel thisLabel = new JLabel(value.title);
				return thisLabel;
			}
		};
		searchResults.setCellRenderer(listRender);
		// make this class the listener for item selection in the list
		searchResults.addListSelectionListener(this);
		// other settings
		searchResults.setVisibleRowCount(6);
		searchResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchResults.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(searchResults);
		sidebar.setLayout(gl_sidebar);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frmLibraryApp.getContentPane().add(desktopPane, BorderLayout.CENTER);
		desktopPane.setLayout(new BorderLayout(10, 10));
		
		lblBookTitle = new JLabel("");
		lblBookTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
		desktopPane.add(lblBookTitle, BorderLayout.NORTH);
		
		lblBookImage = new JLabel("");
		lblBookImage.setIcon(null);
		lblBookImage.setVerticalAlignment(SwingConstants.TOP);
		desktopPane.add(lblBookImage, BorderLayout.EAST);
		
		bookInfoTable = new JTable();
		bookInfoTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		bookInfoTable.setEnabled(false);
		bookInfoTable.setModel(new DefaultTableModel(
				new Object[][] {
					{"Author(s):", ""},
					{"Original Title:", ""},
					{"Publication Date:", ""},
					{"Language Code:", ""},
					{"Books Count:", ""},
					{"Ratings", null},
					{"Average Rating:", ""},
					{"Ratings Count:", ""},
					{"Work Ratings:", ""},
					{"Text Reviews:", ""},
					{"5 Stars:", ""},
					{"4 Stars:", ""},
					{"3 Stars:", ""},
					{"2 Stars:", ""},
					{"1 Star:", ""},
					{"Other Info", null},
					{"Book ID", ""},
					{"GoodReads ID:", ""},
					{"Best Book ID:", ""},
					{"Work ID:", ""},
					{"ISBN:", ""},
					{"ISBN-13:", ""},
				},
				new String[] {
					"Column 1", "Column 2"
				}
			));
		bookInfoTable.getColumnModel().getColumn(0).setPreferredWidth(86);
		bookInfoTable.getColumnModel().getColumn(1).setPreferredWidth(113);
		desktopPane.add(bookInfoTable, BorderLayout.CENTER);
		
		// final settings
		frmLibraryApp.setTitle("Library App");
		frmLibraryApp.setBounds(100, 100, 500, 600);
		frmLibraryApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void OnLoadNewFile() {
		listMod.removeAllElements();
		listMod.addAll(booksData.books);
	}
	
	// overrides selection function in a JList
	@Override
	public void valueChanged(ListSelectionEvent e) {
	    System.out.println("changed selection!");
	    
	    Book selectedBook = searchResults.getSelectedValue();
	    lblBookTitle.setText(selectedBook.title);
	    bookInfoTable.setModel(new DefaultTableModel(
				new Object[][] {
					{"Author(s):", selectedBook.authors},
					{"Original Title:", selectedBook.original_title},
					{"Publication Date:", selectedBook.original_publication_year},
					{"Language Code:", selectedBook.language_code},
					{"Books Count:", selectedBook.books_count},
					{"Ratings", null},
					{"Average Rating:", selectedBook.average_rating},
					{"Ratings Count:", selectedBook.ratings_count},
					{"Work Ratings:", selectedBook.work_ratings_count},
					{"Text Reviews:", selectedBook.work_text_reviews_count},
					{"5 Stars:", selectedBook.ratings_5},
					{"4 Stars:", selectedBook.ratings_4},
					{"3 Stars:", selectedBook.ratings_3},
					{"2 Stars:", selectedBook.ratings_2},
					{"1 Star:", selectedBook.ratings_1},
					{"Other Info", null},
					{"Book ID", selectedBook.book_id},
					{"GoodReads ID:", selectedBook.goodreads_book_id},
					{"Best Book ID:", selectedBook.best_book_id},
					{"Work ID:", selectedBook.work_id},
					{"ISBN:", selectedBook.isbn},
					{"ISBN-13:", ""},
				},
				new String[] {
					"Column 1", "Column 2"
				}
			));
	    // Load Image
	    EventQueue.invokeLater(new Runnable() {
	    	
	    	URI imageUrl;
		    BufferedImage bookImage = null;
	    	@Override
	    	public void run() {
	    		try {
	    			imageUrl = new URI(selectedBook.image_url);
	    			bookImage = ImageIO.read(imageUrl.toURL());
	    		} catch (MalformedURLException e1) {
	    			Error.createWindow("Error Loading Book Image: Malformed URL Exception");
	    			//e1.printStackTrace();
	    		} catch (IOException e1) {
	    			Error.createWindow("Error Loading Book Image: IO Exception");
	    			//e1.printStackTrace();
	    		} catch (URISyntaxException e1) {
	    			Error.createWindow("Error Loading Book Image: URI Syntax Exception");
	    			//e1.printStackTrace();
	    		}
	    		
	    		if (bookImage != null) lblBookImage.setIcon(new ImageIcon(bookImage));
	    	}
	    });
	}
}
