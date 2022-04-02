package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public enum CardType {
		ROOM, PERSON, WEAPON;
	}
	
	public Card(String cardName, CardType type) {
		super();
		
		this.cardName = cardName;
		this.type = type;
	}
	
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
