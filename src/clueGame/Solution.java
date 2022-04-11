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
	
	// Overridden function to test the equality of the Cards from one solution to another
	@Override
	public boolean equals(Object target) {
		if (target == this) return true;
		if (!(target instanceof Solution)) return false;
		
		Solution c = (Solution) target;
		
		if (this.room.equals(c.getRoom()) && this.person.equals(c.getPerson()) && this.weapon.equals(c.getWeapon())) return true;
		else return false;
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
