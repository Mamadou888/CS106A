/*
 * File: PythagoreanTheorem.java
 * Name: Mamadou Diallo 
 * Section Leader: Jared Bitz
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem. This finds the "c" in the pythagorean formula.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		/* You fill this in */
		println("Enter values to compute the Pythagorean Theorem.");
		// Establishing the three necessary variables.
		double a = readDouble("a:");
		double b = readDouble("b:");
		double c;
		// First thing I do is square a and b.
		a = Math.pow(a, 2);
		b = Math.pow(b, 2);
		// Than I make the value of c the square root of the new a and b.
		c = Math.sqrt(a+b);
		// Finally I print "c" and its value.
		println("c = " + c);
	}
}
