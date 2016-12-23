/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.*;

import java.io.BufferedReader;
import java.util.*;
import java.io.*;

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		// Read in the database using the filename above.
		nameSurfers = new ArrayList<NameSurferEntry>();
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = rd.readLine();
				if (line == null) break;
				// Now do something with each line read.
				NameSurferEntry nameSurfer = new NameSurferEntry(line);
				
				nameSurfers.add(nameSurfer);
			}
			rd.close();
			
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
		
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		// create NameSurferEntry named potentialNamesurfer and return it based off what was found in the Arraylist of nameSurfers. 
		potentialNameSurfer = null;	// I'm using null to initialize the variable potentialNameSurfer
		for (int i = 0; i < nameSurfers.size(); i++) {
			if (nameSurfers.get(i).getName().equals(name)) {
				potentialNameSurfer = nameSurfers.get(i);
			}
		}
		// returning the NameSurferEntry
		return potentialNameSurfer;
	}
	
	
	// Private Instance Variables
	private ArrayList<NameSurferEntry> nameSurfers;
	private NameSurferEntry potentialNameSurfer;
}

