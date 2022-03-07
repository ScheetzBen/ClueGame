package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.*;
import experiment.*;
import static org.junit.Assert.*;

public class BoardTestsExp {
	
	private TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	
	/*
	 * Tests adjacencies
	 */
	@Test
	public void testAdjacency() {
		TestBoardCell topLeft = board.getCell(0,0);
		Set<TestBoardCell> topLeftadj = topLeft.getAdjList();
		assertTrue(topLeftadj.contains(board.getCell(1, 0)));
		assertTrue(topLeftadj.contains(board.getCell(0, 1)));
		assertEquals(2, topLeftadj.size());
		
		TestBoardCell bottomRight = board.getCell(3,3);
		Set<TestBoardCell> bottomRightadj = bottomRight.getAdjList();
		assertTrue(bottomRightadj.contains(board.getCell(2, 3)));
		assertTrue(bottomRightadj.contains(board.getCell(3, 2)));
		assertEquals(2, bottomRightadj.size());
		
		TestBoardCell rightEdge = board.getCell(1,3);
		Set<TestBoardCell> rightEdegeadj = rightEdge.getAdjList();
		assertTrue(rightEdegeadj.contains(board.getCell(0, 3)));
		assertTrue(rightEdegeadj.contains(board.getCell(1, 2)));
		assertTrue(rightEdegeadj.contains(board.getCell(2, 3)));
		assertEquals(3, rightEdegeadj.size());
		
		TestBoardCell leftEdge = board.getCell(2,0);
		Set<TestBoardCell> leftEdgeadj = leftEdge.getAdjList();
		assertTrue(leftEdgeadj.contains(board.getCell(1, 0)));
		assertTrue(leftEdgeadj.contains(board.getCell(2, 1)));
		assertTrue(leftEdgeadj.contains(board.getCell(3, 0)));
		assertEquals(3, leftEdgeadj.size());
	}
	
	/*
	 * Tests
	 * */
	@Test
	public void testTargetsNormal() {
		// Test roll of 3 starting at (0,0)
		TestBoardCell cell = board.getCell(0,0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		
		// Test roll of 2 starting at (2,2)
		cell = board.getCell(2,2);
		board.calcTargets(cell, 2);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		
		// Test roll of 3  starting at (3,2)
		cell = board.getCell(3,2);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		
	}
	//Test that checks 
	@Test
	public void testTargetsMixed() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		board.getCell(0, 2).setOccupied(false);
		board.getCell(1, 2).setRoom(false);
		
		board.getCell(3, 1).setOccupied(true);
		board.getCell(1, 3).setRoom(true); 
		cell = board.getCell(1, 1);
		board.calcTargets(cell, 2);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		board.getCell(3, 1).setOccupied(false);
		board.getCell(1, 1).setRoom(false);
	}
	
	@Test
	public void testOccupied() {
		board.getCell(0, 3).setOccupied(true);
		board.getCell(1, 1).setOccupied(true);
		board.getCell(2, 2).setOccupied(true);
		board.getCell(2, 3).setOccupied(true);
		TestBoardCell cell = board.getCell(1, 3);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertFalse(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		board.getCell(0, 3).setOccupied(false);
		board.getCell(1, 1).setOccupied(false);
		board.getCell(2, 2).setOccupied(false);
		board.getCell(2, 3).setOccupied(false);
		
		board.getCell(0, 3).setOccupied(true);
		board.getCell(1, 2).setOccupied(true);
		board.getCell(2, 3).setOccupied(true);
		cell = board.getCell(1, 3);
		board.calcTargets(cell, 2);
		targets = board.getTargets();
		assertEquals(0, targets.size());
		
		board.getCell(0, 3).setOccupied(false);
		board.getCell(1, 2).setOccupied(false);
		board.getCell(2, 3).setOccupied(false);	
	}
	
	@Test
	public void testRooms() {
		board.getCell(0,1).setRoom(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		board.getCell(0,2).setRoom(true);
		cell = board.getCell(0, 3);
		board.calcTargets(cell, 1);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));


		
	}
}

