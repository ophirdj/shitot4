package GUI;

public abstract class StationPanel extends BasicPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StationPanel(String name, Main_Window window) {
		super(window);
	    window.add_button(new View(name),this);
	}
}
