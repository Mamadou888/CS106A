/*
 * File: Breakout.java
 * -------------------
 * Name: Mamadou
 * Section Leader: Jared Bitz
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels.  IMPORTANT NOTE:
	 * ON SOME PLATFORMS THESE CONSTANTS MAY **NOT** ACTUALLY BE THE DIMENSIONS
	 * OF THE GRAPHICS CANVAS.  Use getWidth() and getHeight() to get the 
	 * dimensions of the graphics canvas. */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board.  IMPORTANT NOTE: ON SOME PLATFORMS THESE 
	 * CONSTANTS MAY **NOT** ACTUALLY BE THE DIMENSIONS OF THE GRAPHICS
	 * CANVAS.  Use getWidth() and getHeight() to get the dimensions of
	 * the graphics canvas. */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 15;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
			(WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	// General Variables

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		// This function sets up the game.
		setUpGame();
		// This waits for the screen to be clicked before the game starts
		waitForClick();
		// This will run the while loop that keeps the game going until the game the user either loses or the bricks or gone. 
		playGame();
		// Checks to see if the user won or lost. If they lost, it tells them to play again. If they won, it congratulates them.
		winOrLose();
	}
	
	// Method: winOrLost
	// This method checks for if the user won or lost and responds accordingly. 
	private void winOrLose() {
		if (bricksLeft != 0) {
			removeAll();
			GLabel youLost = new GLabel("You Lost! Try stretching your wrists this time and play again!");
			add(youLost, (getWidth() / 2) - (youLost.getWidth() / 2), (getHeight() / 2) - (youLost.getAscent() / 2));
		} else {
			removeAll();
			GLabel youWon = new GLabel("Congratulations! You won. You Rock!");
			add(youWon, (getWidth() / 2) - (youWon.getWidth() / 2), (getHeight() / 2) - (youWon.getAscent() / 2));
		}
	}
	
	// Method: playGame()
	// This method runs the loop that plays the game and checks for colliding objects and delets bricks and bounces off paddles.
	private void playGame() {
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		vy = 8.0;
		paddleHits = 0;
		while (nTurns != 0) {
			ball.move(vx, vy);
			pause(30);
			collider = getCollidingObject();
			if (collider != null) {
				if (collider == paddle) {
					bounceClip.play();
					vy = -vy;
					paddleHits += 1;
					if (paddleHits % 2 == 0) vx = vx / .9;
				} else {
					vy = -vy;
					removeBrick();
					if (bricksLeft == 0) break;
				}
			} else {
				bounceOffWall();
			}
		}
	}
	
	// Method: setUpGame
	// This function sets up the game by putting down the bricks, setting up the turns, and creating a paddle and ball. 
	private void setUpGame() {
		// Set up the bricks in the colorful scheme where the color changes every two rows..
		setUpBricks();
		nTurns = NTURNS;
		createPaddle();
		createBall();
	}
	
	// Method: removeBrick
	// This function removes a brick from the screen that the ball hit.
	private void removeBrick() {
		remove(collider);
		bricksLeft -= 1;
		bounceClip.play();
		println("Bricks left: " + bricksLeft);
	}
	
	// Method: bounceOffWall
	// This function bounces a ball off the wall.
	private void bounceOffWall() {
		if (ball.getX() <= BRICK_SEP || (ball.getX() + ball.getWidth()) >= (getWidth() - BRICK_SEP) ) {
			vx = -vx;
		} else if ((ball.getY() + ball.getHeight()) >= getHeight()) {
			nTurns -= 1;
			println("Turns Left: " + nTurns);
			if (nTurns != 0) {
				ball.setLocation((getWidth() / 2) - BALL_RADIUS, (getHeight() / 2) - BALL_RADIUS);
				waitForClick();
				vx = rgen.nextDouble(1.0, 3.0);
				if (rgen.nextBoolean(0.5)) vx = -vx;
			}
		} else if (ball.getY() <= 0) {
			vy = -vy;
		}
	}

	// Method: getCollidingObject
	// This method checks to see if the ball is colliding with any object and returns the object that its collided with.
	private GObject getCollidingObject() {
		GObject upLeft = getElementAt(ball.getX(), ball.getY());
		GObject upRight = getElementAt(ball.getX() + ball.getWidth(), ball.getY());
		GObject downLeft = getElementAt(ball.getX(), ball.getY() + ball.getHeight());
		GObject downRight = getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
		if (upLeft != null)	{
			return (upLeft);
		} else if (upRight != null) {
			return (upRight);
		} else if (downLeft != null) {
			return (downLeft);
		} else if (downRight != null) {
			return (downRight);
		} else {
			return (null);
		}
	}
	
	// Method: setUpBricks
	// This method sets up all the rows of bricks in the game in a color scheme where every two rows are the same color.
	private void setUpBricks() {
		double x = (getWidth()/2) - ((NBRICKS_PER_ROW*BRICK_WIDTH + (NBRICKS_PER_ROW-1)*BRICK_SEP)/2);
		double y;
		bricksLeft = NBRICKS_PER_ROW*NBRICK_ROWS;
		Color color = Color.RED;
		for (int i=0;i<NBRICK_ROWS;i++) {  // NEED to make it done by rows.
			y = BRICK_Y_OFFSET + (i)*(BRICK_HEIGHT+BRICK_SEP);
			drawRow(x, y, color);
			if (i == 0) {
				color = Color.RED;
			} else if (i == 1 || i == 2) {
				color = Color.ORANGE;
			} else if (i == 3 || i == 4) {
				color = Color.YELLOW;
			} else if (i == 5 || i == 6) {
				color = Color.GREEN;
			} else if (i == 7 || i == 8) {
				color = Color.CYAN;
			}
		}
	}
	
	// Method: drawRow
	// This method draws a row of bricks in one color. 
	private void drawRow(double x, double y, Color color) {
		for (int i=0; i<NBRICKS_PER_ROW;i++) {
			GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			brick.setFillColor(color);
			brick.setColor(color);
			add(brick);
			x += BRICK_WIDTH + BRICK_SEP;
		}
	}
	
	// Method: createPaddle()
	// This method creates the paddle at the bottom of the screen. adds it, and calls for its x value to follow the mouse. 
	private void createPaddle() {
		int y = getHeight() - PADDLE_Y_OFFSET;
		int x = getWidth()/2-PADDLE_WIDTH/2;
		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle);
		addMouseListeners();
	}

	// Method: mouseMoved()
	// This method keeps track of the mouse movements between the left most side of the screen and the right. It causes for the paddle to change its
	// x value based on the x value of the mouse.
	public void mouseMoved(MouseEvent e) {
		if (e.getX() >= (PADDLE_WIDTH/2) && e.getX() <= getWidth() - (PADDLE_WIDTH / 2)) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, paddle.getY());
		}
	}
	
	// Method: createBall()
	// This method creates a black ball and adds it to the screen.
	private void createBall() {
		int x = (getWidth() / 2) - BALL_RADIUS;
		int y = (getHeight() / 2) - BALL_RADIUS;
		ball = new GOval(x, y, BALL_RADIUS*2, BALL_RADIUS*2); // Center it properly
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
		add(ball);
	}
	
	// Private Instance Variables
	private GRect paddle;
	private double vx, vy;
	private GOval ball;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int bricksLeft;
	private int nTurns; 
	private GObject collider;
	private AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	private int paddleHits;
}




