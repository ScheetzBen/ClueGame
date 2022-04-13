package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame{
	GameControlPanel gcPanel;
	KnownCardsPanel kcPanel;
	Board bPanel;
	
	public ClueGame() {
		setSize(800, 900);
		setTitle("Clue Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gcPanel = new GameControlPanel();
		add(gcPanel, BorderLayout.SOUTH);
		
		kcPanel = new KnownCardsPanel();
		add(kcPanel, BorderLayout.EAST);
		
		bPanel = Board.getInstance();
		bPanel.setConfigFiles("ClueLayout2.csv", "ClueSetup.txt");
		bPanel.initialize();
		
		add(bPanel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}
}
