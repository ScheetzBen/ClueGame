package clueGame;

public class BadConfigFormatException extends Exception{
	public BadConfigFormatException() {
		super("An error exists in the config file used!");
	}
	
	public BadConfigFormatException(String error) {
		super("There is an error in the file: " + error);
	}
}
