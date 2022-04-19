package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Room.TileType;

import java.util.ArrayList;

public class Board extends JPanel{
	// Variables for Board
	
	// Initialize a grid array to hold all the cell in the board
	private BoardCell[][] grid;
	
	// variables to hold the number of rows and columns
	private int numRows, numColumns;
	
	// Holds the filenames for the layout and setup config files
	private String layoutConfigFile, setupConfigFile;
	
	// Array to hold the Players
	private ArrayList<Player> players;
	
	// Map to hold all the different characters and their related Room objects
	private Map<Character, Room> roomMap;
	private ArrayList<Room> rooms;
	
	// Sets to hold cards of different types for dealing
	private ArrayList<Card> cards;
	
	// A Solution object which will hold the solution
	private Solution solution;

	// static variable of Board so that there is only ever one Board object created when the program is running
	private static Board theInstance = new Board(); 

	// Sets to hold information for calculating targets
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	
	private int playerNum = 0;
	
	private Random rnd = new Random();
	// Constructor for Board
	private Board() {
		super();
	}

	// Returns the one Board instance
	public static Board getInstance() {
		return theInstance;
	}

	// Calls loadSetupConfig and loadLayoutConfig and catches their exceptions
	public void initialize() {
		roomMap  = new HashMap<Character, Room>();
		rooms = new ArrayList<Room>();
		players = new ArrayList<Player>();
		cards = new ArrayList<Card>();
		
		try {
			this.loadSetupConfig();
			this.loadLayoutConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}

	// Properly loads the setup file
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(setupConfigFile);

		Scanner in = new Scanner(reader);

		while (in.hasNext()) {
			String[] array = in.nextLine().split(", ");

			// if else statements handle all different Cards possible in ClueSetup.txt
			if (array[0].equals("Room")) {
				cards.add(new Card(array[1], Card.CardType.ROOM));

				rooms.add(new Room(array[1], TileType.ROOM, cards.get(cards.size() - 1)));
				roomMap.put(array[2].charAt(0), rooms.get(rooms.size() - 1));
					
			} else if (array[0].equals("Space")) {
				roomMap.put(array[2].charAt(0), new Room(array[1], Room.TileType.SPACE, cards.get(cards.size() - 1)));

			} else if (array[0].equals("Weapon")) {
				cards.add(new Card(array[1], Card.CardType.WEAPON));

			} else if (array[0].equals("Person")) {
				Color color =  new Color(Integer.parseInt(array[2]), Integer.parseInt(array[3]), Integer.parseInt(array[4]));
				
				if (players.isEmpty()) players.add(new HumanPlayer(array[1], color));
				else players.add(new ComputerPlayer(array[1], color));

				cards.add(new Card(array[1], Card.CardType.PERSON));
				
			} else if (array[0].charAt(0) == '/') {
			continue;
			
			} else {
				throw new BadConfigFormatException(setupConfigFile);
			}
		}
		in.close();
	}

	// Properly loads the layout file and sets all BoardCell variables as well as Room centers and labels
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(layoutConfigFile);

		Scanner in = new Scanner(reader);

		ArrayList<String[]> rows = new ArrayList<String[]>();

		while (in.hasNext()) {
			String line = in.nextLine();

			if (!line.isEmpty()) {
				String[] currCol = line.split(",");
				rows.add(currCol);
			}
		}

		numRows = rows.size();
		numColumns = rows.get(0).length;
		
		// Checking that all rows in the document have the same number of columns
		// for loop checks the size of all elements in rows
		for (String[] i : rows) {
			if (i.length != numColumns) throw new BadConfigFormatException(layoutConfigFile);
		}

		grid = new BoardCell[numRows][numColumns];

