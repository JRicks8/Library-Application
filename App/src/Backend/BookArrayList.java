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
		String splitBy = ",";  
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
			String[] splitLine = line.split(splitBy);    // use comma as separator
			
			books.add(new Book());
			Book currentBook = books.get(i);
			currentBook.book_id = Integer.parseInt(splitLine[0]);
			currentBook.goodreads_book_id = Integer.parseInt(splitLine[1]);
			currentBook.best_book_id = Integer.parseInt(splitLine[2]);
			currentBook.work_id = Integer.parseInt(splitLine[3]);
			currentBook.books_count = Integer.parseInt(splitLine[4]);
			currentBook.isbn = Integer.parseInt(splitLine[5]); //skip isbn13, which is splitLine[6]
			currentBook.authors = splitLine[7];
			currentBook.original_publication_year = Float.parseFloat(splitLine[8]);
			currentBook.original_title = splitLine[9];
			currentBook.title = splitLine[10];
			currentBook.language_code = splitLine[11];
			currentBook.average_rating = Float.parseFloat(splitLine[12]);
			currentBook.ratings_1 = Integer.parseInt(splitLine[13]);
			currentBook.ratings_2 = Integer.parseInt(splitLine[14]);
			currentBook.ratings_3 = Integer.parseInt(splitLine[15]);
			currentBook.ratings_4 = Integer.parseInt(splitLine[16]);
			currentBook.ratings_5 = Integer.parseInt(splitLine[17]);
			currentBook.image_url = splitLine[18];
			currentBook.small_image_url = splitLine[19];
			
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
			System.out.println(currentBook.book_id + "," + currentBook.goodreads_book_id + "," + currentBook.best_book_id + "," + currentBook.work_id + "," + currentBook.books_count + "," + currentBook.isbn + "," + currentBook.authors + "," + currentBook.original_publication_year + "," + currentBook.original_title + "," + currentBook.title + "," + currentBook.language_code + "," + currentBook.average_rating + "," + currentBook.ratings_count + "," + currentBook.work_ratings_count + "," + currentBook.work_text_reviews_count + "," + currentBook.ratings_1 + "," + currentBook.ratings_2 + "," + currentBook.ratings_3 + "," + currentBook.ratings_4 + "," + currentBook.ratings_5 + "," + currentBook.image_url + "," + currentBook.small_image_url);
		}
	}
}
