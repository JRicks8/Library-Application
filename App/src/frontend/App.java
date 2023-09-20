package frontend;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import Backend.Book;
import Backend.BookList;
import Backend.BookArrayList;
import Backend.BookLinkedList;

public class App extends GUI {
	
	File booksDataFile;
	BookList bookList;
	
	private boolean storedAsArrayList = true;
	private boolean measuringPerformance = false;
	
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
		super();
		initialize();
	}
	
	private void initialize() {
		// setup action listeners
		ALImportButtonClicked = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (booksDataFile != null && booksDataFile.exists()) {
					System.out.println("Starting to parse data...");
					
					if (storedAsArrayList) bookList = new BookArrayList();
					else bookList = new BookLinkedList();
					
					long timeStart = System.currentTimeMillis();
					bookList.AddBooksFromFile(booksDataFile.getAbsolutePath());
					if (measuringPerformance) Error.createWindow("Performance Stats\nTotal time to parse data: " + (System.currentTimeMillis() - timeStart));
					System.out.println("Done!");
					listMod.clear();
					listMod.addAll(bookList.books);
				} else {
					// popup error window
					Error.createWindow(Error.ERR_NOFILE);
				}
			}
		};
		btnImportData.addActionListener(ALImportButtonClicked);
		
		ALBrowseButtonClicked = new ActionListener() {
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
		};
		btnBrowseFiles.addActionListener(ALBrowseButtonClicked);
		
		ALAscendingChckbxClicked = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bookList == null) {
					Error.createWindow(Error.ERR_NODATA);
					return;
				}
				listMod.clear();
				long timeStart = System.currentTimeMillis();
				bookList.ReverseBookOrder();
				if (measuringPerformance) Error.createWindow("Performance Stats\nTotal time to reverse list: " + (System.currentTimeMillis() - timeStart));
				listMod.addAll(bookList.books);
			}
		};
		chckbxReverseOrder.addActionListener(ALAscendingChckbxClicked);
		
		ALChckbxMeasurePerformance = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				measuringPerformance = chckbxPerformace.isSelected();
			}
		};
		chckbxPerformace.addActionListener(ALChckbxMeasurePerformance);
		
		ALComboSortSelection = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bookList == null) {
					Error.createWindow(Error.ERR_NODATA);
					return;
				}
				System.out.println("Sorting method: " + comboSortMethod.getSelectedItem().toString());
				// clear the list of results, then sort. then add the sorted list to the results.
				listMod.clear();
				
				long timeStart;
				switch (comboSortMethod.getSelectedItem().toString()) {
					case "Title": 
						timeStart = System.currentTimeMillis();
						bookList.SortByTitle();
						if (measuringPerformance) Error.createWindow("Performance Stats\nTotal time to sort by title: " + (System.currentTimeMillis() - timeStart));
						break;
					case "Publication Year": 
						timeStart = System.currentTimeMillis();
						bookList.SortByPublicationYear();
						if (measuringPerformance) Error.createWindow("Performance Stats\nTotal time to sort by year: " + (System.currentTimeMillis() - timeStart));
						break;
					case "Author": 
						timeStart = System.currentTimeMillis();
						bookList.SortByAuthor();
						if (measuringPerformance) Error.createWindow("Performance Stats\nTotal time to sort by author: " + (System.currentTimeMillis() - timeStart));
						break;
				}
				// if descending order, then reverse the book order
				if (!chckbxReverseOrder.isSelected()) bookList.ReverseBookOrder();
				listMod.addAll(bookList.books);
			}
		};
		comboSortMethod.addActionListener(ALComboSortSelection);
		
		ALComboListMethodSelection = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bookList == null) {
					Error.createWindow(Error.ERR_NODATA);
					return;
				}
				System.out.println("Data Stored as: " + comboListMethod.getSelectedItem().toString());
				
				switch (comboListMethod.getSelectedItem().toString()) {
					case "Array List" :
						storedAsArrayList = true;
						listMod.clear();
						bookList.books.clear();
						bookList = null;
						break;
					case "Linked List" :
						storedAsArrayList = false;
						listMod.clear();
						bookList.books.clear();
						bookList = null;
						break;
				}
				
				listMod.clear();
				Error.createWindow("Please reload the file data to switch data storage method.");
			}
		};
		comboListMethod.addActionListener(ALComboListMethodSelection);
		
		ALSearchIDButtonClicked = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bookList == null) {
					Error.createWindow(Error.ERR_NODATA);
					return;
				}
				long timeStart = System.currentTimeMillis();
				Book book = bookList.SearchByID(bookIDSearchField.getText());
				if (measuringPerformance) Error.createWindow("Performance Stats\nTotal time to search by book ID: " + (System.currentTimeMillis() - timeStart));
				if (book == null) {
					Error.createWindow(Error.ERR_NO_RESULTS);
					return;
				}
				listMod.clear();
				listMod.addElement(book);
			}
		};
		btnIDSearch.addActionListener(ALSearchIDButtonClicked);
		
		ALSearchISBNButtonClicked = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bookList == null) {
					Error.createWindow(Error.ERR_NODATA);
					return;
				}
				long timeStart = System.currentTimeMillis();
				Book book = bookList.SearchByISBN(isbnSearchField.getText());
				if (measuringPerformance) Error.createWindow("Performance Stats\nTotal time to search by book ISBN: " + (System.currentTimeMillis() - timeStart));
				System.out.println("done searching");
				if (book == null) {
					Error.createWindow(Error.ERR_NO_RESULTS);
					return;
				}
				listMod.clear();
				listMod.addElement(book);
			}
		};
		btnISBNSearch.addActionListener(ALSearchISBNButtonClicked);
	}
	
	// overrides selection function in the JList (book search results selected scroll pane)
	@Override
	public void valueChanged(ListSelectionEvent e) {
	    
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
	    		} catch (IOException e1) {
	    			Error.createWindow(Error.ERR_IO_EXCEPTION);
	    		} catch (URISyntaxException e1) {
	    			Error.createWindow(Error.ERR_URI_SYNTAX_EXCEPTION);
	    		}
	    		
	    		if (bookImage != null) lblBookImage.setIcon(new ImageIcon(bookImage));
	    	}
	    });
	}
}
