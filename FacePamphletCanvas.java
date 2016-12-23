/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	// CONSTANTS
	private static final int A_LITTLE_EXTRA = 5;
	
	// Constructor
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
		currentMessage = null;
	}

	// Method: showMessage
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		// Put a message at the direct center of the screen.
		if (msg != "") {
			if (currentMessage != null) remove(currentMessage);
			currentMessage = new GLabel(msg);
			currentMessage.setFont(MESSAGE_FONT);
			add(currentMessage, (getWidth()/2) - (currentMessage.getWidth()/2), getHeight() - BOTTOM_MESSAGE_MARGIN);
		}
	}
	
	// Method: displayProfile
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		
		// Remove everything from the screen first. 
		removeAll();
		
		// If profile is null, do nothing.
		if (profile != null) {
			// Set up name
			GLabel name = setUpName(profile);
			
			// Sets up image/box
			setUpImage(profile, name);
			
			// Set Status
			setStatus(profile, name);
			
		}
		
	}

	// Method: setStatus
	// Set up the status of page paper.
	private void setStatus(FacePamphletProfile profile, GLabel name) {
		String cStatus = "";
		if (!profile.getStatus().equals("")) {
			cStatus = profile.getName() + " is " + profile.getStatus();
		} else {
			cStatus = "No Current Status";
		}
		GLabel status = new GLabel(cStatus);
		status.setFont(PROFILE_STATUS_FONT);
		add(status, LEFT_MARGIN, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + status.getAscent());
		
		// Set up list of friends
		// Add "Friends:" label
		GLabel friendsLabel = new GLabel("Friends:");
		friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsLabel, getWidth()/2, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN);
		// Loop through list of friends using the iterator and print all of them in friends list.
		Iterator<String> friend = profile.getFriends();
		double y = TOP_MARGIN + name.getAscent() + IMAGE_MARGIN + A_LITTLE_EXTRA;
		while(friend.hasNext()) {
			GLabel friendName = new GLabel(friend.next());
			friendName.setFont(PROFILE_FRIEND_FONT);
			add(friendName, getWidth()/2, y + friendName.getAscent());
			y += friendName.getAscent() + A_LITTLE_EXTRA;
		}
	}

	// Method: setUpName
	// Set up the name GLabel
	private GLabel setUpName(FacePamphletProfile profile) {
		GLabel name = new GLabel(profile.getName());
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		add(name, LEFT_MARGIN, TOP_MARGIN + name.getAscent());
		return name;
	}

	// Method: setUpImage
	// Depending on whether there's an image there or not, it sets that area up a certain way.
	private void setUpImage(FacePamphletProfile profile, GLabel name) {
		GImage profileImg = profile.getImage();
		// Set up Image/Box
		if (profileImg == null) {
			// Show the "No Image" box.
			GRect imageBox = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			imageBox.setFilled(false);
			add(imageBox, LEFT_MARGIN, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN);
			// Show the "No Image" label.
			GLabel noImage = new GLabel("No Image");
			noImage.setFont(PROFILE_IMAGE_FONT);
			add(noImage, LEFT_MARGIN + IMAGE_WIDTH/2 - noImage.getWidth()/2, (TOP_MARGIN + name.getAscent() + IMAGE_MARGIN) + IMAGE_HEIGHT/2 + noImage.getAscent()/2);
			
		} else {
			// Show the image, scaled to fit inside the box.
			profileImg.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(profileImg, LEFT_MARGIN, TOP_MARGIN + name.getAscent() + IMAGE_MARGIN);
		}
	}

	// Private Instance Variables
	private GLabel currentMessage;
}
