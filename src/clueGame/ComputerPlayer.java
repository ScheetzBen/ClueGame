package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	public ComputerPlayer(String name, Color color) {
		super(name, color);
	}
	
	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
	}
	
	public Solution createSuggestion(Board board) {
		Solution temp = new Solution(null, null, null);
		
		while (temp.getPerson() == null || temp.getWeapon() == null) {
			Random rnd = new Random();
			
			Card i = board.getCards().get(rnd.nextInt(board.getCards().size()));
			
			if (!getSeen().contains(i)) {
				if (i.getType() == Card.CardType.PERSON && temp.getPerson() == null) temp.setPerson(i);
				else if (i.getType() == Card.CardType.WEAPON && temp.getWeapon() == null) temp.setWeapon(i);
			}
		}

		temp.setRoom(board.getRoom(getRow(), getColumn()).getCard());
		
		return temp;
	}
	
	public BoardCell selectTarget(Set<BoardCell> targets, Board board) {
		for (var i : targets) {
			if (i.isRoomCenter() && !getSeen().contains(board.getRoom(i).getCard())) return i;
		}
		
		ArrayList<BoardCell> tempTargets = new ArrayList<BoardCell>(targets);
		
		Random rnd = new Random();
		
		return tempTargets.get(rnd.nextInt(tempTargets.size()));
	}

	@Override
	void updateHand() {
		// TODO Auto-generated method stub
		
	}
}
