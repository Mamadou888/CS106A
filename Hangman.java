/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.util.*;
import java.awt.*;

public class Hangman extends ConsoleProgram {

	// Instance Variables
	private HangmanLexicon list = new HangmanLexicon();
	private String usersWord;
	private String guessWord;
	private Boolean isThere;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private HangmanCanvas canvas;
	private int guesses;

	// CONSTANTS
	private static final int TURNS = 8;

	// Method: init
	// This method initializes the game and adds the canvas to the right of the console.
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	// Method: run
	// This will bring the entire game together.
	public void run() {
		setFont("Times New Roman-16");	// Just to make the game more visible.
		gameSetUp();					// Sets up the beginning of the game.
		playGame();						// This is the main method that plays the game.
		checkWinOrLost();				//  When the game ends, it checks whether user has one or lost and responds accordingly.
	}

	// Method: playGame
	// This method loops through the entire game and does not end until the user has no more guesses and/or they get the word right.
	private void playGame() {
		while (guesses != 0) {
			if (guessWord.equals(usersWord)) break;
			String guessedString = readLine("Your Guess: ").toUpperCase();
			Character guessedLetter = guessedString.charAt(0);
			if (guessedString.equals(usersWord)) {
				guessWord = usersWord;
				break;
			}
			ifLetterThere(guessedLetter);
			println("The word now looks like this: " + guessWord);
		}
	}

	// Method: isLetterThere
	// Checks to see if the letter the user entered is in the word and responds accordingly
	private void ifLetterThere(char guessedLetter) {
		isThere = isCharInWord(guessedLetter);
		if (isThere) {
			println("That guess is correct.");
			guessWord = updateGuessWord(guessedLetter);	                   // This function will update the guessed word thus far. 
			canvas.displayWord(guessWord);                 // This updates the current guessWord label in the Hangman graphics. 
		} else {
			println("There are no " + guessedLetter + "'s in the word");
			canvas.noteIncorrectGuess(guessedLetter);          //Adds the incorrect guess to the graphics list of incorrect Characters.
			canvas.displayWord(guessWord);
			guesses--;
			println("You have " + guesses + " incorrect guesses left.");
		} 
	}

	// Method: checkWinOrLost
	// Checks if the user won or lost and responds accordingly.
	private void checkWinOrLost() {
		if (guessWord.equals(usersWord)) {
			println("Congratulations, you won!");
		} else if (guesses == 0) {
			println("I'm sorry, you ran out of turns, try again next time.");
		} else {
			println("Error!");
		}
	}

	// Method: gameSetUp
	// This method initally sets up the game and welcomes our user to hangman.
	private void gameSetUp() {
		canvas.reset();
		println("Welcome to Don't Hang A Man!");
		guesses = TURNS;
		int wordIndex = rgen.nextInt(0, list.getWordCount()-1);
		usersWord = list.getWord(wordIndex);
		guessWord = setUpWord();
		println("Your word looks like this: " + guessWord +". If at anytime you think you know what the word is, feel free to enter it in as a guess. Other than that, only enter in one letter.");
		println("You have " + guesses + " incorrect guesses.");
	}

	// Method: isCharInWord
	// This predicate method returns either true or false depending on if the letter is in the word.
	private Boolean isCharInWord(Character gLetter) {
		String gLAsString = gLetter + "";
		if (usersWord.contains(gLAsString)) {
			return true;
		} else {
			return false;
		}
	}


	// Method: updateGuessWord
	// This method updates takes in the guessed letter and updates the guessed word.
	private String updateGuessWord(Character gLetter) {
		String result = "";
		for (int i=0; i<usersWord.length(); i++){
			if (usersWord.charAt(i) == gLetter) {
				result += gLetter;		
				// result.substring(0, i) + gLetter + result.substring(i+gLetter);     // Re combines the string.
			} else {
				result += guessWord.charAt(i);
			}
		}
		return result;
	}

	// Method: setUpWord
	// This method returns a string with the users word. 
	private String setUpWord() {
		String result = "";
		for (int i=0;i<usersWord.length();i++) {
			result += "-";
		}
		return result;
	}

}
