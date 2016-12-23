/*
 * File: CS106ATiles.java
 * Name: Mamadou Diallo
 * Section Leader: Jared Bitz
 * ----------------------
 * This file is the starter file for the CS106ATiles problem. This problem creates 4 tiles that say's CS106A on them in the center of the tile.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class CS106ATiles extends GraphicsProgram {
	
	/** Amount of space (in pixels) between tiles */
	private static final double TILE_SPACE = 20;

	// Width of rectangle in pixels
	private static final double TILE_WIDTH = 180;
	
	// Height of rectangle in pixels
	private static final double TILE_HEIGHT = 90;
	
	// The drawRectWithLabel method draws a rectangle in the specified "x", "y" coordinates given from the parameters.
	// In addition, I manipulate those same x, y coordinates in order to draw the "CS106A" label in the center of the rectangle. 
	private void drawTileWithLabel(double x, double y) {
		GRect rectangle = new GRect(x, y, TILE_WIDTH, TILE_HEIGHT);
		add(rectangle);
		GLabel label = new GLabel("CS106A");
		double gLabelX = (x + TILE_WIDTH/2) - label.getWidth()/2;
		double gLabelY = (y + TILE_HEIGHT/2) + label.getAscent()/2;
		add(label, gLabelX, gLabelY);
	}

	public void run() {
		
		// Establishing variables. 
		double halfScreenWidth = getWidth()/2;
		double halfScreenHeight = getHeight()/2;
		double halfTileSpace = TILE_SPACE/2;
		
		// Tile 1
		drawTileWithLabel((halfScreenWidth - halfTileSpace) - TILE_WIDTH, (halfScreenHeight - halfTileSpace) - TILE_HEIGHT);
		
		// Tile 2
		drawTileWithLabel(halfScreenWidth + halfTileSpace, (halfScreenHeight - halfTileSpace) - TILE_HEIGHT);

		// Tile 3
		drawTileWithLabel((halfScreenWidth - halfTileSpace)-TILE_WIDTH, halfScreenHeight + halfTileSpace);
				
		// Tile 4
		drawTileWithLabel(halfScreenWidth + halfTileSpace, halfScreenHeight + halfTileSpace);
		
	}
}

