package clueGame;

import java.awt.Color;
import java.util.ArrayList;

abstract class Player {
	private String name;
	private Color color;
	private int row, column;
	private ArrayList<Card> cards;
	
	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	abstract void updateHand();
}
