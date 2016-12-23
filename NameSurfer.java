/*
 * File: NameSurfer.java
 * Team Partner: Doris Rodriquez
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

    // CONSTANTS
	private static final int NAME_FIELD_SIZE = 25;
	
	
/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the top of the window.
 */
	public void init() {
		// Initialize graph - adding graphics
		graph = new NameSurferGraph();
		add(graph);
		
		// Initializing Interactors
		Name = new JLabel("Name:");	// Initializing Name Label
		add(Name, NORTH);
		
		nameField = new JTextField(NAME_FIELD_SIZE);	// Initializing nameField text label
		nameField.setActionCommand("Graph");
		nameField.addActionListener(this);
		add(nameField, NORTH);
		
		// Adding a checkbox to either graph a line graph or bar graph. 
		pickType = new JComboBox();
		pickType.addItem("Bar Graph");
		pickType.addItem("Line Graph");
		// Don't allow user to type in a type.
		pickType.setSelectedItem(false);
		// Add a label with separating spaces for combo box
		add(new JLabel(" Type:"), NORTH);
		add(pickType, NORTH);
		
		
		graphButton = new JButton("Graph");	// Initializing graphButton button
		graphButton.setActionCommand("Graph");
		add(graphButton, NORTH);
		
		clearButton = new JButton("Clear");	// Initializing clearButton button
		add(clearButton, NORTH);
		
		addActionListeners();	// Turn on Action listeners to hear buttons clicked.
		
		
		// Initialize database.
		nameDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		
		
		
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		// Do something depending on which button is clicked. 
		if (e.getActionCommand().equals("Graph")) {
			String nameEntered = nameField.getText();
			// Turn nameEntered into the upCase format.
			System.out.println("Graph: " + nameEntered);
			String nameEnteredUpCase = updateName(nameEntered);
			correspondingEntry = nameDataBase.findEntry(nameEnteredUpCase);
			if (correspondingEntry == null) {
				System.out.println("That doesn't exist");
			} else {
				System.out.println(correspondingEntry.toString());
//				graph.addEntry(correspondingEntry);
				
				String type = (String) pickType.getSelectedItem();
				// Chooses what parameter to pass in depending on which was selected.
				if (type.equals("Line Graph")) {
					graph.addEntry(correspondingEntry);
					graph.set(type);
					graph.update();
				} else if (type.equals("Bar Graph")) {
					graph.clear();
					graph.addEntry(correspondingEntry);
					graph.set(type);
					graph.update();
				}
				
			}
		}
		// If the clear button is clicked. Clear the field. 
		if (e.getSource() == clearButton) {
			System.out.println("Clear");
			graph.clear();
			graph.update();
		}
	}
	
	// Method: updateName
	// This returns the name lowercase, with the first letter uppercase.
	private String updateName(String name) {
		String newName = name.toUpperCase().charAt(0) + name.substring(1).toLowerCase();
		return newName;
	}
	
	
	// Private Instance Variables
	private JLabel Name;
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	
	// Radio Buttons
	private JComboBox pickType;
	
	// Name Surfer Instance Variables.
	private NameSurferDataBase nameDataBase;
	private NameSurferEntry correspondingEntry;
	private NameSurferGraph graph;
}
