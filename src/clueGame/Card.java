package clueGame;

public class Card {
	// Variables to hold the name and type of a Card
	private String cardName;
	private CardType type;
	
	// Enumerator which gives the Card type
	public enum CardType {
		ROOM, PERSON, WEAPON;
	}
	
	// Constructor to set the name and type of a Card
	public Card(String cardName, CardType type) {
		super();
		
		this.cardName = cardName;
		this.type = type;
	}
	
	// Function to check if the name and type of 2 Cards match
	@Override
	public boolean equals(Object target) {
		if (target == this) return true;
		if (!(target instanceof Card)) return false;
		
		Card c = (Card) target;
		
		if (this.cardName.equals(c.cardName) && this.type.equals(c.type)) return true;
		else return false;
	}

	// Getters for Card
	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
}
