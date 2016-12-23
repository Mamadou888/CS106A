/*
 * File: PythagoreanTheorem.java
 * Name: Mamadou Diallo 
 * Section Leader: Jared Bitz
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem. This finds the "c" in the pythagorean formula.
 */

import acm.program.*;

public class Midterm_Prob_3 extends ConsoleProgram {
	// Constants
	private final static double INTEREST = .02;
	
	public void run() {
		println("Let me calulate your interest for you!");
		double iB = readDouble("ib?");
		currentBalance = iB;
		int iM = readInt("iM?");
		int iY = readInt("iY?");
		int eM = readInt("eM?");
		int eY = readInt("eY");
		println("Year" + iY + ", Month " + iM + " , balance: " + currentBalance);
		while (true) {
			if (iM == eM && iY == eY) break;
			iM++;
			if (iM>12) {
				iM = 1;
				iY++;
			}
			nextBalance();
			println("Year" + iY + ", Month " + iM + " , balance: " + currentBalance);
		}
	}
	
	private void nextBalance() {
		currentBalance += currentBalance*INTEREST;
	}
	
	// Instance Variables
	private double currentBalance;
}
