package Backend;

public class Testing {
	
	public static void main(String[] args) throws Exception  
	{  
		BookArrayList testList = new BookArrayList();
		//testList.AddBooksFromFile("C:\\books.csv");
		String[] test = "egg,toast,,waffle".split(",");
		for(String string : test) {
			if(string == "") {
				System.out.println("blank");
			}
			
		}
	} 
}
