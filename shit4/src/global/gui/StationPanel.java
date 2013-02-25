package global.gui;

import java.awt.Color;

import global.dictionaries.IDictionary;
import global.dictionaries.Languages;
import global.dictionaries.Messages;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StationPanel extends BasicPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static final int GLOBAL_ROWS_NUM = 1;
	protected void addGlobalRows(JPanel stationPanel, Color bgColor){
		JPanel languagePanel = Languages.getLanguagesPanel(this, this);
		languagePanel.setBackground(bgColor);
		stationPanel.add(languagePanel);
	}
	
	
	private JButton button;
	private JPanel panel;
	private Messages name;
	protected IDictionary dictionary;
	private Integer id;
	
	/**
	 * use this constructor for tests without graphic
	 */
	public StationPanel(){} 
	
	public StationPanel(Main_Window main_window, JPanel defualtPanel){
		super(main_window);
		setPanel(defualtPanel);
		dictionary = new IDictionary() {
			
			@Override
			public String translate(Messages message) {
				return null;
			}
		};
	}
	
	public StationPanel(Messages name, Main_Window main_window) {
		super(main_window);
	    dictionary = window.MAINFRAME_LANGUAGE.getDictionary();
	    window.add_button(new View(name,translate(name)),this);
	}
	
	public StationPanel(Messages name, Integer id, Main_Window main_window) {
		super(main_window);
	    dictionary = window.MAINFRAME_LANGUAGE.getDictionary();
	    this.id = id;
	    window.add_button(new View(name,translate(name)+id),this);
	}
	
	@Override
	public void closeWindow(){
		window.remove_panel(this);
	}
	
	@Override
	public String translate(Messages message) {
		return dictionary.translate(message);
	}

	public void setButton(JButton button){
		this.button = button;
	}
	
	public void setStationName(Messages name){
		this.name = name;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public String getStationName() {
		if(id != null)
			return translate(name)+id;
		return translate(name);
	}

	public JButton getButton() {
		return button;
	}
	
	@Override
	public void setLanguage(Languages language) {
		dictionary = language.getDictionary();
	}
}
