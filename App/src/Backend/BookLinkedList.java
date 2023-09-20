package Backend;

import java.util.LinkedList;

public class BookLinkedList extends BookList{
	public BookLinkedList(){
		books = new LinkedList<Book>();
	}
	
	//returns the Book class in the list that matches the id (null if none are found). Uses linear search for LinkedLists.
	public Book SearchByID(String _id) {		
		/*loop through each book until the one with the correct id is found.
		If no book has the correct id, return null.*/
		for(Book book : books) {
			if(book.book_id.contentEquals(_id)) {
				return book;
			}
		}
		return null;
	}
	
	//returns the Book class in the list that matches the isbn (null if none are found). Uses linear search for LinkedLists.
	public Book SearchByISBN(String _isbn) {
		/*loop through each book until the one with the correct isbn is found.
		If no book has the correct isbn, return null.*/
		for(Book book : books) {
			System.out.println(_isbn + " : " + book.isbn + " : " + book.isbn.contentEquals(_isbn));
			if(book.isbn.contentEquals(_isbn)) {
				System.out.println(book.isbn);
				return book;
			}
		}
		return null;
	}
	
}
