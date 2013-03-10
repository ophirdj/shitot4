package global.gui;

import global.dictionaries.Messages;

/**
 * Used for the buttons in the main window
 */
public class View {
	Messages name;
	private String original;
	
	public View(Messages given_name, String original) {
		name = given_name;
		this.original = original;
	}
	
	public Messages getName(){
		return name;
	}
	
	@Override
	public String toString() {
		return original;
	}
}
