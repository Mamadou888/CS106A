/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import acm.util.*;
import java.io.*;
import java.util.*;

public class HangmanLexicon {

	// Instance Variables.
	private ArrayList<String> wordList;
	
	// Method: HangmanLexicon
	// This method serves as the constructor of the game. It fetches the file with words and adds all the words to the array wordList.
	public HangmanLexicon() {
		wordList = new ArrayList<String>();
		try {
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while (true) {
				String line = rd.readLine();
				if (line == null) break;
				wordList.add(line);
			}
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}
	
	// Method: getWordCount
	// This method returns the number of words in the lexicon.
	public int getWordCount() {
		return wordList.size();
	}

	// Method: getWord
	// This method returns the word at the specified index.
	public String getWord(int index) {
		return (wordList.get(index));
	};
	
}
