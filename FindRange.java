/*
 * File: FindRange.java
 * Name: Mamadou Diallo
 * Section Leader: Jared Bitz
 * --------------------
 * This file is the starter file for the FindRange problem. FindRange, finds the smallest and largest number in 
 * a list inputed by the user. 
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	// The SENTINEL should be a constant so it can be easily changed to another value. 
	private static final int SENTINEL = 0;
	
	public void run() {
		// In the first instance, I check to see if the first number they entered is the SENTINEL and if it is, it tells the user that values have not been entered
		// and the program breaks. 
		println("This program finds the largest and smallest numbers. Enter " + SENTINEL + " when you are finished listing your numbers.");
		int input = readInt("? ");
		if (input == SENTINEL) {
			println("No values have been entered, thus:");
		}
		// To establish the initial smaller and larger number, I set them to the the users initial input.
		int currentSmallest = input;
		int currentLargest = input;
		// The while loop is so that the program continues to ask for integers until the user enters in the SENTINEL.
		while (input != SENTINEL) {
			input =  readInt("? ");
			if (input == SENTINEL) break;
			if (input <= currentSmallest) {
				currentSmallest = input;
			} else if (input >= currentLargest) {
				currentLargest = input;
			}
		}
		// Finally, the program spits out the final values of currentSmallest and currentLargest
		println("Smallest: " + currentSmallest);
		println("Largest: " + currentLargest);
	}
}

