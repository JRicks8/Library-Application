package Backend;

public class Testing {	
	public static void main(String[] args) {
		BookList testList = new BookArrayList();
		testList.AddBooksFromFile("C:\\books.csv");
		
		System.out.println(testList.books.get(0).title);
	}
}
