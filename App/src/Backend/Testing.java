package Backend;

public class Testing {
	
	public static void main(String[] args) throws Exception  
	{  
		BookArrayList testList = new BookArrayList();
		testList.AddBooksFromFile("C:\\books.csv");
		testList.SortByPublicationYear();
		testList.PrintBooks();
	} 
}
