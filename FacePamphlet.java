/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.Canvas;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	// Method: init
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		
		// Add NORTH side interactors
		name = new JLabel("Name: ");
		nameField = new JTextField(TEXT_FIELD_SIZE);
		nameField.addActionListener(this);
		add = new JButton("Add");
		delete = new JButton("Delete");
		lookUp = new JButton("Lookup");
		
		add(name, NORTH);
		add(nameField, NORTH);
		add(add, NORTH);
		add(delete, NORTH);
		add(lookUp, NORTH);
		
		// Add WEST side interactors
		changeStatus = new JTextField(TEXT_FIELD_SIZE);
		changeStatus.setActionCommand("Change Status");
		changeStatus.addActionListener(this);
		changeStatusBTN = new JButton("Change Status");
		changeStatusBTN.setActionCommand("Change Status");
		
		changePic = new JTextField(TEXT_FIELD_SIZE);
		changePic.setActionCommand("Change Picture");
		changePic.addActionListener(this);
		changePicBTN = new JButton("Change Picture");
		changePicBTN.setActionCommand("Change Picture");
		
		addFriend = new JTextField(TEXT_FIELD_SIZE);
		addFriend.setActionCommand("Add Friend");
		addFriend.addActionListener(this);
		addFriendBTN = new JButton("Add Friend");
		addFriend.setActionCommand("Add Friend");
		
		add(changeStatus, WEST);
		add(changeStatusBTN, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(changePic, WEST);
		add(changePicBTN, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(addFriend, WEST);
		add(addFriendBTN, WEST);
		
		addActionListeners();

		// Initialize Face Pamphlet Class Stuff
		faces = new FacePamphletDatabase();	// Initialize Face Pamphlet Stuff
		currentProfile = null;	// Initialize Current Profile
		canvas = new FacePamphletCanvas();	// Initialize Canvas
		add(canvas);
    }
    
	// Method actionPerformed
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	
    	// If buttons without actionCommands are clicked. 
    	String cmd = nameField.getText();
    	if (!cmd.equals("")) {
        	if (e.getSource() == add) {
        		// If the name does not exist in the database, add the profile to the database.
        		addProfile(cmd);
        		
        	} else if (e.getSource() == delete) {
        		// Delete profile from everywhere.
        		deleteProfile(cmd);
        		
        	} else if (e.getSource() == lookUp) {
        		// Look up profile and display it.
        		lookUp(cmd);
        	}
    	}
    	
    	// If buttons that demand actionCommands are clicked.
    	if (e.getActionCommand() == "Change Status") {
    		String status = changeStatus.getText();
    		changeStatus(status);
    		
    	} else if (e.getActionCommand() == "Change Picture") {
    		String picture = changePic.getText();
    		changePicture(picture);
    		
    	} else if (e.getActionCommand() == "Add Friend") {
    		String friend = addFriend.getText();
    		addFriend(friend);
 
    	}
    	
	}

    // Method: addProfile
    // Deals with adding a profile to the database and the screen
	private void addProfile(String cmd) {
		if (faces.containsProfile(cmd)) {
			currentProfile = faces.getProfile(cmd);
			// Grab the profile user entered and update the display.
			canvas.displayProfile(currentProfile);
			canvas.showMessage("A profile with the name " + currentProfile.getName() + " already exists");
		} else {
			FacePamphletProfile newProfile = new FacePamphletProfile(cmd);
			faces.addProfile(newProfile);
			println("Add: new profile: " + newProfile.toString());
			currentProfile = newProfile;
			// Update display with current profile
			canvas.displayProfile(currentProfile);
			canvas.showMessage("New profile created");
		}
	}

	// Method: deleteProfile
	// Deals with deleting a profile and checks if its in the database already
	private void deleteProfile(String cmd) {
		String deleteHow = "";
		if (faces.containsProfile(cmd)) {
			deleteHow = ("Profile of " + faces.getProfile(cmd).getName() + " deleted.");
			faces.deleteProfile(cmd);
		} else {
			deleteHow = ("A profile with the name " + cmd + " does not exist. Stop deleting random shit.");
		}
      
		// Remove everything from the display. Than display nothing. 
		currentProfile = null;
		canvas.displayProfile(currentProfile);
		canvas.showMessage(deleteHow);
	}

	// Method: lookUp
	// Looks up the entered name and displays it on the canvas. 
	private void lookUp(String cmd) {
		String lookHow;
		if (faces.containsProfile(cmd)) {
			currentProfile = faces.getProfile(cmd);
			// update the display with current profile
			lookHow = "Displaying " + cmd;
		} else {
			// Remove everything from the display. Than display it.
			currentProfile = null;
			lookHow = "A profile with the name " + cmd + " does not exist";
		}
		canvas.displayProfile(currentProfile);
		canvas.showMessage(lookHow);
	}

	// Method: changeStatus
	// Changes the status of the current profile
	private void changeStatus(String status) {
		if (!status.equals("")) {
			String statusMessage = "";
			// Update the status of the current profile. If its not null.
			if (currentProfile != null) {
				currentProfile.setStatus(status);
				// Re-update the current profile with the latest status. 
				canvas.displayProfile(currentProfile);
				statusMessage = "Status updated to: " + status;
			} else {
				statusMessage = "Please select a profile to change the status of";
			}
			// Show the appropriate message.
			canvas.showMessage(statusMessage);
		}
	}

	// Method: changePicture
	// Change the picture of the current profile
	private void changePicture(String picture) {
		if (!picture.equals("")) {
			String pictureMessage = "";
			if (currentProfile != null) {
				GImage image = null;
				try {
					image = new GImage(picture);
				} catch (ErrorException ex) {
					// Code that is executed if the filename fails 
					pictureMessage = "Unable to open image file: " + picture;
				}
				
				if (image != null) {
					currentProfile.setImage(image);	// set the current profiles image to the one just uploaded
					// Update the display (image).
					canvas.displayProfile(currentProfile);
					pictureMessage = "Picture updated";
				}
				
			} else {
				pictureMessage = "Select a profile to change the picture of";
			}
			canvas.showMessage(pictureMessage);
		}
	}

	// Method: addFriend
	// Adds the method to the database and the screen
	private void addFriend(String friend) {
		if (!friend.equals("")) {
			// If the friend named entered in is valid
			String friendMessage = "";
			if (currentProfile != null ) {
				// Make sure name entered is a valid name in database
				if (faces.containsProfile(friend)) {
					// If all qualifications fit well:
					// This function returns true or false. True if it didn't exist. False if it did exist already. 
					if (currentProfile.addFriend(friend)) {
						faces.getProfile(friend).addFriend(currentProfile.getName());
						canvas.displayProfile(currentProfile);
						friendMessage = "Congrats! " + friend +  " added as a friend";
					} else {
						friendMessage = currentProfile.getName() + " already has " + friend + " as a friend.";
					}
					
				} else {
					friendMessage = friend + " does not exist.";
				}
				
			} else {
				friendMessage = "Please select a profile to add friend to.";
			}
			canvas.showMessage(friendMessage);
		}
	}

    
    // Private Instance Variables - Interactors
    	// NORTH
    private JLabel name;
    private JTextField nameField;
    private JButton add;
    private JButton delete;
    private JButton lookUp;
    	// WEST
    private JTextField changeStatus;
    private JButton changeStatusBTN;
    private JTextField changePic;
    private JButton changePicBTN;
    private JTextField addFriend;
    private JButton addFriendBTN;
    
    	// Face Pamphlet Classes
    private FacePamphletDatabase faces;
    private FacePamphletProfile currentProfile;	// The current profile 
    private FacePamphletCanvas canvas;
}
