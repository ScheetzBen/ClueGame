package clueGame;

import java.awt.Color;
import java.util.ArrayList;

abstract class Player {
	// Variables for Player objects
	private String name;
	private Color color;
	private int row, column;
	private ArrayList<Card> cards;
	
	// Constructor sets the name and Color of a Player
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	// Abstract method used to update the hand of a Player
	abstract void updateHand();
	
	// Getters for Player
	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public ArrayList<Card> getCards() {
		return new ArrayList<Card>();
	}
}
