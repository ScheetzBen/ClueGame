package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Set;

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
		Set<Card> personCards = board.getPersonCards();
		assertEquals(6, personCards.size());
		assertTrue(personCards.contains(new Card("Charlie", Card.CardType.PERSON)));
		assertTrue(personCards.contains(new Card("Jan", Card.CardType.PERSON)));
		assertTrue(personCards.contains(new Card("Oralee", Card.CardType.PERSON)));		
		assertTrue(personCards.contains(new Card("Gary", Card.CardType.PERSON)));
		assertTrue(personCards.contains(new Card("Kyle", Card.CardType.PERSON)));
		assertTrue(personCards.contains(new Card("Mary", Card.CardType.PERSON)));
		
		Set<Card> weaponCards = board.getWeaponCards();
		assertTrue(weaponCards.contains(new Card("Knife", Card.CardType.WEAPON)));
		assertTrue(weaponCards.contains(new Card("Hammer", Card.CardType.WEAPON)));
		assertTrue(weaponCards.contains(new Card("Axe", Card.CardType.WEAPON)));
		assertTrue(weaponCards.contains(new Card("Pool Stick", Card.CardType.WEAPON)));
		assertTrue(weaponCards.contains(new Card("Poison", Card.CardType.WEAPON)));
		assertTrue(weaponCards.contains(new Card("Cable", Card.CardType.WEAPON)));
		
		Set<Card> roomCards = board.getRoomCards();
		assertTrue(roomCards.contains(new Card("Family Room", Card.CardType.ROOM)));
		assertTrue(roomCards.contains(new Card("Kitchen", Card.CardType.ROOM)));
		assertTrue(roomCards.contains(new Card("Dining Room", Card.CardType.ROOM)));
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
