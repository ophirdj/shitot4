package global.gui;

import java.awt.Color;

import global.dictionaries.IDictionary;
import global.dictionaries.Languages;
import global.dictionaries.Messages;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * General panel for any station
 * @author Ziv Ronen
 *
 */
public class StationPanel extends BasicPanel{
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
	 * Use this constructor for tests without graphic
	 */
	public StationPanel(){} 
	
	
	/**
	 * Create a new panel in given window (starts as some default panel)
	 * @param main_window: window the panel will show in
	 * @param defualtPanel: default panel to show
	 */
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
	
	/**
	 * Create a panel with a name in main_window
	 * @param name: panel name
	 * @param main_window: window the panel will show in
	 */
	public StationPanel(Messages name, Main_Window main_window) {
		super(main_window);
	    dictionary = window.MAINFRAME_LANGUAGE.getDictionary();
	    window.add_button(new View(name,translate(name)),this);
	}
	
	/**
	 * Create a panel with a name (name will be <name><id>) in main_window
	 * @param name: panel name
	 * @param id: panel number
	 * @param main_window: window the panel will show in
	 */
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

	/**
	 * Set panel's button
	 * @param button
	 */
	public void setButton(JButton button){
		this.button = button;
	}
	
	/**
	 * Set panel's name
	 * @param name
	 */
	public void setStationName(Messages name){
		this.name = name;
	}

	/**
	 * Get the actual panel
	 * @return actual panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Set the actual panel
	 * @param panel: actual panel
	 */
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	/**
	 * Get name
	 * @return panel name
	 */
	public String getStationName() {
		if(id != null)
			return translate(name)+id;
		return translate(name);
	}

	/**
	 * Get panel's button
	 * @return panel's button
	 */
	public JButton getButton() {
		return button;
	}
	
	public void setLanguage(Languages language) {
		dictionary = language.getDictionary();
	}
}
