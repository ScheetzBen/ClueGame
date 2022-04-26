package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	// Variables to hold the top and bottom panel for the control GUI
	private JPanel topPanel = new JPanel(new GridLayout(0, 4));
	private JPanel bottomPanel = new JPanel(new GridLayout(0, 2));
	private JButton accusationButton = new JButton("Make Accusation");
	private JButton nextButton = new JButton("Next");
	
	private Board board;
	
	// Constructor initializes all Panels
	public GameControlPanel(Board board) {
		this.board = board;
		
		setLayout(new GridLayout(2, 0));
		
		JPanel turnInfo = new JPanel(new GridLayout(2, 0));
		turnInfo.add(new JLabel("Whose Turn?"));
		turnInfo.add(new JTextField());
		
		JPanel rollInfo = new JPanel();
		rollInfo.add(new JLabel("Roll:"));
		rollInfo.add(new JTextField(5));
		
		topPanel.add(turnInfo);
		topPanel.add(rollInfo);
		topPanel.add(accusationButton);
		topPanel.add(nextButton);
		nextButton.addActionListener(new NextListener());
		
		bottomPanel.add(etchedBorderPanel("Guess"));
		bottomPanel.add(etchedBorderPanel("Guess Result"));
		
		this.add(topPanel);
		this.add(bottomPanel);
	}
	
	// Helper class to remove code duplication for guess and guess result panels
	private JPanel etchedBorderPanel(String title) {
		JPanel panel = new JPanel(new GridLayout(1, 0));
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		
		panel.add(new JTextField());
		
		return panel;
	}
	
	// sets the current Player's name and their Roll in the GUI
	public void setTurn(Player currPlayer, Integer roll) {
		JPanel currPanel = (JPanel) topPanel.getComponent(0);
		JTextField currText = (JTextField) currPanel.getComponent(1);
		
		currText.setText(currPlayer.getName());
		currText.setBackground(currPlayer.getColor());
		
		currPanel = (JPanel) topPanel.getComponent(1);
		currText = (JTextField) currPanel.getComponent(1);
		currText.setText(roll.toString());
		
		this.updateUI();
	}
	
	// sets the current Player's guess
	public void setGuess(String text) {
		JPanel currPanel = (JPanel) bottomPanel.getComponent(0);
		JTextField currText = (JTextField) currPanel.getComponent(0);
		
		currText.setText(text);
		
		this.updateUI();
	}
	
	// sets the guess result for the current Player's guess
	public void setGuessResult(String text, Color playerColor) {
		JPanel currPanel = (JPanel) bottomPanel.getComponent(1);
		JTextField currText = (JTextField) currPanel.getComponent(0);
		
		currText.setBackground(playerColor);
		
		currText.setText(text);
		
		this.updateUI();
	}
	
	// Called when next is pressed and calls the proper function inside of Board to handle it
	public void nextPressed() {
		board.handleTurn();
	}
	
	// NextListener class calls the proper functions inside of GameControlPanel when the Next button is pressed
	private class NextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			nextPressed();
		}
	}
	

	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel(Board.getInstance());  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.ORANGE, 0, 0), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult( "So you have nothing?", Color.WHITE);
	}
}