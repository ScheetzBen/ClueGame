package clueGame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame{
	GameControlPanel gcPanel;
	KnownCardsPanel kcPanel;
	Board bPanel;
	
	public ClueGame() {
		setSize(800, 900);
		setTitle("Clue Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		kcPanel = new KnownCardsPanel();
		add(kcPanel, BorderLayout.EAST);
		
		bPanel = Board.getInstance();
		bPanel.setConfigFiles("ClueLayout2.csv", "ClueSetup.txt");
		bPanel.setKCPanel(kcPanel);
		bPanel.initialize();
		bPanel.deal();
		
		add(bPanel, BorderLayout.CENTER);
		
		gcPanel = new GameControlPanel(bPanel);
		add(gcPanel, BorderLayout.SOUTH);
		
		bPanel.setGCPanel(gcPanel);
		bPanel.setClueGame(this);
	}
	
	public void displayIntro() {
		JOptionPane.showMessageDialog(null, "You are " + bPanel.getPlayers().get(0).getName() + ". Can you find the solution before the Computer players?");
		bPanel.handleTurn();
	}
	
	public GameControlPanel getGCPanel() {
		return gcPanel;
	}
	
	public KnownCardsPanel getKCPanel() {
		return kcPanel;
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		game.displayIntro();
	}
}
