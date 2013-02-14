package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ClickView implements ActionListener{
	private JPanel button_panel;
	private Main_Window window;
	
	public ClickView(JPanel panel, Main_Window given_window) {
		window = given_window;
		button_panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		window.show_station(button_panel);
	}
}
