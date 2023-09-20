package Backend;

public class Book {
	/*all variables are stored as strings because strings can hold empty values, so blank cells in
	the csv files will not crash the program when the data is being put into Book classes.*/
	public String book_id;
	public String goodreads_book_id;
	public String best_book_id;
	public String work_id;
	public String books_count;
	public String isbn;
	public String isbn13;
	public String authors;
	public String original_publication_year;
	public String original_title;
	public String title;
	public String language_code;
	public String average_rating;
	public String ratings_count;
	public String work_ratings_count;
	public String work_text_reviews_count;
	public String ratings_1;
	public String ratings_2;
	public String ratings_3;
	public String ratings_4;
	public String ratings_5;
	public String image_url;
	public String small_image_url;
	
	//constructor without parameters, all numbers are 0 and all strings are empty ("")
	public Book() {
		book_id = "";
		goodreads_book_id = "";
		best_book_id = "";
		work_id = "";
		books_count = "";
		isbn = "";
		authors = "";
		original_publication_year = "";
		original_title = "";
		title = "";
		language_code = "";
		average_rating = "";
		ratings_count = "";
		work_ratings_count = "";
		work_text_reviews_count = "";
		ratings_1 = "";
		ratings_2 = "";
		ratings_3 = "";
		ratings_4 = "";
		ratings_5 = "";
		image_url = "";
		small_image_url = "";
	}
}