package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Solution;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;

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
		ArrayList<Card> cards = board.getCards();
		assertEquals(21, cards.size());
		assertTrue(cards.contains(new Card("Charlie", Card.CardType.PERSON)));
		assertTrue(cards.contains(new Card("Jan", Card.CardType.PERSON)));
		assertTrue(cards.contains(new Card("Oralee", Card.CardType.PERSON)));		
		assertTrue(cards.contains(new Card("Gary", Card.CardType.PERSON)));
		assertTrue(cards.contains(new Card("Kyle", Card.CardType.PERSON)));
		assertTrue(cards.contains(new Card("Mary", Card.CardType.PERSON)));
		
		assertTrue(cards.contains(new Card("Knife", Card.CardType.WEAPON)));
		assertTrue(cards.contains(new Card("Hammer", Card.CardType.WEAPON)));
		assertTrue(cards.contains(new Card("Axe", Card.CardType.WEAPON)));
		assertTrue(cards.contains(new Card("Pool Stick", Card.CardType.WEAPON)));
		assertTrue(cards.contains(new Card("Poison", Card.CardType.WEAPON)));
		assertTrue(cards.contains(new Card("Cable", Card.CardType.WEAPON)));
		
		assertTrue(cards.contains(new Card("Family Room", Card.CardType.ROOM)));
		assertTrue(cards.contains(new Card("Kitchen", Card.CardType.ROOM)));
		assertTrue(cards.contains(new Card("Dining Room", Card.CardType.ROOM)));
	}
	
	@Test
	public void TestPlayerLoaded() {
		ArrayList<Player> players = board.getPlayers();
		assertEquals(6, players.size());
		
		for (var i: players) {
			assertTrue(i.getName() != "");
			assertFalse(i.getColor().equals(null));
		}
	}
	
	@Test
	public void TestSolutionDealt() {
		Solution solution = board.getSolution();

		assertEquals(Card.CardType.PERSON, solution.getPerson().getType());
		assertEquals(Card.CardType.WEAPON, solution.getWeapon().getType());
		assertEquals(Card.CardType.ROOM, solution.getRoom().getType());
	}
	
	@Test
	public void TestPlayerCards() {
		ArrayList<Player> players = board.getPlayers();
		
		for (var i : players) {
			assertEquals(3, i.getCards().size());
		}
	}
}
