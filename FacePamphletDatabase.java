/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {
	
	// Constructor
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		profiles = new ArrayList<FacePamphletProfile>();	// Initializes the Arraylist that will hold all the profiles.
		profileMap = new HashMap<String, FacePamphletProfile>(); //Initializes the HashMap that will hold all the profile accounts. 
	}
	
	// Method: addProfile
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
 		// If profileMap does not contain the key profile.getName, than add it.
		if (!profileMap.containsKey(profile.getName())) {
			profileMap.remove(profile);
		}
		// Adds the given profile to the database
		profileMap.put(profile.getName(), profile);
		
	}

	// Method: getProfile
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		// Returns the FacePamphletProfile associated with the name passed in the parameter from the HashMap
		return profileMap.get(name);
	}
	
	// Method: deleteProfile
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {	
		
		// Remove the given name from the HashMap of profiles, if its in there to begin with. If not, do nothing.
		if (profileMap.containsKey(name)) {
			
			// Removes the name/friend from each friend. 
			for (String friend: profileMap.keySet()) {
				// if the element with the key friend has the name variable in the array list of friends, than remove it. 
				Iterator<String> friendFriends = profileMap.get(friend).getFriends();
				while (friendFriends.hasNext()) {
					 if (friendFriends.next().equals(name)) {
						 profileMap.get(friend).removeFriend(name);
					 }
				}	
			}
			
			profileMap.remove(name);	// Remove the name from the profileMap database.
		}
		
	}

	// Method: containsProfile
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		// If the profileMap has the key, this immediately ends. 
		if (profileMap.containsKey(name)) return true;
		
		return false;	// Otherwise
	}
	
	
	// Private Instance Variables
	ArrayList<FacePamphletProfile> profiles;
	Map<String, FacePamphletProfile> profileMap;  
}
