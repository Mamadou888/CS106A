/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {
	// CONSTANTS
	private static final int SPACES_IN_LINE = 11;
/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		// Create String tokenizer
//		line = "Sam 83 140 228 286 426 612 486 577 836 0 0";
		Nline = line;
		lineEntry = new String[SPACES_IN_LINE+1];
		lineEntryData = new int[SPACES_IN_LINE];
		StringTokenizer tokenizer = new StringTokenizer(line);
		int tokenCount = 0;
		// Populates the String array, lineEntry, with each token (word).
		while (tokenizer.hasMoreTokens()) {
			String nextToken = tokenizer.nextToken();
			lineEntry[tokenCount] = nextToken;	
			tokenCount++;
		}
		// Get the entry name
		entryName = lineEntry[0];
		
		// At this point lineEntry is an Array of Strings that has every String part in the data set line.
		// The for loop below will add it to the lineEntryData array starting at index 0.
		for (int i = 1; i <= SPACES_IN_LINE; i++) {
			int cInt = Integer.parseInt(lineEntry[i]);
			lineEntryData[i-1] = cInt;
		}
		
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return entryName;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		// lineEntryData[decade]
		System.out.println("" + lineEntryData[decade]);
		return lineEntryData[decade];
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		return "[" + Nline + " ]";
	}
	
	// Private Instance Variables
	private String entryName;
	private String[] lineEntry;
	private int[] lineEntryData;
	private String Nline;
}

