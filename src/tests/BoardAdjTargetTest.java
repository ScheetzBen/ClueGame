package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
		// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;
		
		@BeforeAll
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
			// Initialize will load config files 
			board.initialize();
		}

		// Ensure that player does not move around within room
		// These cells are GREY on the planning spreadsheet
		@Test
		public void testAdjacenciesRooms()
		{
			// we want to test a couple of different rooms.
			// First, the M that has 3 doors and a secret room
			Set<BoardCell> testList = board.getAdjList(7,2);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(11, 6)));
			assertTrue(testList.contains(board.getCell(10, 6)));
			assertTrue(testList.contains(board.getCell(12, 0)));
			assertTrue(testList.contains(board.getCell(21, 20)));
			
			// now test F
			//In F there are 2 entrances and a trap door that we need to account for
			testList = board.getAdjList(21, 20);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(18, 17)));
			assertTrue(testList.contains(board.getCell(18, 18)));
			assertTrue(testList.contains(board.getCell(7, 2)));

			
			// one more room, the Dining Room, with 6 doors
			testList = board.getAdjList(12, 11);
			assertEquals(6, testList.size());
			assertTrue(testList.contains(board.getCell(8, 10)));
			assertTrue(testList.contains(board.getCell(8, 11)));
			assertTrue(testList.contains(board.getCell(12, 8)));
			assertTrue(testList.contains(board.getCell(12, 14)));
			assertTrue(testList.contains(board.getCell(16, 11)));
			assertTrue(testList.contains(board.getCell(16, 12)));
		}

		
		// Ensure door locations include their rooms and also additional walkways
		// These cells are GREY on the planning spreadsheet
		@Test
		public void testAdjacencyDoor()
		{
			//Test door for M room. 
			Set<BoardCell> testList = board.getAdjList(12, 0);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(7, 2)));
			assertTrue(testList.contains(board.getCell(13, 0)));
			assertTrue(testList.contains(board.getCell(12, 1)));
			
			//O Room door
			testList = board.getAdjList(13, 6);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(13, 7)));
			assertTrue(testList.contains(board.getCell(13, 5)));
			assertTrue(testList.contains(board.getCell(12, 6)));
			assertTrue(testList.contains(board.getCell(17, 5)));
			
			// D room Door
			testList = board.getAdjList(12, 8);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(11, 8)));
			assertTrue(testList.contains(board.getCell(13, 8)));
			assertTrue(testList.contains(board.getCell(12, 11)));
			assertTrue(testList.contains(board.getCell(12, 7)));
		}
		
		// Test a variety of walkway scenarios
		// These tests are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			// Test adjacent to a door
			Set<BoardCell> testList = board.getAdjList(13, 1);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(13, 0)));
			assertTrue(testList.contains(board.getCell(12, 1)));
			assertTrue(testList.contains(board.getCell(13, 2)));
			
			// Test near a door but not adjacent
			testList = board.getAdjList(7, 7);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(7, 8)));
			assertTrue(testList.contains(board.getCell(7, 6)));
			assertTrue(testList.contains(board.getCell(8, 7)));
			assertTrue(testList.contains(board.getCell(6, 7)));

			// Test adjacent to Unused Spaces
			testList = board.getAdjList(2, 17);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(2, 16)));
			assertTrue(testList.contains(board.getCell(3, 17)));

			// Test adjacent to 2 doors
			testList = board.getAdjList(12, 6);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(12, 7)));
			assertTrue(testList.contains(board.getCell(12, 5)));
			assertTrue(testList.contains(board.getCell(11, 6)));
			assertTrue(testList.contains(board.getCell(13, 6)));
		
		}
		
		
		// Tests out of room center, 1, 3 and 4
		// These are DARK BLUE on the planning spreadsheet
		@Test
		public void testTargetsInOffice() {
			// test a roll of 1
			board.calcTargets(board.getCell(17, 5), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(13, 6)));
			assertTrue(targets.contains(board.getCell(19, 3)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(17, 5), 3);
			targets= board.getTargets();
			assertEquals(9, targets.size());
			assertTrue(targets.contains(board.getCell(17, 3)));
			assertTrue(targets.contains(board.getCell(20, 4)));	
			assertTrue(targets.contains(board.getCell(13, 4)));
			assertTrue(targets.contains(board.getCell(12, 5)));
			assertTrue(targets.contains(board.getCell(11, 6)));
			assertTrue(targets.contains(board.getCell(12, 7)));	
			assertTrue(targets.contains(board.getCell(13, 8)));	
			assertTrue(targets.contains(board.getCell(14, 7)));
			// Moving into Rest Room
			assertTrue(targets.contains(board.getCell(16, 1)));
			
			// test a roll of 4
			board.calcTargets(board.getCell(17, 5), 4);
			targets= board.getTargets();
			assertEquals(14, targets.size());
			assertTrue(targets.contains(board.getCell(20, 5)));
			assertTrue(targets.contains(board.getCell(16, 3)));
			// Moving into Master Bedroom and Rest Room
			assertTrue(targets.contains(board.getCell(16, 1)));
			assertTrue(targets.contains(board.getCell(7, 2)));	
		}
		
		@Test
		public void testTargetsInKitchen() {
			// test a roll of 1
			board.calcTargets(board.getCell(4, 12), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(2, 8)));
			assertTrue(targets.contains(board.getCell(7, 10)));	
			assertTrue(targets.contains(board.getCell(7, 11)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(4, 12), 3);
			targets= board.getTargets();
			assertEquals(13, targets.size());
			assertTrue(targets.contains(board.getCell(0, 8)));
			assertTrue(targets.contains(board.getCell(7, 12)));
			// Stops on door
			assertTrue(targets.contains(board.getCell(8, 11)));
			//Moving into Dining Room
			assertTrue(targets.contains(board.getCell(12, 11)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(4, 12), 4);
			targets= board.getTargets();
			assertEquals(22, targets.size());
			// Stopping on kitchen doors
			assertTrue(targets.contains(board.getCell(7, 10)));
			assertTrue(targets.contains(board.getCell(7, 11)));
			// Stopping on Dining Room doors
			assertTrue(targets.contains(board.getCell(8, 10)));
			assertTrue(targets.contains(board.getCell(8, 11)));
			// Stopping in Dining Room
			assertTrue(targets.contains(board.getCell(12, 11)));
		}

		// Tests out of room center, 1, 3 and 4
		// These are DARK BLUE on the planning spreadsheet
		@Test
		public void testTargetsAtDoor() {
			// test a roll of 1, at door
			board.calcTargets(board.getCell(18, 18), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(18, 17)));
			assertTrue(targets.contains(board.getCell(18, 19)));	
			assertTrue(targets.contains(board.getCell(17, 18)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(18, 18), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(19, 16)));
			assertTrue(targets.contains(board.getCell(17, 16)));
			assertTrue(targets.contains(board.getCell(17, 18)));
			// Landing on doorway
			assertTrue(targets.contains(board.getCell(18, 17)));
			// Moving into family room
			assertTrue(targets.contains(board.getCell(21, 20)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(18, 18), 4);
			targets= board.getTargets();
			assertEquals(7, targets.size());
			assertTrue(targets.contains(board.getCell(17, 15)));
			assertTrue(targets.contains(board.getCell(16, 16)));
			assertTrue(targets.contains(board.getCell(17, 17)));	
			assertTrue(targets.contains(board.getCell(18, 16)));
			// Moving into Family Room
			assertTrue(targets.contains(board.getCell(21, 20)));	
		}

		@Test
		public void testTargetsInWalkway1() {
			// test a roll of 1
			board.calcTargets(board.getCell(10, 15), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(10, 16)));
			assertTrue(targets.contains(board.getCell(10, 14)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(10, 15), 3);
			targets= board.getTargets();
			assertEquals(10, targets.size());
			assertTrue(targets.contains(board.getCell(12, 14)));
			assertTrue(targets.contains(board.getCell(10, 14)));
			assertTrue(targets.contains(board.getCell(10, 16)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(10, 15), 4);
			targets= board.getTargets();
			assertEquals(16, targets.size());
			assertTrue(targets.contains(board.getCell(13, 16)));
			// Moving into rooms
			assertTrue(targets.contains(board.getCell(12, 11)));
			assertTrue(targets.contains(board.getCell(13, 20)));	
		}

		@Test
		public void testTargetsInWalkway2() {
			// test a roll of 1
			board.calcTargets(board.getCell(25, 6), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(24, 6)));
			assertTrue(targets.contains(board.getCell(25, 7)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(25, 6), 3);
			targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(24, 6)));
			assertTrue(targets.contains(board.getCell(25, 7)));	
			assertTrue(targets.contains(board.getCell(22, 6)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(25, 6), 4);
			targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(23, 6)));
			assertTrue(targets.contains(board.getCell(24, 7)));
			assertTrue(targets.contains(board.getCell(22, 7)));	
		}

		@Test
		// test to make sure occupied locations do not cause problems
		public void testTargetsOccupied() {
			// test a roll of 4 blocked 2 down
			board.getCell(8, 21).setOccupied(true);
			board.calcTargets(board.getCell(6, 21), 4);
			board.getCell(8, 21).setOccupied(false);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(6, 17)));
			assertTrue(targets.contains(board.getCell(7, 18)));
			assertTrue(targets.contains(board.getCell(6, 19)));
			// Moving into Sun Room
			assertTrue(targets.contains(board.getCell(3, 21)));
			// Places not reachable
			assertFalse(targets.contains(board.getCell(8, 21)));
		
			// we want to make sure we can get into a room, even if flagged as occupied
			// Cell to the right is occupied
			board.getCell(17, 10).setOccupied(true);
			// Room is occupied
			board.getCell(22, 12).setOccupied(true);
			board.calcTargets(board.getCell(17, 9), 1);
			board.getCell(17, 10).setOccupied(false);
			board.getCell(22, 12).setOccupied(false);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(16, 9)));	
			assertTrue(targets.contains(board.getCell(17, 8)));
			// Room is in targets
			assertTrue(targets.contains(board.getCell(22, 12)));	
			
			// check leaving a room with a blocked doorway
			board.getCell(6, 19).setOccupied(true);
			board.calcTargets(board.getCell(3, 21), 3);
			board.getCell(6, 19).setOccupied(false);
			targets= board.getTargets();
			assertEquals(1, targets.size());
			// Room has 1 door, but also a secret passage
			assertTrue(targets.contains(board.getCell(16, 1)));
		}
}
