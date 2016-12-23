/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	
	// Instance Variables used later.
	private String listOfIC;
	private GLabel iC;
	private GLabel newGuessedWords;
	private int index;
	
	// Method: reset
	// This method resets the display so that only the scaffold appears
	public void reset() {
		GLine scaffold1 = new GLine(getWidth()/4, getHeight() - getHeight()/7, getWidth()/4, getHeight()/10);
		GLine scaffold2 = new GLine(getWidth()/4, getHeight()/10, getWidth()/2, getHeight()/10);
		GLine scaffold3 = new GLine(getWidth()/2, getHeight()/10, getWidth()/2, ROPE_LENGTH+getHeight()/10);
		add(scaffold1);
		add(scaffold2);
		add(scaffold3);
		index = 0;    // Also initializes index to 0 in order for the man to be hanged in a chronological order.
	}

	// Method: displayWord
	// This method updates the word on the screen to correspond to the current state of the game.  
	// The argument string shows what letters have been guessed so far; unguessed letters are indicated by hyphens.
	public void displayWord(String word) {
		GObject guessedWords = getElementAt(getWidth()/10, getHeight() - getHeight()/10);
		if (guessedWords == null) {
			newGuessedWords = new GLabel(word, getWidth()/10, getHeight() - getHeight()/10);
			add(newGuessedWords);
		} else {
			newGuessedWords.setLabel(word);
		}
	}

	// Method: noteIncorrectGuess
	// This method constructs the man and ensures his body parts are hanged in order. It also updates the list of incorrect guesses.
	public void noteIncorrectGuess(char letter) {
		drawBodyPart();
		updateIGList(letter);
	}
	
	// Method: drawBodyPart
	// This method draws the next body part depending on the amount of Incorrect guesses. 
	private void drawBodyPart() {
		GOval head = new GOval(HEAD_RADIUS*2, HEAD_RADIUS*2);
		GLine body = new GLine(getWidth()/2, (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10, getWidth()/2, (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH);
		switch (index) {
			case 0: add(head, (getWidth()/2 - head.getWidth()/2), ROPE_LENGTH + getHeight()/10 ); break;
			case 1: add(body); break;
			case 2: leftArm(); break;
			case 3: rightArm(); break;
			case 4: leftLeg(); break;
			case 5: rightLeg(); break;
			case 6: leftFoot(); break;
			case 7: rightFoot(); break;
		}
		index++;
	}
	
	// Method: updateIGList
	// This method updates the list of incorrect guesses. It also checks to see if the list is there, and if not, it adds the list there. 
	private void updateIGList(char letter) {
		GObject iCGO = getElementAt(getWidth()/10, getHeight() - getHeight()/15);   // IC = Incorrect Guesses
		if (iCGO == null) {
			listOfIC = "" + letter;
			iC = new GLabel(listOfIC, getWidth()/10, getHeight() - getHeight()/15);
			add(iC);
		} else {
			listOfIC += letter;
			iC.setLabel(listOfIC);
		}
	}
	
	private void rightFoot() { 				// Adds the right foot. 
		GLine foot = new GLine(getWidth()/2 - HIP_WIDTH, (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH + LEG_LENGTH, getWidth()/2 - HIP_WIDTH - FOOT_LENGTH, (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH + LEG_LENGTH);
		add(foot);
	}
	
	private void leftFoot() {				// Adds the left foot.
		GLine foot = new GLine((getWidth()/2 + HIP_WIDTH), (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH + LEG_LENGTH, (getWidth()/2 + HIP_WIDTH + FOOT_LENGTH), (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH + LEG_LENGTH);
		add(foot);
	}
	
	private void rightLeg() {				// Adds the right leg.
		GLine rightLeg1 = new GLine((getWidth()/2), (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH, (getWidth()/2 + HIP_WIDTH), (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH);
		GLine rightLeg2 = new GLine((getWidth()/2 + HIP_WIDTH), (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH, (getWidth()/2 + HIP_WIDTH), (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH + LEG_LENGTH);
		add(rightLeg1);
		add(rightLeg2);
	}
	
	private void leftLeg() {				// Adds the left leg.
		GLine leftLeg1 = new GLine(getWidth()/2, (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH, getWidth()/2 - HIP_WIDTH, (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH);
		GLine leftleg2 = new GLine(getWidth()/2 - HIP_WIDTH, (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH, getWidth()/2 - HIP_WIDTH, (ROPE_LENGTH+HEAD_RADIUS*2) + getHeight()/10 + BODY_LENGTH + LEG_LENGTH);
		add(leftLeg1);
		add(leftleg2);
	}
	
	private void leftArm() {				// Adds the left arm.
		GLine leftArm = new GLine(getWidth()/2, (ROPE_LENGTH+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD) + getHeight()/10, getWidth()/2 - UPPER_ARM_LENGTH, (ROPE_LENGTH+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD) + getHeight()/10);
		GLine leftArm2 = new GLine(getWidth()/2 - UPPER_ARM_LENGTH, (ROPE_LENGTH+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD) + getHeight()/10, getWidth()/2 - UPPER_ARM_LENGTH, (ROPE_LENGTH+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD) + getHeight()/10 + LOWER_ARM_LENGTH);
		add(leftArm);
		add(leftArm2);
	}
	
	private void rightArm() {				// Adds the right arm.
		GLine rightArm = new GLine(getWidth()/2, (ROPE_LENGTH+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD) + getHeight()/10, getWidth()/2 + UPPER_ARM_LENGTH, (ROPE_LENGTH+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD) + getHeight()/10);
		GLine rightArm2 = new GLine(getWidth()/2 + UPPER_ARM_LENGTH, (ROPE_LENGTH+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD) + getHeight()/10, getWidth()/2 + UPPER_ARM_LENGTH, (ROPE_LENGTH+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD) + getHeight()/10 + LOWER_ARM_LENGTH);
		add(rightArm);
		add(rightArm2);
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
