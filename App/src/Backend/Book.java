package Backend;

public class Book {
	public int book_id;
	public int goodreads_book_id;
	public int best_book_id;
	public int work_id;
	public int books_count;
	public String isbn;
	public String isbn13;
	public String authors;
	public float original_publication_year;
	public String original_title;
	public String title;
	public String language_code;
	public float average_rating;
	public int ratings_count;
	public int work_ratings_count;
	public int work_text_reviews_count;
	public int ratings_1;
	public int ratings_2;
	public int ratings_3;
	public int ratings_4;
	public int ratings_5;
	public String image_url;
	public String small_image_url;
	
	//constructor without parameters, all numbers are 0 and all strings are empty ("")
	public Book() {
		book_id = 0;
		goodreads_book_id = 0;
		best_book_id = 0;
		work_id = 0;
		books_count = 0;
		isbn = "";
		authors = "";
		original_publication_year = 0;
		original_title = "";
		title = "";
		language_code = "";
		average_rating = 0;
		ratings_count = 0;
		work_ratings_count = 0;
		work_text_reviews_count = 0;
		ratings_1 = 0;
		ratings_2 = 0;
		ratings_3 = 0;
		ratings_4 = 0;
		ratings_5 = 0;
		image_url = "";
		small_image_url = "";
	}
}