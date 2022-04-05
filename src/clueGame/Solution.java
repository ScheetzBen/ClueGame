package clueGame;

public class Solution {
	// Variables to hold the room, person, and weapon Cards for the solution
	private Card room, person, weapon;

	// Constructor sets the room, person, and weapon Cards
	public Solution(Card person, Card weapon, Card room) {
		super();
		
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}

	// Getters for Solution
	public Card getRoom() {
		return room;
	}

	public Card getPerson() {
		return person;
	}

	public Card getWeapon() {
		return weapon;
	}

	// Setters for Solution
	public void setRoom(Card room) {
		this.room = room;
	}

	public void setPerson(Card person) {
		this.person = person;
	}

	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	
}
