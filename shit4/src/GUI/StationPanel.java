package GUI;

public abstract class StationPanel extends BasicPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Main_Window window;
	
	public StationPanel(String name) {
		window = Global_Window.main_window;
	    window.add_button(new View(name),this);
	}
}
