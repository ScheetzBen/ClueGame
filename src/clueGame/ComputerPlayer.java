package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	public ComputerPlayer(String name, Color color) {
		super(name, color);
	}
	
	public Solution createSuggestion() {
		return new Solution(new Card("", Card.CardType.PERSON), new Card("", Card.CardType.PERSON), new Card("", Card.CardType.PERSON));
	}
	
	public BoardCell selectTarget(Set<BoardCell> targets) {
		return new BoardCell(0, 0, 'A');
	}

	@Override
	void updateHand() {
		// TODO Auto-generated method stub
		
	}
}
