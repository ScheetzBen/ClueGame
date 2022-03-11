package clueGame;

public enum DoorDirection {
	// What direction a door faces, or if there is no door
	UP("^"), DOWN("v"), LEFT("<"), RIGHT(">"), NONE("None");
	
	private String value;

	DoorDirection(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
