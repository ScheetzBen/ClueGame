package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;
import clueGame.Card;

public class GameSetupTests {
	private static Board board;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
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
		
	}
}
