package Backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BookArrayList extends BookList{
	public ArrayList<Book> books = new ArrayList<Book>();
	
	//read from a csv file, and create a book for each line of the file.
	public void AddBooksFromFile(String _filepath) {
		
		String line = "";   
		try   
		{  
		//parsing a CSV file into BufferedReader class constructor  
		BufferedReader br = new BufferedReader(new FileReader(_filepath)); 
		
		//skip the first line: the first line should contain only the names of each column rather than usable data.
		br.readLine();
		
		//used to keep track of the index of the Book class being filled out.
		int i = 0;
	
		while ((line = br.readLine()) != null)   //returns a Boolean value  
		{  
		ArrayList<String> splitLine = SplitString(line);    // use comma as separator
		
		//System.out.println(i); //useful for debugging to see which entry causes an error.
		
		books.add(new Book());
		Book currentBook = books.get(i);
		currentBook.book_id = Integer.parseInt(splitLine.get(0));
		currentBook.goodreads_book_id = Integer.parseInt(splitLine.get(1));
		currentBook.best_book_id = Integer.parseInt(splitLine.get(2));
		currentBook.work_id = Integer.parseInt(splitLine.get(3));
		currentBook.books_count = Integer.parseInt(splitLine.get(4));
		currentBook.isbn = splitLine.get(5); 
		currentBook.isbn13 = splitLine.get(6);
		currentBook.authors = splitLine.get(7);
		currentBook.original_publication_year = Float.parseFloat(splitLine.get(8));
		currentBook.original_title = splitLine.get(9);
		currentBook.title = splitLine.get(10);
		currentBook.language_code = splitLine.get(11);
		currentBook.average_rating = Float.parseFloat(splitLine.get(12));
		currentBook.ratings_count = Integer.parseInt(splitLine.get(13));
		currentBook.work_ratings_count = Integer.parseInt(splitLine.get(14));
		currentBook.work_text_reviews_count = Integer.parseInt(splitLine.get(15));
		currentBook.ratings_1 = Integer.parseInt(splitLine.get(16));
		currentBook.ratings_2 = Integer.parseInt(splitLine.get(17));
		currentBook.ratings_3 = Integer.parseInt(splitLine.get(18));
		currentBook.ratings_4 = Integer.parseInt(splitLine.get(19));
		currentBook.ratings_5 = Integer.parseInt(splitLine.get(20));
		currentBook.image_url = splitLine.get(21);
		currentBook.small_image_url = splitLine.get(22);
		
		i++;
		}  				
		br.close();
		}   
		catch (IOException e)   
		{  
		e.printStackTrace();  
		}  
		
	}
	
	//print the values of a book.
	public void PrintBook(int _index) {
		Book currentBook = books.get(_index);
		System.out.println("book id: " + currentBook.book_id + ", goodreads book id: " + currentBook.goodreads_book_id + ", best book id: " + currentBook.best_book_id + ", work id: " + currentBook.work_id + ", books count: " + currentBook.books_count + ", isbn: " + currentBook.isbn + ", isbn13: " + currentBook.isbn13 + ", authors: " + currentBook.authors + ", original publication year: " + currentBook.original_publication_year + ", original title: " + currentBook.original_title + ", title: " + currentBook.title + ", langauge code: " + currentBook.language_code + ", average rating: " + currentBook.average_rating + ", ratings count: " + currentBook.ratings_count + ", work ratings count: " + currentBook.work_ratings_count + ", work text reviews count: " + currentBook.work_text_reviews_count + ", ratings 1: " + currentBook.ratings_1 + ",ratings 2: " + currentBook.ratings_2 + ",ratings 3: " + currentBook.ratings_3 + ", ratings 4: " + currentBook.ratings_4 + ", ratings 5: " + currentBook.ratings_5 + ", image url: " + currentBook.image_url + ", small image url: " + currentBook.small_image_url);
	}
	
	//calls PrintBook for every book in the books list. 
	public void PrintBooks() {
		for(int i=0;i<books.size();i++) {
			PrintBook(i);
		}
	}
	
	//swaps the position of two books in the list.
	public void SwapPositions(int _indexA, int _indexB) {
		Book tempBook = books.get(_indexB);
		books.set(_indexB, books.get(_indexA));
		books.set(_indexA, tempBook);
	}
	
	//sorts books by author starting with special characters, then numbers, then a and ends with z. 
	public void SortByAuthor() {
		//go through each index of the list, and compare the name at that index with all other names.
		//if a more suitable name is found for the position, swap the position of that book and the book currently at the index.
		
		//a represents the index currently being determined, b represents the index of the book being compared to, c represents the index of the characters being compared.
		//loop through each index in the books list.
		for(int a=0;a<books.size();a++) {
			//at each index, compare every book that is at a higher index.
			for(int b=a+1;b<books.size();b++) {
				String bookATitle = books.get(a).authors;
				String bookBTitle = books.get(b).authors;
				
				//for each book that is at a higher index, check if it comes before the current book in order.
				for(int c=0;c<bookATitle.length() && c<bookBTitle.length();c++) {
					//the book with the lowest valued character at the lowest index that does not contain the same
					//character for both strings comes first.
					
					//if bookA[c] has a lower value than bookB[c], book A comes first the comparison can stop.
					if(Character.getNumericValue(bookATitle.charAt(c)) < Character.getNumericValue(bookBTitle.charAt(c))) {
						break;
					}
					//if bookA[c] has a higher value than bookB[c], book B comes first the comparison can stop.
					else if(Character.getNumericValue(bookATitle.charAt(c)) > Character.getNumericValue(bookBTitle.charAt(c))) {
						SwapPositions(a,b);
						break;
					}
				}
			}
		}
	}
	
	//sorts books by publication year, with earlier books coming first.
	public void SortByPublicationYear() {
		
	}


	
}
