package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player {
	// Variables for Player objects
	private String name;
	private Color color;
	private int row, column;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Card> seen = new ArrayList<Card>();
	
	// Constructor sets the name and Color of a Player
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	// Constructor which can also set the row and column of the Player
	public Player(String name, Color color, int row, int column) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}
	
	// Function to see if a player can disprove a suggestion
	public Card disproveSuggestion(Solution suggestion) {
		Card[] sugg = new Card[3];
		
		sugg[0] = suggestion.getPerson();
		sugg[1] = suggestion.getWeapon();
		sugg[2] = suggestion.getRoom();
		
		ArrayList<Card> disproveHold = new ArrayList<Card>();
		
		// For loop checks if any card in the hand matched any card in suggested
		for (Card suggCards : sugg) {
			for (Card hand : this.cards) {
				if (suggCards.equals(hand)) disproveHold.add(hand);
			}
		}
		
		if (disproveHold.size() > 1) {
			Random rnd = new Random();
			
			return disproveHold.get(rnd.nextInt(disproveHold.size()));
			
		} else if (!disproveHold.isEmpty()) {
			return disproveHold.get(0);
		}
		
		return null;
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
		return cards;
	}
	
	public ArrayList<Card> getSeen() {
		return seen;
	}

	// Setters for Player
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public void addSeen(Card card) {
		seen.add(card);
	}
	
	public void setPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
