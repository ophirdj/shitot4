package GUI;

public abstract class StationPanel extends BasicPanel{
	public Main_Window window;
	
	public StationPanel(String name) {
		window = Global_Window.main_window;
	    window.add_button(new View(name),this);
	}
}
