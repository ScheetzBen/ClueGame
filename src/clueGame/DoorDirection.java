package clueGame;

public enum DoorDirection {
	// What direction a door faces, or if there is no door
	UP("^"), DOWN("v"), LEFT("<"), RIGHT(">"), NONE("None");

	private String value;

	// Constructor for DoorDirection sets the given string to the value
	DoorDirection(String value) {
		this.value = value;
	}

	// toString so that DoorDirection can be converted to a proper string
	@Override
	public String toString() {
		return value;
	}
}
