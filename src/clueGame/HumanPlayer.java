package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player{

	// Constructor to set the name and color of a HumanPlayer
	public HumanPlayer(String name, Color color) {
		super(name, color);
	}
	
	public HumanPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column);
	}
	
	public void suggestionSubmitted(Board board, Solution suggestion) {
		board.handleSuggestion(suggestion, this);
	}
	
	@Override
	public void makeSuggestion(Board board) {
		SuggestionDialog suggestionDialog = new SuggestionDialog(board, this, true);
		suggestionDialog.setVisible(true);
	}
}
