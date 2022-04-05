package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Solution;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSolutionTest {
	private static Board board;
	private static Solution solution;
	private static Card person, weapon, room;

	@BeforeAll
	public static void setUp() {
		board = Board.getInstance();
		
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
		board.deal();
		
		solution = new Solution(new Card("Charlie", Card.CardType.PERSON), new Card("Knife", Card.CardType.WEAPON), new Card("Kitchen", Card.CardType.ROOM));
		
		person = new Card("Gary", Card.CardType.PERSON);
		weapon = new Card("Poison", Card.CardType.WEAPON);
		room = new Card("Master Bedroom", Card.CardType.ROOM);
	}
	
	@Test
	public void checkAccusationTest() {
		// Testing correct solution
		Solution currSolution = new Solution(board.getSolution().getPerson(), board.getSolution().getWeapon(), board.getSolution().getRoom());
		
		assertTrue(board.checkAccusation(currSolution));
		
		// Testing wrong person
		currSolution.setPerson(person);
		
		assertFalse(board.checkAccusation(currSolution));
		
		// Testing wrong weapon
		currSolution.setPerson(board.getSolution().getPerson());
		
		currSolution.setWeapon(weapon);
		
		assertFalse(board.checkAccusation(currSolution));
		
		// Testing wrong room
		currSolution.setWeapon(board.getSolution().getWeapon());
		
		currSolution.setRoom(room);
		
		assertFalse(board.checkAccusation(currSolution));
	}
	
	@Test
	public void disproveSuggestionTest() {
		HumanPlayer player = new HumanPlayer("Charlie", Color.BLACK);

		// Testing player has no matching Cards
		assertEquals(null, player.disproveSuggestion(solution));

		// Testing player has 1 matching Card
		player.addCard(solution.getPerson());

		assertEquals(solution.getPerson(), player.disproveSuggestion(solution));

		// Testing player has 2 matching Cards
		player.addCard(solution.getWeapon());

		Card givenCard = player.disproveSuggestion(solution);

		Card[] possibleCards = new Card[]{solution.getPerson(), solution.getWeapon()};
		boolean isCardCorrect = false;

		for (Card i : possibleCards) 
			if (i.equals(givenCard)) isCardCorrect = true;

		assertTrue(isCardCorrect);
		
		// Testing for ComputerPlayer
		ComputerPlayer cPlayer = new ComputerPlayer("Charlie", Color.BLACK);

		// Testing player has no matching Cards
		assertEquals(null, cPlayer.disproveSuggestion(solution));

		// Testing player has 1 matching Card
		cPlayer.addCard(solution.getPerson());

		assertEquals(solution.getPerson(), cPlayer.disproveSuggestion(solution));

		// Testing player has 2 matching Cards
		cPlayer.addCard(solution.getWeapon());

		givenCard = cPlayer.disproveSuggestion(solution);
		
		isCardCorrect = false;

		for (Card i : possibleCards) 
			if (i.equals(givenCard)) isCardCorrect = true;

		assertTrue(isCardCorrect);
	}
	
	@Test
	public void handleSuggestionTest() {
		Player[] players = new Player[]{new HumanPlayer("Human", Color.BLACK), new ComputerPlayer("C1", Color.BLACK), new ComputerPlayer("C2", Color.BLACK)};
		
		board.setPlayers(players);
		
		players[0].addCard(person);
		players[1].addCard(weapon);
		players[2].addCard(room);
		
		// Suggestion that no one can disprove
		assertEquals(null, board.handleSuggestion(solution, new ComputerPlayer("cTemp", Color.BLACK)));
		
		// Suggestion that only accusing player can disprove
		assertEquals(null, board.handleSuggestion(new Solution(person, solution.getWeapon(), solution.getRoom()), players[0]));
		
		// Suggestion that only HumanPlayer can disprove
		assertEquals(person, board.handleSuggestion(new Solution(person, solution.getWeapon(), solution.getRoom()), players[1]));
		
		// Suggestion that 2 players can disprove
		assertEquals(weapon, board.handleSuggestion(new Solution(solution.getPerson(), weapon, room), players[0]));
		
		ArrayList<Card> c1Cards = players[1].getCards();
		// only Card should be weapon which means that handleSuggestion just returned the Card from the first Player which could disprove
		assertEquals(weapon, c1Cards.get(0));
	}
}
