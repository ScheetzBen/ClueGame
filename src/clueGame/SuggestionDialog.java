package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import clueGame.Card.CardType;

public class SuggestionDialog extends JDialog{
	private JComboBox<String> person, weapon;
	
	public SuggestionDialog(Board board, HumanPlayer player, boolean isSuggestion) {
		setTitle("Make Suggestion");
		setLayout(new GridLayout(4, 2));
		setSize(200, 200);
		
		JLabel personLabel = new JLabel("Person:");
		JLabel weaponLabel = new JLabel("Weapon:");
		JLabel roomLabel = new JLabel("Current Room:");
		
		person = new JComboBox<String>();
		weapon = new JComboBox<String>();
		
		for (Card card : board.getCards()) {
			if (card.getType() == Card.CardType.PERSON) person.addItem(card.getCardName());
			else if (card.getType() == Card.CardType.WEAPON) weapon.addItem(card.getCardName());
		}
		
		String roomName = new String(board.getRoom(player.getRow(), player.getColumn()).getName());
		
		JTextField roomText = new JTextField(roomName);
		roomText.setEditable(false);
		
		JButton submit = new JButton("Submit");
		
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
				Card personCard = new Card(person.getSelectedItem().toString(), CardType.PERSON);
				Card weaponCard = new Card(weapon.getSelectedItem().toString(), CardType.WEAPON);
				Card roomCard = new Card(roomName, CardType.ROOM);
				
				if (isSuggestion)
					player.suggestionSubmitted(board, new Solution(personCard, weaponCard, roomCard));
				else
					board.handleAccusation(new Solution(personCard, weaponCard, roomCard));
			}
		});
		
		JButton cancel = new JButton("Cancel");
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		add(personLabel);
		add(person);
		add(weaponLabel);
		add(weapon);
		add(roomLabel);
		add(roomText);
		add(submit);
		add(cancel);
	}
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout2.csv", "ClueSetup.txt");
		board.initialize();
		
		Player player = board.getPlayers().get(0);
		
		player.setPosition(4, 12, board);
		
		SuggestionDialog suggestionDialog = new SuggestionDialog(board, (HumanPlayer) player, true);
		
		suggestionDialog.setVisible(true);
	}
}
