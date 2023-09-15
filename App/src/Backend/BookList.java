package Backend;

import java.util.ArrayList;
import java.util.List;

//abstract class used to make BookArrayList and BookLinkedList classes.
public abstract class BookList{
	public List<Book> books;
	
	//splits a string using commas as a delimiter, but ignores commas between quotation marks.
	protected ArrayList<String> SplitString(String _string) {
		//used to store the split string.
		ArrayList<String> splitString = new ArrayList<String>();
		//used to store the characters in the current chunk of the string.
		String currentString = "";
		//used to store the index of the current character being iterated through.
		char currentChar = ' ';
		//indicates whether commas should be ignored or not.
		boolean quotationEnclosed = false;
				
		for(int i=0;i<_string.length();i++) {
			currentChar = _string.charAt(i);
			
			//if an un-enclosed comma is encountered, add the current string to the slitString list,
			//and reset the current string to be blank.
			if(currentChar == ',' && quotationEnclosed == false) {
				splitString.add(currentString);
				currentString = "";
			}
			//if a quotation mark is encountered, toggle quotationEnclosed
			else if(currentChar == '"'){
				quotationEnclosed = !quotationEnclosed;
			}
			//if the char is not an un-enclosed comma or quotation mark, add it to the string.
			else {
				currentString += currentChar;
			}
			
		}
		//add the last chunk then return.
		splitString.add(currentString);
		return splitString;
	}
}