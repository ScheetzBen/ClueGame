package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player{

	// Constructor to set the name and color of a HumanPlayer
	public HumanPlayer(String name, Color color) {
		super(name, color);
	}
	
	// Abstract method inherited from Player
	// Not currently implemented
	@Override
	void updateHand() {
		// TODO Auto-generated method stub
		
	}
}
