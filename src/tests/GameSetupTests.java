package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;
import clueGame.Card;
import clueGame.ComputerPlayer;

public class GameSetupTests {
	private static Board board;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		board.deal();
	}
	
	@Test
	public void TestCardNames() {
		assertEquals(Card.CardType.PERSON, board.getCard("Charlie").getType());
		assertEquals(Card.CardType.PERSON, board.getCard("Jan").getType());
		assertEquals(Card.CardType.PERSON, board.getCard("Oralee").getType());
		assertEquals(Card.CardType.PERSON, board.getCard("Gary").getType());
		assertEquals(Card.CardType.PERSON, board.getCard("Kyle").getType());
		assertEquals(Card.CardType.PERSON, board.getCard("Mary").getType());
		
		assertEquals(Card.CardType.WEAPON, board.getCard("Knife").getType());
		assertEquals(Card.CardType.WEAPON, board.getCard("Hammer").getType());
		assertEquals(Card.CardType.WEAPON, board.getCard("Axe").getType());
		assertEquals(Card.CardType.WEAPON, board.getCard("Pool Stick").getType());
		assertEquals(Card.CardType.WEAPON, board.getCard("Poison").getType());
		assertEquals(Card.CardType.WEAPON, board.getCard("Cable").getType());
		
		assertEquals(Card.CardType.ROOM, board.getCard("Family Room").getType());
		assertEquals(Card.CardType.ROOM, board.getCard("Kitchen").getType());
		assertEquals(Card.CardType.ROOM, board.getCard("Dining Room").getType());
	}
	
	@Test
	public void TestPlayerLoaded() {
		assertTrue(board.getCard(board.getHumanPlayer().getName()).getCardName() != "");
		assertTrue(board.getHumanPlayer().getColor() != null);
		
		ComputerPlayer[] ai = board.getComputerPlayers();
		assertEquals(5, ai.length);
		
		for (var i: ai) {
			assertTrue(board.getCard(i.getName()).getCardName() != "");
			assertTrue(i.getColor() != null);
		}
	}
	
	@Test
	public void TestSolutionDealt() {
		Card[] solution = board.getSolution();
		
		assertEquals(3, solution.length);
		
		int p = 0, w = 0, r = 0;
		
		for (var i : solution) {
			if (i.getType() == Card.CardType.PERSON) p++;
			else if (i.getType() == Card.CardType.WEAPON) w++;
			else if (i.getType() == Card.CardType.ROOM) r++;
		}
		
		assertEquals(1, p);
		assertEquals(1, w);
		assertEquals(1, r);
	}
	
	@Test
	public void TestPlayerCards() {
		assertEquals(3, board.getHumanPlayer().getCards().size());
		
		ComputerPlayer[] ai = board.getComputerPlayers();
		
		for (var i : ai) {
			assertEquals(3, i.getCards().size());
		}
	}
}
