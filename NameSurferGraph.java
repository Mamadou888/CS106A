/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	// CONSTANTS
	private static final int LABEL_BORDER = 3;
	
	// Constructor: NameSurferGraph
	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		// You fill in the rest //
		entries = new ArrayList<NameSurferEntry>();
	}
	
	// Method: clear
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		// You fill this in //
		entries.clear();
	}
	
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
		entries.add(entry);
	}
	
	
	public void set(String Gtype) {
		if (Gtype.equals("Line Graph")) {
			typeOfGraph = 0;
		} else if (Gtype.equals("Bar Graph")) {
			typeOfGraph = 1;
		} else {
			System.out.print("Neither Bar or Line Graph was Selected. Error 1");
		}
	}
	
	// Method: update()
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		setUpBackground();
		if (typeOfGraph == 0) {
			drawEntries();
		} else if (typeOfGraph == 1) {
			drawRectEntry();
		} else {
			System.out.print("Neither Bar or Line Graph was Selected. Error 2");
		}
	}
	
	private void drawRectEntry() {
		// For each entry, draw a rectangle and a label in set colors
		for (int i = 0; i < entries.size(); i++) {
			int x = (getWidth() / NDECADES) / 4;
			int y = 0;
			int width = 0;
			int height = 0;
			for (int j = 0; j < NDECADES - 1; j++) {
				// Set up Y
				y = (getHeight() - GRAPH_MARGIN_SIZE*2)*(entries.get(i).getRank(j));
				y -= MAX_RANK;
				y /= MAX_RANK;
				
				width = (getWidth() / NDECADES) / 2;
				height = (getHeight() - GRAPH_MARGIN_SIZE*2) - y;

				if (entries.get(i).getRank(j) != 0) { 
					
					// Create and add the rectangle
					GRect barGraph = new GRect(width, height);
					barGraph.setFilled(true);
					barGraph.setFillColor(Color.MAGENTA);
					add(barGraph, x, y + GRAPH_MARGIN_SIZE );
					
					// Create and add the label
					GLabel label = new GLabel(entries.get(i).getName() + " " + entries.get(i).getRank(j));
					label.setFont("Helvetica-14");
					label.setColor(Color.MAGENTA);
					add(label, (x + width/2) - (label.getWidth()/2), (y + GRAPH_MARGIN_SIZE) - (label.getAscent() / 2));
				

					// Add the last label
					if (j == NDECADES - 2) {
						
						// Create and add the label
						GLabel Nlabel = new GLabel(entries.get(i).getName() + " " + entries.get(i).getRank(j+1));
						Nlabel.setFont("Helvetica-14");
						Nlabel.setColor(Color.MAGENTA);
						
						y = (getHeight() - GRAPH_MARGIN_SIZE*2)*(entries.get(i).getRank(j+1));
						y -= MAX_RANK;
						y /= MAX_RANK;
						
						add(Nlabel, (getWidth() / NDECADES) + (x + width/2) - (Nlabel.getWidth()/2), (y + GRAPH_MARGIN_SIZE) - (Nlabel.getAscent() / 2));
					
						// Add last rectangle.
						height = (getHeight() - GRAPH_MARGIN_SIZE*2) - y;
						GRect NbarGraph = new GRect(width, height );
						NbarGraph.setFilled(true);
						NbarGraph.setFillColor(Color.MAGENTA);
						add(NbarGraph, (getWidth() / NDECADES) + x, y + GRAPH_MARGIN_SIZE);
						
					}
					
				} else {
					
					// Create and add the label
					GLabel label = new GLabel(entries.get(i).getName() + "*");
					label.setFont("Helvetica-14");
					label.setColor(Color.MAGENTA);
					add(label, (x + width/2), getHeight() - GRAPH_MARGIN_SIZE);
					
					// Add the last label
					if (j == NDECADES - 2) {
						GLabel Nlabel = new GLabel(entries.get(i).getName() + "*");
						Nlabel.setFont("Helvetica-14");
						Nlabel.setColor(Color.MAGENTA);
						
						add(Nlabel, (getWidth() / NDECADES) + (x + width/2) - (Nlabel.getWidth()/2), getHeight() - GRAPH_MARGIN_SIZE);
					
					}
				}

				// Increment the x value.
				x += (getWidth() / NDECADES);
			}

			
		}
	}
	
	// Method: drawEntries
	// This draws all the entries in the entries ArrayList
	private void drawEntries() {
		// For each entry, draw lines and labels in set colors.
		for (int i = 0; i < entries.size(); i++) {
			
			setColor(i);

			NameSurferEntry currentEntry = entries.get(i);
			lineX1 = 0;
			lineY1 = 0;
			lineX2 = 0;
			lineY2 = 0;
			sLabel = currentEntry.getName();
			sLabelX = 0; 
			sLabelY = 0;
			
			// Add the first circle
			GOval fOvalShape = new GOval(10, 10);
			int lY = (getHeight() - GRAPH_MARGIN_SIZE*2)*(currentEntry.getRank(0));
			lY -= MAX_RANK;
			lY /= MAX_RANK;
			fOvalShape.setFilled(true);
			fOvalShape.setFillColor(color);
			add(fOvalShape, 0 - fOvalShape.getWidth()/2, (lY + GRAPH_MARGIN_SIZE) - (fOvalShape.getHeight()/2));
			
			// For each decade, graph a line and a label.
			for (int j = 0; j < NDECADES - 1; j++) {
				// If the current rank is 0 and the one in front of it is 0.
				
				if (currentEntry.getRank(j) == 0 && currentEntry.getRank(j+1) == 0) {
					// Set variables
					lineY1 = GRAPH_MARGIN_SIZE;
					lineX2 = lineX1;
					lineY2 = GRAPH_MARGIN_SIZE;
					
					sLabel = currentEntry.getName() + "*";
					sLabelX = lineX1 + LABEL_BORDER;
					sLabelY = getHeight() - GRAPH_MARGIN_SIZE*2;
					
					// Add the last label
					if (j == NDECADES - 2) {
						String sLabel1 = currentEntry.getName() + ("*");
						GLabel Nlabel = new GLabel(sLabel1);
						int sLabelX2 = sLabelX  + (getWidth() / NDECADES);
						int sLabelY2 = sLabelY + GRAPH_MARGIN_SIZE;
						
						Nlabel.setFont("Helvetica-14");
						Nlabel.setColor(color);
						
						add(Nlabel, sLabelX2, sLabelY2);
					}
					
				} else if (currentEntry.getRank(j) == 0 && currentEntry.getRank(j+1) != 0) {	// If current rank is 0 and the next one is not 0.
					// Draw just a GLabel at the bottom 
					sLabel = currentEntry.getName() + "*";
					sLabelX = lineX1 + LABEL_BORDER;
					sLabelY = getHeight() - GRAPH_MARGIN_SIZE*2;
					lineX2 = lineX1 + (getWidth() / NDECADES);
					lineY1 = getHeight() - GRAPH_MARGIN_SIZE*2;
					lineY2 = (getHeight() - GRAPH_MARGIN_SIZE*2)*(currentEntry.getRank(j+1));
					lineY2 -= MAX_RANK;
					lineY2 /= MAX_RANK;
					
					// Add the last label
					if (j == NDECADES - 2) {
						String sLabel1 = currentEntry.getName() + (" " + currentEntry.getRank(j+1));
						GLabel Nlabel = new GLabel(sLabel1);
						int lineY3 = (getHeight() - GRAPH_MARGIN_SIZE*2)*(currentEntry.getRank(j+1));
						lineY3 -= MAX_RANK;
						lineY3 /= MAX_RANK;
						int sLabelX2 = sLabelX + (getWidth() / NDECADES);
						int sLabelY2 = lineY3 - LABEL_BORDER;
						
						Nlabel.setFont("Helvetica-14");
						Nlabel.setColor(color);
						
						add(Nlabel, sLabelX2, sLabelY2);
					}
					
					// Add the circles
					GOval ovalShape = new GOval(10, 10);
					ovalShape.setFilled(true);
					ovalShape.setFillColor(color);
					add(ovalShape, lineX1 - (ovalShape.getWidth()/2), (GRAPH_MARGIN_SIZE + lineY1) - ovalShape.getHeight()/2);
					

				} else if (currentEntry.getRank(j) != 0 && currentEntry.getRank(j+1) == 0) {
					
					// Line coordinates
					lineY1 = (getHeight() - GRAPH_MARGIN_SIZE*2)*(currentEntry.getRank(j));
					lineY1 -= MAX_RANK;
					lineY1 /= MAX_RANK;
					
					lineX2 = lineX1 + (getWidth() / NDECADES);
					lineY2 = getHeight() - GRAPH_MARGIN_SIZE*2;
					
					// Labels coordinates
					sLabel = currentEntry.getName() + (" " + currentEntry.getRank(j));
					sLabelX = lineX1 + LABEL_BORDER;
					sLabelY = lineY1 - LABEL_BORDER;
					
					// Add the last label
					if (j == NDECADES - 2) {
						String sLabel1 = currentEntry.getName() + ("*");
						GLabel Nlabel = new GLabel(sLabel1);
						int lineY3 = (getHeight() - GRAPH_MARGIN_SIZE*2)*(currentEntry.getRank(j+1));
						lineY3 -= MAX_RANK;
						lineY3 /= MAX_RANK;
						int sLabelX2 = sLabelX + (getWidth() / NDECADES);
						int sLabelY2 = (getHeight() - GRAPH_MARGIN_SIZE) - LABEL_BORDER;
						
						Nlabel.setFont("Helvetica-14");
						Nlabel.setColor(color);
						
						add(Nlabel, sLabelX2, sLabelY2);
					}
					
					// Add the circles
					GOval ovalShape = new GOval(10, 10);
					ovalShape.setFilled(true);
					ovalShape.setFillColor(color);
					add(ovalShape, lineX1 - (ovalShape.getWidth()/2), (GRAPH_MARGIN_SIZE + lineY1) - ovalShape.getHeight()/2);
					
				} else if (currentEntry.getRank(j) != 0 && currentEntry.getRank(j+1) != 0) {	// If the rank returns an integers greater than one for both it and the one after it.

					// First Coordinates
					// x1 = 0; at first
					lineY1 = (getHeight() - GRAPH_MARGIN_SIZE*2)*(currentEntry.getRank(j));
					lineY1 -= MAX_RANK;
					lineY1 /= MAX_RANK;
					// Second Coordinates
					lineX2 = lineX1 + (getWidth() / NDECADES);
					lineY2 = (getHeight() - GRAPH_MARGIN_SIZE*2)*(currentEntry.getRank(j+1));
					lineY2 -= MAX_RANK;
					lineY2 /= MAX_RANK;
					
					// Set up Label variables
					sLabel = currentEntry.getName() + (" " + currentEntry.getRank(j));
					sLabelX = lineX1 + LABEL_BORDER;
					sLabelY = lineY1 - LABEL_BORDER;
					
					// Add the last label
					if (j == NDECADES - 2) {
						String sLabel1 = currentEntry.getName() + (" " + currentEntry.getRank(j+1));
						GLabel Nlabel = new GLabel(sLabel1);
						int lineY3 = (getHeight() - GRAPH_MARGIN_SIZE*2)*(currentEntry.getRank(j+1));
						lineY3 -= MAX_RANK;
						lineY3 /= MAX_RANK;
						int sLabelX2 = sLabelX + (getWidth() / NDECADES);
						int sLabelY2 = (lineY3 + GRAPH_MARGIN_SIZE) - LABEL_BORDER;
						
						Nlabel.setFont("Helvetica-14");
						Nlabel.setColor(color);
						
						add(Nlabel, sLabelX2, sLabelY2);
					}
					
					// Add the circles
					GOval ovalShape = new GOval(10, 10);
					ovalShape.setFilled(true);
					ovalShape.setFillColor(color);
					add(ovalShape, lineX2 - (ovalShape.getWidth()/2), (GRAPH_MARGIN_SIZE + lineY2) - ovalShape.getHeight()/2);
				}
				// Draws the line per Rank
				GLine line = new GLine(lineX1, GRAPH_MARGIN_SIZE + lineY1, lineX2, GRAPH_MARGIN_SIZE + lineY2);
				line.setColor(color);
				add(line);
				// Draws the label per Rank
				GLabel label = new GLabel(sLabel);
				label.setFont("Helvetica-14");
				label.setColor(color);
				add(label, sLabelX, sLabelY + GRAPH_MARGIN_SIZE);
				
				lineX1 += getWidth() / NDECADES;	// Increment the x coordinate to the next decade.
			}
		}
	}
	
	// Method: setColor
	// Sets the color of the line
	private void setColor(int i) {
		// For each entry, draw the first in black. The second in red, the third in blue, and the fourth in magenta then they cycle.
		switch (i%4) {	// Should cycle through colors
			case 0:
				color = Color.BLACK;
				break;
			case 1:
				color = Color.RED;
				break;
			case 2:
				color = Color.BLUE;
				break;
			case 3:
				color = Color.MAGENTA;
				break;
			default:
				break;
		}
	}
	
	// Method: setUpBackground
	// Sets up the background with vertical and horizontal lines
	private void setUpBackground() {
		// create vertical lines, 11 of them. 
		int x1 = 0;
		int y1 = getHeight();
		int x2 = 0;
		int y2 = 0;
		int temp = 0;
		
		for (int i = 0; i < NDECADES; i++) {
			// Create vertical lines, 11 of them
			GLine line = new GLine(x1, y1, x2, y2);
			add(line);
			// Create 11 labels that increment by 10.
			GLabel label = new GLabel("" + (START_DECADE + temp), x1 + LABEL_BORDER, getHeight() - LABEL_BORDER);
			add(label);
			
			x1 += getWidth() / NDECADES;
			x2 += getWidth() / NDECADES;
			temp += 10;
		}

		// Create horizontal lines
		GLine Hline1 = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		GLine Hline2 = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(Hline1);
		add(Hline2);

		
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	// Private Instance Variables
	private ArrayList<NameSurferEntry> entries;
	private int lineX1;
	private int lineY1;
	private int lineX2;
	private int lineY2;
	private String sLabel;
	private int sLabelX; 
	private int sLabelY;
	
	private Color color;
	
	private int typeOfGraph;
}
