package Backend;

public class Testing {
	//class is used solely for testing backend code and may be safely deleted without affecting the app.
	
	
	public static void main(String[] args) throws Exception  
	{  
		BookArrayList testList = new BookArrayList();
		testList.AddBooksFromFile("C:\\books.csv");
		testList.PrintBook(Integer.parseInt(testList.SearchByISBN("1").book_id));
	} 
	
}
