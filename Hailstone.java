/*
 * File: Hailstone.java
 * Name: Mamadou Diallo
 * Section Leader: Jared Bitz
 * --------------------
 * This file is the starter file for the Hailstone problem. The hailstone problem is a cool problem that manipulates any number until it equals 1
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		// First I establish the variable count so I can keep track of how many times the while loop executes which is the amount of times the input is manipulated.
		int count = 0;
		int input = readInt("Enter a number: ");       // To get the users input so we can later manipulate it. 
		while (input != 1) {						   // The while loop will keep manipulating the users number and print whether its even or odd until the input is equal to 1
			if (input % 2 != 0) {					   // If the input is even than multiply it by 3 and add 1
				println(input + " is odd so I make 3n+1: " + (3*input+1));
				input = 3*input + 1;
			} else if (input % 2 != 1) { 			   // If the input is odd than divide it by 2.
				println(input + " is even so I take half: " + (input/2));
				input = input/2;
			}
			count++;
		}
		// Finally the program prints the amount of rounds it took for the program to finally reach 1. 
		println("The process took " + count + " to reach 1");
	}
	// When n = 27 the program takes 111 times.
}

