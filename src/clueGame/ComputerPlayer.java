package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private Solution accusation;
	
	// Constructor to set the name and color of a ComputerPlayer
	public ComputerPlayer(String name, Color color) {
		super(name, color);
	}
	
	// Constructor which can also set the position of a ComputerPlayer
	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
	}
	
	// Function to randomly make a suggestion based on the Room the ComputerPlayer is in
	@Override
	public void makeSuggestion(Board board) {
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
		
		board.handleSuggestion(temp, this);
	}
	
	public Solution suggestion(Board board) {
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
	
	// Function to randomly select a target given a list of possible targets
	public BoardCell selectTarget(Set<BoardCell> targets, Board board) {
		if (accusation != null) board.handleAccusation(accusation);
		
		for (var i : targets) {
			if (i.isRoomCenter() && !getSeen().contains(board.getRoom(i).getCard())) return i;
		}
		
		ArrayList<BoardCell> tempTargets = new ArrayList<BoardCell>(targets);
		
		Random rnd = new Random();
		
		return tempTargets.get(rnd.nextInt(tempTargets.size()));
	}
	
	public void setAccusation(Solution accusation) {
		this.accusation = accusation;
	}
}
