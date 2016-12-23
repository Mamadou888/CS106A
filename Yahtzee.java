/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.ArrayList;

import acm.io.*;
import acm.program.*;
import acm.util.*;


public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		
		playGame();	// Brings the game together.
		calculateTotalScores();	// add up all the total scores for each player based off upper score, upper bonus, and lower score. 
		displayWinner();	// Calculating the winner and display it as a message.
	
	}
	
	// Method: calculateTotalScores
	// This method, for each player, adds up their total score, their upper bonus, and their lower score, to redisplay the updated total score. 
	private void calculateTotalScores() {
		for (int i = 0; i < nPlayers; i++) {
			
			// Calculate Upper Score
			int upperScore = 0;
			int upperBonus = 0;
			for (int j = ONES; j < UPPER_SCORE; j++) {
				upperScore += playerInfo[i][j];
				// Check if they earned the upper bonus. Calculate it.
				if (upperScore >= 63) {
					upperBonus = 35;
					playerInfo[i][UPPER_BONUS] = upperBonus;
				} else {
					upperBonus = 0;
					playerInfo[i][UPPER_BONUS] = upperBonus;
				}
			}
			playerInfo[i][UPPER_SCORE] = upperScore;
			display.updateScorecard(UPPER_SCORE, i+1, playerInfo[i][UPPER_SCORE]);
			display.updateScorecard(UPPER_BONUS, i+1, playerInfo[i][UPPER_BONUS]);
			
			// Calculate lower score.
			int lowerScore = 0;
			for (int j = THREE_OF_A_KIND; j < LOWER_SCORE; j++) {
				lowerScore += playerInfo[i][j];
			}
			// Update lower score categories
			playerInfo[i][LOWER_SCORE] = lowerScore;
			display.updateScorecard(LOWER_SCORE, i+1, playerInfo[i][LOWER_SCORE]);	// update scoreboard with lower score
			
			// Update total score categories
			playerInfo[i][TOTAL] = upperScore + upperBonus + lowerScore;	// calculate total score
			display.updateScorecard(TOTAL, i+1, playerInfo[i][TOTAL]);	// update scoreboard with total score
		}
	}
	
	// Method: displayWinner
	// This method calculates the winner of the game by comparing their total scores. With that it prints a message, contratulating the winner.
	private void displayWinner() {
		int winningScore = 0; 
		int winnerInt = 0;
		for (int i = 0; i < nPlayers; i++) {
			if (winningScore <= playerInfo[i][TOTAL]) {
				winningScore = playerInfo[i][TOTAL];
				winnerInt = playerInfo[i][0];
			}
		}
		display.printMessage("Congratulations, " + playerNames[winnerInt] + ",  you're the winner with a total score of " + winningScore +" points!");
	}
	
	// Method: playGame()
	// This method plays the game and brings everything together. It ends when all the scoring categories are filled. 
	private void playGame() {
		/* You fill this in */
		setUpPlayerInfo();
		setUpCategories();
		// For each round...
		for (int i = 1; i <= N_SCORING_CATEGORIES; i++) {
			// For each turn/player...
			for (int j = 0; j < nPlayers; j++) {	
				rollDice(j);
				rollAgain();
				rollAgain();
				pickCategory(j);	// allow the user to click the category they want their set of dice to apply to. 
				addScoreToCategory(playerInfo[j][0]);	// add the appropriate score to its category and display it.
				updateTotalScore(j);	// add score to total score
			}
		}
	}
	
	// Method: setUpCategories
	// This simply sets up the category array for later use. 
	private void setUpCategories() {
		categories = new int[nPlayers][N_CATEGORIES+1];
		categoryPick = false;
	}
	
	// Method: rollDice
	// This method rolls the dice initially to be random integers. 
	private void rollDice(int cPlayer) {
		display.printMessage(playerNames[cPlayer] + "'s turn! Click the 'Roll Dice' button to roll the dice.");

		display.waitForPlayerToClickRoll(playerInfo[cPlayer][0]+1);	//Initial Roll
		setUpDice(); // sets up the initial array of dice randomly.
		// Random Dice Testing Below - REMEMBER TO DELETE LATER
//		dice[0] = 1;
//		dice[1] = 1;
//		dice[2] = 1;
//		dice[3] = 5;
//		dice[4] = 5;
		display.displayDice(dice);	// display the roll of dice.
	}
	
	// Method: setUpDice
	// This method sets up the dice during the first roll to be random integers. 
	private void setUpDice() {
		dice = new int[N_DICE];
		for (int  i = 0; i < N_DICE; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
	}
	
	// Method: rollAgain
	// This method rolls the dice again.
	private void rollAgain() {
		display.printMessage("Select the dice you wish to re-roll and click 'Roll Again'. You also don't have to select any dice. ");
		display.waitForPlayerToSelectDice();
		checkSelectedDice();
	}
	
	// Method: alreadyPicked
	// This method returns a boolean that determines whether the user has already picked a category or not.
	private boolean alreadyPicked(int cPicked, int cPlayer) {
		// Loop through all the categories for each player in the new categories array. If anyone of them equals cPicked
		// Than return false. 
		boolean isThere = false; 
		for (int i = 1; i < N_CATEGORIES; i++) {
			if (categories[cPlayer][i] == cPicked) {
				isThere = true;
			}
		}
		return isThere;
	}
	
	// Method: pickCategory
	// This method waits for the user to select a category, and than determines how many points they should receive based off the category they picked.
	private void pickCategory(int cPlayer) { 
		// Select Category
		display.printMessage("Select a category for this roll");
		int categoryPicked = display.waitForPlayerToSelectCategory();
		
		// Check the current category picked to see if it was picked already. 
		// If it's false, than let's add the categoryPicked to our categories array to the appropriate player and keep going with the game.
		categoryPick = alreadyPicked(categoryPicked, cPlayer);	// alreadyPicked() will either turn categoryPick true or false.
		if (categoryPick == false) {
			categories[cPlayer][categoryPicked] = categoryPicked;
		} else {
			while (categoryPick == true) {
				display.printMessage("You already picked this category. Choose another!");
				categoryPicked = display.waitForPlayerToSelectCategory();
				categoryPick = alreadyPicked(categoryPicked, cPlayer);
			}
		}
		
		// Do the below once categoryPick = false. 
		points = 0;
		category = 0;
		switch (categoryPicked) {	//Chooses how many points and what category to apply the points to, depending on the category picked.
			case ONES:	
				category = ONES; 
				// The amount of points is equal to the total # of 1's showing on the dice.
				countDiceValues();	// Counts the dice values and groups them so the amount of each is easy to keep track of.
				if (NofOnes.size() == 0) { 
					points = 0;
				} else {
					points = NofOnes.size();
				}
				break;
			case TWOS:
				category = TWOS;
				// total # of 2's, 3's, 4's, 5's, and 6's showing on the dice. If none, points = 0.
				countDiceValues();
				// multiply the number of 2's by 2
				points = NofTwos.size()*category;
				break;
			case THREES:
				category = THREES;
				// total # of 2's, 3's, 4's, 5's, and 6's showing on the dice
				countDiceValues();
				points = NofThrees.size()*category;
				// if there are none, points = 0.
				break;
			case FOURS:
				category = FOURS;
				// total # of 2's, 3's, 4's, 5's, and 6's showing on the dice.
				countDiceValues();
				points = NofFours.size()*category;
				break;
			case FIVES:
				category = FIVES;
				// total # of 2's, 3's, 4's, 5's, and 6's showing on the dice.
				countDiceValues();
				points = NofFives.size()*category;
				break;
			case SIXES:
				category = SIXES;
				// total # of 2's, 3's, 4's, 5's, and 6's showing on the dice.
				countDiceValues();
				points = NofSixes.size()*category;
				break;
			case THREE_OF_A_KIND:	
				category = THREE_OF_A_KIND;
				// points is the sum of all of the values showing on the dice. 
				countDiceValues(); 
				points = NofOnes.size()*1 + NofTwos.size()*2 + NofThrees.size()*3 + NofFours.size()*4 + NofFives.size()*5 + NofSixes.size()*6;
				break;
			case FOUR_OF_A_KIND:	
				category = FOUR_OF_A_KIND;
				// points is the sum of all the values showing on the dice.
				countDiceValues();
				points = NofOnes.size()*1 + NofTwos.size()*2 + NofThrees.size()*3 + NofFours.size()*4 + NofFives.size()*5 + NofSixes.size()*6;
				break;
			case FULL_HOUSE:
				category = FULL_HOUSE;
				points = 25;
				break;
			case SMALL_STRAIGHT:
				category = SMALL_STRAIGHT;
				points = 30;
				break;
			case LARGE_STRAIGHT:
				category = LARGE_STRAIGHT;
				points = 40;
				break;
			case YAHTZEE:
				category = YAHTZEE;
				points = 50;
				break;
			case CHANCE:
				category = CHANCE;
				// points is the sum of all the values showing on the dice.
				countDiceValues();
				points = NofOnes.size()*1 + NofTwos.size()*2 + NofThrees.size()*3 + NofFours.size()*4 + NofFives.size()*5 + NofSixes.size()*6;
				break;
			default:
				break;
		}
	}
	
	// Method: countDiceValues
	// This method simply adds the values of each dice to an array list containing only those values. It makes it much easier to later keep track of
	// how many values of what are in each dice roll. 
	private void countDiceValues() {
		// Because these are instance variable array lists, I have to clear them each go around.
		NofOnes.clear();
		NofTwos.clear();
		NofThrees.clear();
		NofFours.clear();
		NofFives.clear();
		NofSixes.clear();
		
		int randomFillerInt = 0;	// This value doesn't matter. All I need is the size of the Array List in order for this to work.
		// Know it adds objects to the appropriate Array lists. 
		for (int d = 0; d < N_DICE; d++) {
			if (dice[d] == 1) {
				NofOnes.add( new Integer( randomFillerInt ));
			}
			if (dice[d] == 2) {
				NofTwos.add( new Integer( randomFillerInt ));
			}
			if (dice[d] == 3) {
				NofThrees.add( new Integer( randomFillerInt ));
			}
			if (dice[d] == 4) {
				NofFours.add( new Integer( randomFillerInt ));
			}
			if (dice[d] == 5) {
				NofFives.add( new Integer( randomFillerInt ));
			}
			if (dice[d] == 6) {
				NofSixes.add( new Integer( randomFillerInt ));
			}
		}
	}
	
	// Method: addScoreToCategory
	// This method updates the current players score for the category they picked depending on if it fits the criteria for that category.
	// It also adds the users score to their individual list of scores, both on the scoreboard and to a multidimensional array that I keep track of. 
	private void addScoreToCategory(int cPlayer) {
		//cPlayer is the current player
		
		if (checkCategory(dice, category)) {
			display.updateScorecard(category, cPlayer+1, points);
			playerInfo[cPlayer][category] = points;
		} else {
			points = 0;
			display.updateScorecard(category, cPlayer+1, points);
			playerInfo[cPlayer][category] = points;
			display.printMessage("Oops, you got 0 points!");
		}
	}
	
	// Method: checkCategory
	// This method returns a boolean. The boolean returns true if the users final dice roll fits the category they picked. If not, it returns false. 
	private boolean checkCategory(int[] dice, int category) {
		boolean checked = false;
		int randomFillerInt = 0;	// This value doesn't matter. All I need is the size of the Array List in order for this to work.
		switch (category) {
			case ONES: 
			case TWOS: 
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
				checked = true;
				break;
			case THREE_OF_A_KIND:
				countDiceValues();	// count # of each dice
				// if any of the dice arrays size equals 3 than return true, else return false.
				ArrayList amntofThrees = new ArrayList<Integer>();	// To keep track of the amount of numbers who size is 1
				
				if (NofOnes.size() == 3) amntofThrees.add(new Integer(randomFillerInt));
				if (NofTwos.size() == 3) amntofThrees.add(new Integer(randomFillerInt));
				if (NofThrees.size() == 3) amntofThrees.add(new Integer(randomFillerInt));
				if (NofFours.size() == 3) amntofThrees.add(new Integer(randomFillerInt));
				if (NofFives.size() == 3) amntofThrees.add(new Integer(randomFillerInt));
				if (NofSixes.size() == 3) amntofThrees.add(new Integer(randomFillerInt));
				if (amntofThrees.size() == 1) {
					checked = true;
				} else {
					checked = false;
				}
				break;
			case FOUR_OF_A_KIND:
				countDiceValues();	// count # of each dice
				// if any of the dice arrays size equals 4 than return true, else return false.
				ArrayList amntofFours = new ArrayList<Integer>();	// To keep track of the amount of numbers who size is 1
				
				if (NofOnes.size() == 4) amntofFours.add(new Integer(randomFillerInt));
				if (NofTwos.size() == 4) amntofFours.add(new Integer(randomFillerInt));
				if (NofThrees.size() == 4) amntofFours.add(new Integer(randomFillerInt));
				if (NofFours.size() == 4) amntofFours.add(new Integer(randomFillerInt));
				if (NofFives.size() == 4) amntofFours.add(new Integer(randomFillerInt));
				if (NofSixes.size() == 4) amntofFours.add(new Integer(randomFillerInt));
				if (amntofFours.size() == 1) {
					checked = true;
				} else {
					checked = false;
				}
				break;
			case FULL_HOUSE:
				countDiceValues();	// count # of each dice
				// if any of the dice arrays size equals 3 and any other equals two return true, else return false. 
				ArrayList amntof3s = new ArrayList<Integer>();	// To keep track of the amount of numbers who size is 3
				ArrayList amntof2s = new ArrayList<Integer>();	// To keep track of the amount of numbers who size is 2
				if (NofOnes.size() == 3) amntof3s.add(new Integer(randomFillerInt));
				if (NofOnes.size() == 2) amntof2s.add(new Integer(randomFillerInt));
				if (NofTwos.size() == 3) amntof3s.add(new Integer(randomFillerInt));
				if (NofTwos.size() == 2) amntof2s.add(new Integer(randomFillerInt));
				if (NofThrees.size() == 3) amntof3s.add(new Integer(randomFillerInt));
				if (NofThrees.size() == 2) amntof2s.add(new Integer(randomFillerInt));
				if (NofFours.size() == 3) amntof3s.add(new Integer(randomFillerInt));
				if (NofFours.size() == 2) amntof2s.add(new Integer(randomFillerInt));
				if (NofFives.size() == 3) amntof3s.add(new Integer(randomFillerInt));
				if (NofFives.size() == 2) amntof2s.add(new Integer(randomFillerInt));
				if (NofSixes.size() == 3) amntof3s.add(new Integer(randomFillerInt));
				if (NofSixes.size() == 2) amntof2s.add(new Integer(randomFillerInt));
				if (amntof3s.size() == 1 && amntof2s.size() == 1) {
					checked = true;
				} else {
					checked = false;
				}
				break;
			case SMALL_STRAIGHT:
				countDiceValues();	// count # of each dice
				// The dice must contain at least four consecutive values. 

				ArrayList amntof1s = new ArrayList<Integer>();	// To keep track of the amount of numbers who size is 1
				
				if (NofOnes.size() == 1) amntof1s.add(new Integer(randomFillerInt));
				if (NofTwos.size() == 1) amntof1s.add(new Integer(randomFillerInt));
				if (NofThrees.size() == 1) amntof1s.add(new Integer(randomFillerInt));
				if (NofFours.size() == 1) amntof1s.add(new Integer(randomFillerInt));
				if (NofFives.size() == 1) amntof1s.add(new Integer(randomFillerInt));
				if (NofSixes.size() == 1) amntof1s.add(new Integer(randomFillerInt));
				if (amntof1s.size() == 4 || amntof1s.size() == 5) {
					checked = true;
				} else {
					checked = false;
				}
				break;
			case LARGE_STRAIGHT:
				countDiceValues();	// count # of each dice
				// The dice must contain five consecutive values.
				ArrayList amntof1S = new ArrayList<Integer>();	// To keep track of the amount of numbers who size is 1
				
				if (NofOnes.size() == 1) amntof1S.add(new Integer(randomFillerInt));
				if (NofTwos.size() == 1) amntof1S.add(new Integer(randomFillerInt));
				if (NofThrees.size() == 1) amntof1S.add(new Integer(randomFillerInt));
				if (NofFours.size() == 1) amntof1S.add(new Integer(randomFillerInt));
				if (NofFives.size() == 1) amntof1S.add(new Integer(randomFillerInt));
				if (NofSixes.size() == 1) amntof1S.add(new Integer(randomFillerInt));
				if (amntof1S.size() == 5) {
					checked = true;
				} else {
					checked = false;
				}
				break;
			case YAHTZEE:
				countDiceValues();	// count # of each dice
				
				ArrayList amntofN_DICE = new ArrayList<Integer>();	// To keep track of the amount of numbers who size is 1
				
				if (NofOnes.size() == 5) amntofN_DICE.add(new Integer(randomFillerInt));
				if (NofTwos.size() == 5) amntofN_DICE.add(new Integer(randomFillerInt));
				if (NofThrees.size() == 5) amntofN_DICE.add(new Integer(randomFillerInt));
				if (NofFours.size() == 5) amntofN_DICE.add(new Integer(randomFillerInt));
				if (NofFives.size() == 5) amntofN_DICE.add(new Integer(randomFillerInt));
				if (NofSixes.size() == 5) amntofN_DICE.add(new Integer(randomFillerInt));
				if (amntofN_DICE.size() == 1) {
					checked = true;
				} else {
					checked = false;
				}
				break;
			case CHANCE:
				checked = true;
				break;
			default:
				break;
		}
		return checked;
	}
	
	// Method: updateTotalScore
	// This method updates the total score for that player in their personal array of scores and updates the overall score on scoreboard. 
	private void updateTotalScore(int cPlayer) {
		playerInfo[cPlayer][TOTAL] += points;
		display.updateScorecard(TOTAL, playerInfo[cPlayer][0]+1, playerInfo[cPlayer][TOTAL]);
	}
	
	// Method: checkSelectedDice
	// This method checks the dice the user selected and re-rolls them.
	private void checkSelectedDice() {
		boolean dieIsSelected = false;
		for (int k = 0; k < N_DICE; k++) {
			if (display.isDieSelected(k)) {
				dieIsSelected = true;
				dice[k] = rgen.nextInt(1, 6);
			} 
		}
		if (dieIsSelected) {
			display.displayDice(dice);
		} 
	}
	
	// Method: setUpPlayerInfo
	// This method creates the playerInfo multidimensional array and sets up so that each player can be associated with an integer. 
	// It also pre-fills the unclickable categories. 
	private void setUpPlayerInfo() {
		playerInfo = new int[nPlayers][N_CATEGORIES+1];
		for (int i = 0; i < nPlayers; i++) {
			playerInfo[i][0] = i;	// Attach an integer to each players scores. 
			
			// Set up 'unclickable' categories
			playerInfo[i][UPPER_SCORE] = 0;
			playerInfo[i][UPPER_BONUS] = 0;
			playerInfo[i][LOWER_SCORE] = 0;
			playerInfo[i][TOTAL] = 0;
			
		}	
	}
		
/* Private instance variables */
	private int nPlayers;
	private int[][] playerInfo;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] dice;
	private int category;
	private int points;
	private int[][] categories;
	private boolean categoryPick;
	
//	Instance Variables for # of X in Dice.
	private ArrayList NofOnes = new ArrayList<Integer>();
	private ArrayList NofTwos = new ArrayList<Integer>();
	private ArrayList NofThrees = new ArrayList<Integer>();
	private ArrayList NofFours = new ArrayList<Integer>();
	private ArrayList NofFives = new ArrayList<Integer>();
	private ArrayList NofSixes = new ArrayList<Integer>();

}
