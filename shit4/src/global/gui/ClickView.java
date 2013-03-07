package global.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO add javadoc
public class ClickView implements ActionListener{
	private StationPanel button_panel;
	private Main_Window window;
	
	public ClickView(StationPanel panel, Main_Window given_window) {
		window = given_window;
		button_panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		window.show_station(button_panel);
	}
}
