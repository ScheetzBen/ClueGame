package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Solution;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class ComputerAITest {
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueComputerTestLayout.csv", "ClueComputerTestSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		board.deal();
	}
	
	@Test
	public void createSuggestionTest() {
		ComputerPlayer currPlayer = (ComputerPlayer) board.getPlayers().get(1);
		
		currPlayer.setPosition(2, 2);
		
		Solution currSugg = currPlayer.createSuggestion();
		
		// Testing that the room the ComputerPlayer is in appears in the suggestion created
		assertEquals(board.getRoom(currPlayer.getRow(), currPlayer.getColumn()).getCard(), currSugg.getRoom());
		
		int p = 0, w = 0;
		
		// Testing that a random weapon and room are selected
		for (var i : board.getCards()) {
			if (i.getType() == Card.CardType.PERSON) 
				if (currSugg.getPerson().equals(i)) p++;
			if (i.getType() == Card.CardType.WEAPON)
				if (currSugg.getWeapon().equals(i)) w++;
		}
		
		assertEquals(1, p);
		assertEquals(1, w);
		
		// Testing that unseen cards are selected when all other cards are seen
		currPlayer.addSeen(new Card("Knife", Card.CardType.WEAPON));
		currPlayer.addSeen(new Card("Charlie", Card.CardType.PERSON));
		
		currSugg = currPlayer.createSuggestion();
		
		assertEquals(new Card("Jan", Card.CardType.PERSON), currSugg.getPerson());
		assertEquals(new Card("Hammer", Card.CardType.WEAPON), currSugg.getWeapon());
	}
	
	@Test
	public void selectTargetTest() {
		ComputerPlayer currPlayer = (ComputerPlayer) board.getPlayers().get(1);
		
		currPlayer.setPosition(2, 5);
		
		// Roll of 1 includes no rooms
		board.calcTargets(board.getCell(currPlayer.getRow(), currPlayer.getColumn()), 1);
		
		Set<BoardCell> targets = board.getTargets();
		
		boolean targetCorrect = false;
		
		BoardCell selected = currPlayer.selectTarget(targets);
		
		// Testing that random target was selected
		for (var i : targets) {
			if (i == selected) targetCorrect = true;
		}
		
		assertTrue(targetCorrect);
		
		currPlayer.addSeen(new Card("BRoom", Card.CardType.ROOM));
		
		// Roll of 3 includes 2 rooms 1 is seen
		board.calcTargets(board.getCell(currPlayer.getRow(), currPlayer.getColumn()), 3);
		
		// Test whether the ComputerPlayer entered the unseen Room
		assertEquals(board.getRoom('R'), board.getRoom(currPlayer.selectTarget(targets)));
		
		currPlayer.addSeen(new Card("Room", Card.CardType.ROOM));
		
		// Roll of 3 includes 2 Rooms both are seen
		board.calcTargets(board.getCell(currPlayer.getRow(), currPlayer.getColumn()), 3);
		
		selected = currPlayer.selectTarget(targets);
		
		targetCorrect = false;
		
		// Testing that random target was selected
		for (var i : targets) {
			if (i == selected) targetCorrect = true;
		}
		
		assertTrue(targetCorrect);
	}
}
