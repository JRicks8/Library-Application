package Backend;

import java.util.ArrayList;

public class BookArrayList extends BookList{
	
	public BookArrayList(){
		books = new ArrayList<Book>();
	}
	
	//returns the Book class in the list that matches the id. Uses binary search for ArrayLists.
	public Book SearchByID(String _id) {
		/*binary search requires a sorted list, but it would not be ideal to have to alter the 
		books list just to find a specific book, so the books list is used to make a temporary sorted list.*/
		ArrayList<Book> sortedList = new ArrayList<Book>(NewIDSortedList(books));
		//rangeStart and rangeEnd keep track of the range of indexes that may contain a match.
		int rangeStart = 0;
		int rangeEnd = sortedList.size();
;		//index is used to keep track of where we are at in the search.
		int index;
		//id of the book found at the index.
		int currentID;
		//id of the book being searched for. If cannot be parsed return null.
		int id;
		try {
			id = Integer.parseInt(_id);
		}
		catch(Exception E){
			return null;
		}
		
		
		/*in each iteration, check the index in the middle of the range. If the id at that index
		matches the id being searched for, return that book and end the search. If the id
		at that index is not a match, eliminate the side that could not contain the id being searched for.
		If only one index is left in the range and it is not the correct id, return null.*/
		while(true) {
			index = (rangeStart + rangeEnd)/2;
			currentID = Integer.parseInt(sortedList.get(index).book_id);
			
			/*this fixes a rounding issue sometimes encountered when searching for an non-existant
			id that's bigger than any id in the list. If range is 98-99, index will get stuck at 98
			due to int rounding, and the loop never ends.*/
			if(index == rangeStart && currentID < id) {
				index++;
			}
						
			if(currentID == id) {
				return sortedList.get(index);
			}
			else if(currentID > id){
				rangeEnd = index;
			}
			else if(currentID < id) {
				rangeStart = index;
			}
			//if none of the other conditions are met and there are no more indexes to search, return null.
			if(rangeStart == rangeEnd) {
				return null;
			}
		}
	}
	
	//returns the Book class in the list that matches the id. Uses binary search for ArrayLists.
	public Book SearchByISBN(String _isbn) {
		/*binary search requires a sorted list, but it would not be ideal to have to alter the 
		books list just to find a specific book, so the books list is used to make a temporary sorted list.*/
		ArrayList<Book> sortedList = new ArrayList<Book>(NewISBNSortedList(books));
		//rangeStart and rangeEnd keep track of the range of indexes that may contain a match.
		int rangeStart = 0;
		int rangeEnd = sortedList.size();
		//index is used to keep track of where we are at in the search.
		int index;
		//isbn of the book found at the index.
		int currentISBN;
		//isbn of the book being searched for. If cannot be parsed return null.
		int isbn;
		try {
			isbn = ParseInt(_isbn);
		}
		catch(Exception E){
			return null;
		}
				
		/*in each iteration, check the index in the middle of the range. If the isbn at that index
		matches the isbn being searched for, return that book and end the search. If the isbn
		at that index is not a match, eliminate the side that could not contain the isbn being searched for.
		If only one index is left in the range and it is not the correct isbn, return null.*/
		while(true) {
			index = (rangeStart + rangeEnd)/2;
			currentISBN = ParseInt(sortedList.get(index).isbn);
			
			/*this fixes a rounding issue sometimes encountered when searching for an non-existant
			isbn that's bigger than any isbn in the list. If range is 98-99, index will get stuck at 98
			due to int rounding, and the loop never ends.*/
			if(index == rangeStart && currentISBN < isbn) {
				index++;
			}
			
			if(currentISBN == isbn) {
				return sortedList.get(index);
			}
			else if(currentISBN > isbn){
				rangeEnd = index;
			}
			else if(currentISBN < isbn) {
				rangeStart = index;
			}
			//if none of the other conditions are met and there are no more indexes to search, return null.
			if(rangeStart == rangeEnd) {
				return null;
			}
		}
	}
	
	
	
	


	
}