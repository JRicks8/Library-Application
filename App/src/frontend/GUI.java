package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Backend.Book;
import net.miginfocom.swing.MigLayout;

public class GUI implements ListSelectionListener {
	
	public JFrame frmLibraryApp;
	
	// buttons & interactibles
	JButton btnImportData;
	JButton btnBrowseFiles;
	JButton btnIDSearch;
	JButton btnISBNSearch;
	
	// visual elements
	JPanel header;
	JLabel lblSelectedFile;
	JCheckBox chckbxAscending;
	JComboBox<String> comboSortMethod;
	JComboBox<String> comboListMethod;
	JTextField bookIDSearchField;
	DefaultListModel<Book> listMod;
	JList<Book> searchResults;
	JTable bookInfoTable;
	JLabel lblBookTitle;
	JLabel lblBookImage;
	JTextField isbnSearchField;
	
	public GUI() {
		frmLibraryApp = new JFrame();
		frmLibraryApp.getContentPane().setBackground(new Color(192, 192, 192));
		
		header = new JPanel();
		FlowLayout flowLayout = (FlowLayout) header.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frmLibraryApp.getContentPane().add(header, BorderLayout.NORTH);
		
		// Import Data button
		btnImportData = new JButton("Load Data");
		
		// small text label that shows the selected file to import data from
		lblSelectedFile = new JLabel("File Selected: None");
		header.add(lblSelectedFile);
		
		// button that shows the file browser
		btnBrowseFiles = new JButton("Browse");
		btnBrowseFiles.addActionListener(ALBrowseButtonClicked);
		header.add(btnBrowseFiles);
		header.add(btnImportData);
		
		comboListMethod = new JComboBox<String>();
		comboListMethod.setToolTipText("Book list stored as...");
		comboListMethod.setModel(new DefaultComboBoxModel<String>(new String[] {"Array List", "Linked List"}));
		comboListMethod.setSelectedIndex(0);
		header.add(comboListMethod);
		
		// left sidebar
		JPanel sidebar = new JPanel();
		frmLibraryApp.getContentPane().add(sidebar, BorderLayout.WEST);
		
		// scrollpane that contains the search results
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMaximumSize(new Dimension(400, 100000));
		
		// Ascending/Descending checkbox
		chckbxAscending = new JCheckBox("Ascending");
		chckbxAscending.setSelected(true);
		
		// on changing Ascending/descending checkbox, reverse the list
		chckbxAscending.addActionListener(ALAscendingChckbxClicked);
		
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
		comboSortMethod = new JComboBox<String>();
		comboSortMethod.setToolTipText("Sort By...");
		comboSortMethod.setModel(new DefaultComboBoxModel<String>(new String[] {"Title", "Publication Year", "Author"}));
		comboSortMethod.setSelectedIndex(0);
		
		// upon changing combo box sorting options, re-sort the data
		comboSortMethod.addActionListener(ALComboSortSelection);
		
		JLabel lblBookID = new JLabel("Book ID:");
		sidebar.add(lblBookID, "cell 0 0,alignx right");
		
		JLabel lblISBN = new JLabel("ISBN:");
		sidebar.add(lblISBN, "cell 0 1,alignx right");
		
		// book ID search field
		bookIDSearchField = new JTextField();
		bookIDSearchField.setColumns(10);
		sidebar.add(bookIDSearchField, "cell 2 0,growx,aligny center");
		
		// button for searching by book ID
		btnIDSearch = new JButton("Search");
		sidebar.add(btnIDSearch, "cell 4 0,growx,aligny top");
		btnIDSearch.addActionListener(ALSearchIDButtonClicked);
		
		// field for searching for ISBN
		isbnSearchField = new JTextField();
		isbnSearchField.setColumns(10);
		sidebar.add(isbnSearchField, "cell 2 1,growx");
		
		// button for searching by ISBN
		btnISBNSearch = new JButton("Search");
		sidebar.add(btnISBNSearch, "cell 4 1,growx");
		sidebar.add(comboSortMethod, "cell 5 1,alignx left,aligny center");
		btnISBNSearch.addActionListener(ALSearchISBNButtonClicked);
		
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
	
	// Action listeners for the interface
	public ActionListener ALImportButtonClicked;
	public ActionListener ALBrowseButtonClicked;
	public ActionListener ALAscendingChckbxClicked;
	public ActionListener ALComboSortSelection;
	public ActionListener ALComboListMethodSelection;
	public ActionListener ALSearchIDButtonClicked;
	public ActionListener ALSearchISBNButtonClicked;
	
	// placeholder override so Eclipse doesn't get mad at me (implemented in App class)
	@Override
	public void valueChanged(ListSelectionEvent e) {}
}