		// Nested for loop just run for all cells in grid and checks they are a valid Space and sets their DoorDirection;
		for (int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[row].length; col++) {
				if (!roomMap.containsKey(rows.get(row)[col].charAt(0))) throw new BadConfigFormatException(layoutConfigFile);

				grid[row][col] = new BoardCell(row, col, rows.get(row)[col].charAt(0));
				
				if (row == 0 && col == 7)
					players.get(0).setPosition(row, col);
				else if (row == 2 && col == 18)
					players.get(1).setPosition(row, col);
				else if (row == 12 && col == 3) 
					players.get(2).setPosition(row, col);
				else if (row == 15 && col == 15)
					players.get(3).setPosition(row, col);
				else if (row == 7 && col == 16)
					players.get(4).setPosition(row, col);
				else if (row == 7 && col == 7)
					players.get(5).setPosition(row, col);

				if (rows.get(row)[col].length() > 1) {
					char specialType = rows.get(row)[col].charAt(1);
					BoardCell currCell = this.getCell(row, col);

					// Switch handles special cell types
					switch(specialType) {
					case '*':
						getCell(row, col).setCenter(true);
						getRoom(currCell.getInitial()).setCenterCell(currCell);
						break;
					case '#':
						getCell(row, col).setLabel(true);
						getRoom(currCell.getInitial()).setLabelCell(currCell);
						break;
					case '^':
						getCell(row, col).setDoorDirection(DoorDirection.UP);
						break;
					case 'v':
						getCell(row, col).setDoorDirection(DoorDirection.DOWN);
						break;
					case '<':
						getCell(row, col).setDoorDirection(DoorDirection.LEFT);
						break;
					case '>':
						getCell(row, col).setDoorDirection(DoorDirection.RIGHT);
						break;
					default:
						getCell(row, col).setSecretPassage(specialType);	
					}
				}
			}
		}

		// Initializes the adjacency list for all the cells in the grid
		for (BoardCell[] row : grid) {
			for(BoardCell cell : row) {
				if ((getRoom(cell).getType() == Room.TileType.SPACE && cell.getInitial() != 'X') || cell.getSecretPassage() != ' ')
					cell.setAdjacencies(this);
			}
		}
		in.close();
	}
	
	// Deals the cards evenly to all players
	public void deal() {
		ArrayList<Card> deck = new ArrayList<Card>(cards);

		solution = new Solution(getRandomCard(deck, Card.CardType.PERSON), getRandomCard(deck, Card.CardType.WEAPON), getRandomCard(deck, Card.CardType.ROOM));
		
		// while loop deal the rest of the cards into player hands
		while (!deck.isEmpty()) {			
			for (var player : players) {
				int currCard = rnd.nextInt(deck.size());
				
				player.addCard(deck.get(currCard));
				
				deck.remove(currCard);
				
				if (deck.isEmpty()) break;
			}
		}
	}
	
	// Helper class to remove code duplication
	// Function returns a random Card of specified card type from a deck
	private Card getRandomCard(ArrayList<Card> deck, Card.CardType type) {
		int currCard = rnd.nextInt(deck.size());
		
		while (deck.get(currCard).getType() != type) currCard = rnd.nextInt(deck.size());
		Card temp = deck.get(currCard);
		deck.remove(currCard);
		
		return temp;
	}
	
	// Calculates all possible targets for the start cell given the pathlength
	public void calcTargets(BoardCell startCell, int pathlength) {
		visited.add(startCell);

		// Runs for all adjacent cells
		for (BoardCell cell : startCell.getAdjList()) {
			boolean test = true;
			
			// Testing to see if the adjacent cell  has already been visited
			for (BoardCell inVisited : visited) {
				if (inVisited == cell) { 
					test = false;
					break;
				}
			}
			
			// As long as the cell has not been visited and is not occupied (Unless it is a room center) then the cell is added to targets
			if (test && (!(cell.isOccupied()) || cell.isRoomCenter())) {
				visited.add(cell);
				if (cell.isRoomCenter()) {
					targets.add(cell);
					visited.remove(cell);
					continue;
				}

				// Recursive call until the pathlength is 1
				if (pathlength == 1) targets.add(cell);
				else calcTargets(cell, pathlength - 1);
				visited.remove(cell);
			}
		}
	}
	
	// Checks whether any Player can dispute a suggestion
	public Card handleSuggestion(Solution suggestion, Player accusor) {
		for (Player player : players) {
			if (player == accusor) continue;
			
			Card tempCard = player.disproveSuggestion(suggestion);
			
			if (tempCard != null) return tempCard;
		}
		
		return null;
	}

	// Method to see whether an accusation is correct
	public boolean checkAccusation(Solution accusation) {
		if (accusation.equals(solution)) return true;
		return false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int cellHeight = (getHeight()) / (numRows + 2);
		int cellWidth = (getWidth()) / (numColumns + 2);
		
		ArrayList<BoardCell> doors = new ArrayList<BoardCell>();
		
		for (BoardCell[] row : grid) {
			for (BoardCell cell : row) {
				if (cell.isDoorway()) {
					doors.add(cell);
					continue;
				}
				
				cell.draw(cellHeight, cellWidth, g, this);
			}
		}
		
		for (BoardCell cell : doors) {
			cell.draw(cellHeight, cellWidth, g, this);
		}
		
		for (Player player: players) {
			player.draw(cellHeight, cellWidth, g);
		}
		
		for (var room : rooms) {
			room.draw(cellHeight, cellWidth, 1, g);
		}
	}
	
	public void handleTurn(GameControlPanel gcPanel) {
		Player currPlayer = players.get(playerNum);
		
		if (currPlayer instanceof HumanPlayer) {
			if (!targets.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please finish your turn");
				return;
			} else {
				int currRoll = roll();

				gcPanel.setTurn(currPlayer, currRoll);

				calcTargets(getCell(currPlayer.getRow(), currPlayer.getColumn()), currRoll);
				
				System.out.println(targets.size());

				for (BoardCell target : targets) {
					target.flag();
				}

				repaint();
			}
		}
	}
	
	private int roll() {
		return rnd.nextInt(6) + 1;
	}

	// Setters for board variables
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
		this.layoutConfigFile = new String("data/" + layoutConfigFile);
		this.setupConfigFile = new String("data/" + setupConfigFile);
	}
	
	public void setPlayers(Player[] players) {
		this.players.clear();
		
		for (Player player : players)
			this.players.add(player);
	}

	// Getters for board variables
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public Room getRoom(char room) {
		return roomMap.get(room);
	}

	public Room getRoom(int row, int col) {
		return roomMap.get(getCell(row, col).getInitial());
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Solution getSolution() {
		return solution;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	// Return a cell in the grid
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return this.getCell(row, col).getAdjList();
	}

	// Resets the targets and visited sets and returns the targets
	public Set<BoardCell> getTargets() {
		Set<BoardCell> temp = targets;
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		return temp;
	}
	
	private class BoardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}
