package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import Backend.BookLinkedList;
import Backend.BookList;
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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;


public class App implements ListSelectionListener {
	
	private JFrame frmLibraryApp;
	
	private File booksDataFile;
	private BookLinkedList booksData;
	private JTextField bookIDSearchField;
	private DefaultListModel<Book> listMod;
	private JList<Book> searchResults;
	private JTable bookInfoTable;
	private JLabel lblBookTitle;
	private JLabel lblBookImage;
	private JTextField isbnSearchField;

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
		FlowLayout flowLayout = (FlowLayout) header.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frmLibraryApp.getContentPane().add(header, BorderLayout.NORTH);
		
		// Import Data button
		JButton btnImportData = new JButton("Load Data");
		btnImportData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (booksDataFile != null && booksDataFile.exists()) {
					System.out.println("Starting to parse data...");
					
					booksData = new BookLinkedList();
					booksData.AddBooksFromFile(booksDataFile.getAbsolutePath());
					System.out.println("Done!");
					OnLoadNewFile();
				} else {
					// popup error window
					Error.createWindow(Error.ERR_NOFILE);
				}
			}
		});
		
		// small text label that shows the selected file to import data from
		final JLabel lblSelectedFile = new JLabel("File Selected: None");
		header.add(lblSelectedFile);
		
		// button that shows the file browser
		JButton btnBrowseFiles = new JButton("Browse");
		btnBrowseFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// make the file browser after clicking the button
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
					// if a valid file has been selected, update the program
					booksDataFile = fileChooser.getSelectedFile();
					lblSelectedFile.setText("File Selected: " + booksDataFile.getName());
					System.out.println("Selected file: " + booksDataFile.getAbsolutePath());
				}
			}
		});
		header.add(btnBrowseFiles);
		header.add(btnImportData);
		
		// left sidebar
		JPanel sidebar = new JPanel();
		frmLibraryApp.getContentPane().add(sidebar, BorderLayout.WEST);
		
		// scrollpane that contains the search results
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMaximumSize(new Dimension(400, 100000));
		
		// Ascending/Descending checkbox
		JCheckBox chckbxAscending = new JCheckBox("Ascending");
		chckbxAscending.setSelected(true);
		
		// on changing Ascending/descending checkbox, reverse the list
		chckbxAscending.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (booksData == null) {
					Error.createWindow(Error.ERR_NODATA);
					return;
				}
				listMod.clear();
				booksData.ReverseBookOrder();
				listMod.addAll(booksData.books);
			}
		});
		
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
		sidebar.setLayout(new MigLayout("", "[50.00px][6px][116.00px,grow][6px][56.00px][76.00px]", "[23px][][481px]"));
		
		// sorting filter combo box
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setToolTipText("Sort By...");
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Title", "Publication Year", "Author"}));
		comboBox.setSelectedIndex(0);
		
		// upon changing combo box sorting options, re-sort the data
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (booksData == null) {
					Error.createWindow(Error.ERR_NODATA);
					return;
				}
				System.out.println("Sorting method: " + comboBox.getSelectedItem().toString());
				System.out.println(comboBox.getSelectedItem().toString() == "Author");
				// clear the list of results, then sort. then add the sorted list to the results.
				listMod.clear();
				switch (comboBox.getSelectedItem().toString()) {
					case "Title": 
						break;
					case "Publication Year": 
						booksData.SortByPublicationYear();
						break;
					case "Author": 
						booksData.SortByAuthor();
						break;
					default: 
						System.out.println("err");
				}
				// if descending order, then reverse the book order
				if (!chckbxAscending.isSelected()) booksData.ReverseBookOrder();
				listMod.addAll(booksData.books);
			}
		});
		
		JLabel lblBookID = new JLabel("Book ID:");
		sidebar.add(lblBookID, "cell 0 0,alignx right");
		
		JLabel lblISBN = new JLabel("ISBN:");
		sidebar.add(lblISBN, "cell 0 1,alignx right");
		
		// book ID search field
		bookIDSearchField = new JTextField();
		bookIDSearchField.setColumns(10);
		sidebar.add(bookIDSearchField, "cell 2 0,growx,aligny center");
		
		// button for searching by book ID
		JButton btnIDSearch = new JButton("Search");
		sidebar.add(btnIDSearch, "cell 4 0,growx,aligny top");
		btnIDSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (booksData == null) {
					Error.createWindow(Error.ERR_NODATA);
					return;
				}
				Book book = booksData.SearchByID(bookIDSearchField.getText());
				if (book == null) {
					Error.createWindow(Error.ERR_NO_RESULTS);
					return;
				}
				listMod.clear();
				listMod.addElement(book);
			}
		});
		
		// field for searching for ISBN
		isbnSearchField = new JTextField();
		isbnSearchField.setColumns(10);
		sidebar.add(isbnSearchField, "cell 2 1,growx");
		
		// button for searching by ISBN
		JButton btnISBNSearch = new JButton("Search");
		sidebar.add(btnISBNSearch, "cell 4 1,growx");
		sidebar.add(comboBox, "cell 5 1,alignx left,aligny center");
		btnISBNSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (booksData == null) {
					Error.createWindow(Error.ERR_NODATA);
					return;
				}
				//TODO: search by isbn
			}
		});
		
		// other settings
		searchResults.setVisibleRowCount(6);
		searchResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchResults.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setViewportView(searchResults);
		sidebar.add(scrollPane, "cell 0 2 6 1,grow");
		sidebar.add(chckbxAscending, "cell 5 0,alignx left,aligny top");
		
		// center viewport, contains the book information display
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frmLibraryApp.getContentPane().add(desktopPane, BorderLayout.CENTER);
		desktopPane.setLayout(new BorderLayout(10, 10));
		
		// book info title
		lblBookTitle = new JLabel("");
		lblBookTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
		desktopPane.add(lblBookTitle, BorderLayout.NORTH);
		
		// book info image
		lblBookImage = new JLabel("");
		lblBookImage.setIcon(null);
		lblBookImage.setVerticalAlignment(SwingConstants.TOP);
		desktopPane.add(lblBookImage, BorderLayout.EAST);
		
		// book info table
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
		frmLibraryApp.setBounds(100, 100, 800, 600);
		frmLibraryApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void OnLoadNewFile() {
		listMod.clear();
		listMod.addAll(booksData.books);
	}
	
	// overrides selection function in a JList
	@Override
	public void valueChanged(ListSelectionEvent e) {
	    System.out.println("changed selection!");
	    
	    if (searchResults.getSelectedValue() == null) return;
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
					{"ISBN-13:", selectedBook.isbn13},
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
	    			Error.createWindow(Error.ERR_MALFORMED_URL);
	    			//e1.printStackTrace();
	    		} catch (IOException e1) {
	    			Error.createWindow(Error.ERR_IO_EXCEPTION);
	    			//e1.printStackTrace();
	    		} catch (URISyntaxException e1) {
	    			Error.createWindow(Error.ERR_URI_SYNTAX_EXCEPTION);
	    			//e1.printStackTrace();
	    		}
	    		
	    		if (bookImage != null) lblBookImage.setIcon(new ImageIcon(bookImage));
	    	}
	    });
	}
}
