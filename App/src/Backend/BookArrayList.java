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
		
		//skip the first line: it should contain only the names of each column rather than usable data.
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
			currentBook.isbn = splitLine.get(5); //skip isbn13, which is splitLine[6]
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
	
	//prints the values of every book in the books list. 
	public void PrintBooks() {
		for(Book currentBook : books) {
			System.out.println("book id: " + currentBook.book_id + ", goodreads book id: " + currentBook.goodreads_book_id + ", best book id: " + currentBook.best_book_id + ", work id: " + currentBook.work_id + ", books count: " + currentBook.books_count + ", isbn: " + currentBook.isbn + ", authors: " + currentBook.authors + ", original publication year: " + currentBook.original_publication_year + ", original title: " + currentBook.original_title + ", title: " + currentBook.title + ", langauge code: " + currentBook.language_code + ", average rating: " + currentBook.average_rating + ", ratings count: " + currentBook.ratings_count + ", work ratings count: " + currentBook.work_ratings_count + ", work text reviews count: " + currentBook.work_text_reviews_count + ", ratings 1: " + currentBook.ratings_1 + ",ratings 2: " + currentBook.ratings_2 + ",ratings 3: " + currentBook.ratings_3 + ", ratings 4: " + currentBook.ratings_4 + ", ratings 5: " + currentBook.ratings_5 + ", image url: " + currentBook.image_url + ", small image url: " + currentBook.small_image_url);
		}
	}
}
