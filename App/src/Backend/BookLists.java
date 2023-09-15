package Backend;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;  


public class BookLists {
	
	//abstract class used to make BookArrayList and BookLinkedList classes.
	public abstract class BookList{
		public List<Book> books;
		
		
	}
	
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

				
				i++;
				}  
			System.out.println(books.get(9).book_id);
				
			br.close();
			}   
			catch (IOException e)   
			{  
			e.printStackTrace();  
			}  
			
		}
	}
	
	
	
	public static void main(String[] args) throws Exception  
	{  
		BookLists bookLists = new BookLists();
		BookArrayList testList = bookLists.new BookArrayList();
		testList.AddBooksFromFile("C:\\books.csv");
	}  
}
