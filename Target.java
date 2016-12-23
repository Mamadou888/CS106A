/*
 * File: Target.java
 * Name: Mamadou Diallo
 * Section Leader: Jared Bitz
 * -----------------
 * This file is the starter file for the Target problem. This problem basically recreates the Target logo.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	
	private static final double INCH = 72.0;                    // Making INCH = 72 makes it simpler to convert from inches to pixels.
	
	// createCircle method: takes in the parameters passed in when the method is called.
	// Using those parameters, the method creates a circle a 'x','y' coordinate with "width" width and "height" height.
	// Than it sets the color of the circle created to "color" and either fills it in or leaves it unfilled depending on the value of the "fill" variable.
	// Finally, I add the circle to the graphics screen.
	private void createCircle(double x, double y, double width, double height, Color color, boolean fill) {
		GOval circle = new GOval(x , y, width, height);
		circle.setColor(color);
		circle.setFilled(fill);
		add(circle);
	}
	
	// Steps:
	// 1. Establish variables dealing with the size of the graphics screen and the diameter of the circles in order to make centering the circles easier. 
	// 2. Call the createCircle function I created on the three circles and pass in the specified parameters from the homework (color, filled, radius) for each circle.
	// 3. That's it. Now just run the function.
	public void run() {
		
		// Establishing variables
		double screenWidth = getWidth();
		double halfWidth = screenWidth/2;
		double screenHeight = getHeight();
		double halfHeight = screenHeight/2;
		double diameter = INCH*2;
		
		// Outer circle
		createCircle( halfWidth-INCH, halfHeight-INCH, diameter, diameter, Color.RED, true );
		
		// Middle circle
		createCircle( halfWidth-INCH*.65, halfHeight-INCH*.65, diameter*.65, diameter*.65, Color.WHITE, true );
		
		// Inner Circle.
		createCircle( halfWidth-INCH*.3, halfHeight-INCH*.3, diameter*.3, diameter*.3, Color.RED, true );

	}
	
}
