package clueGame;

import java.awt.Color;
import java.awt.GridLayout;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class KnownCardsPanel extends JPanel{
	// Variables to hold the panel which contains each CardType
	private JPanel peopleCards = new JPanel();
	private JPanel roomCards = new JPanel();
	private JPanel weaponCards = new JPanel();
	
	// Constructor to initialize the panels needed
	public KnownCardsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		peopleCards = etchedBorderPanel("People");
		roomCards = etchedBorderPanel("Rooms");
		weaponCards = etchedBorderPanel("Weapons");
		
		this.add(peopleCards);
		this.add(roomCards);
		this.add(weaponCards);
	}
	
	// Helper class to remove code duplication for guess and guess result panels
	private JPanel etchedBorderPanel(String title) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.setBorder(new TitledBorder(new EtchedBorder(), title));
		
		JPanel currPanel = new JPanel();
		currPanel.setLayout(new GridLayout(2, 0));
		JLabel label = new JLabel("In Hand:");
		
		currPanel.add(label);
		
		panel.add(currPanel);
		
		currPanel = new JPanel();
		currPanel.setLayout(new GridLayout(2, 0));
		label = new JLabel("Seen:");
		
		currPanel.add(label);
		
		panel.add(currPanel);
		
		return panel;
	}
	
	// Function to add cards into the proper In Hand: section according to CardType
	public void addHandCards(ArrayList<Card> inHand) {
		JTextField currCard;
		
		for (Card card : inHand) {
			JPanel currPanel = (JPanel) getPanel(card).getComponent(0);
			
			currPanel.setLayout(new GridLayout(currPanel.getComponentCount() + 1, 0));
			
			currCard = new JTextField();
			currCard.setEditable(false);
			currCard.setText(card.getCardName());
			currCard.setBackground(Color.WHITE);
			
			currPanel.add(currCard);
		}
		
		this.updateUI();
	}
	
	// Function adds a Card to the Seen: section for the proper CardType and sets it's background color to the given Color
	// The Color is easy to match to a Player because we can use getColor() on a Player
	public void addSeenCards(Card seen, Color ownerColor) {
		JTextField cardName = new JTextField();
		cardName.setEditable(false);
		
		JPanel currPanel = (JPanel) getPanel(seen).getComponent(1);
		
		currPanel.setLayout(new GridLayout(currPanel.getComponentCount() + 1, 0));
		
		cardName.setText(seen.getCardName());
		cardName.setBackground(ownerColor);
		
		currPanel.add(cardName);
		
		this.updateUI();
	}
	
	// Helper class return the a panel based on the CardType of card
	private JPanel getPanel(Card card) {
		if (card.getType() == CardType.PERSON) return peopleCards;
		else if (card.getType() == CardType.ROOM) return roomCards;

		return weaponCards;
	}
	
	public static void main(String[] args) {
		KnownCardsPanel panel = new KnownCardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 700);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// Tests card adding functions
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card("Poison", CardType.WEAPON));
		cards.add(new Card("Gary", CardType.PERSON));
		cards.add(new Card("Dining Room", CardType.ROOM));
		
		panel.addHandCards(cards);
		
		panel.addSeenCards(new Card("Kyle", CardType.PERSON), Color.GREEN);
		panel.addSeenCards(new Card("Knife", CardType.WEAPON), Color.ORANGE);
		panel.addSeenCards(new Card("Kitchen", CardType.ROOM), Color.RED);
	}
}
