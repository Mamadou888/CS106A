/*
 * File: Pyramid.java
 * Name: Mamadou Diallo
 * Section Leader: Jared Bitz
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly. This problems builds a Pyramid with the specified bricks in base. 
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Height of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 10;
	
	// When it stops building the rows.
	private static final int SENTINEL = 0;

	public void run() {
		
		// Establishing necessary variables to make the while function simpler. 
		
		int halfTheWidth = (getWidth()/2);
		int yValue = getHeight()-BRICK_HEIGHT;
		int bricksInBase = BRICKS_IN_BASE;
		int toTheRight=0;
		int halfBaseLength=((bricksInBase*BRICK_WIDTH)/2);
		int xValue = halfTheWidth - halfBaseLength;

		// Starting from the bottom, while bricks in base does not equal zero, than build a row. Each time, subtract 1 to go fill in the next row. 
		
		while (bricksInBase != SENTINEL) {
			for (int i=0; i<bricksInBase; i++) {
				GRect rectangle = new GRect(xValue, yValue, BRICK_WIDTH, BRICK_HEIGHT);
				add(rectangle);
				xValue += BRICK_WIDTH;
			}
			toTheRight+=1;
			bricksInBase-=1;
			yValue = yValue-BRICK_HEIGHT;
			xValue = ((BRICK_WIDTH/2)*toTheRight + halfTheWidth) - halfBaseLength;
		} 
	}
}














