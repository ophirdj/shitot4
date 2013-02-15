package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import dictionaries.IDictionary;
import factories.IDictionaryFactory;
import dictionaries.IDictionary.Messages;


public class Main_Window extends JFrame {
	private static final long serialVersionUID = 56L;
	public static final Color BackGroundColor = new Color(58,95,205);
	
	private static final JPanel defualt_panel = new JPanel(new FlowLayout());
	
	private JPanel main_panel;
	private JPanel buttons_panel;
	private JPanel current_panel;
	private JPanel current_station_panel;
	
	private Hashtable<JPanel, JButton> station_button_map;
	private Hashtable<JPanel, JPanel> station_show_map;
	private Hashtable<JPanel, String> station_name_map;
	
	private IDictionary dictionary;
	
	public Main_Window(IDictionaryFactory translatorFactory) {
		super();
		this.dictionary = translatorFactory.createInstance();
		super.setTitle(dictionary.translate(Messages.Main_Window));
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setMinimumSize(new Dimension(500,300));
		
	    
	    main_panel = new JPanel();
	    main_panel.setLayout(new BorderLayout());
		buttons_panel = new JPanel(new FlowLayout());
		buttons_panel.setBackground(BackGroundColor);
		
		current_station_panel = defualt_panel;
		current_panel = defualt_panel;
		
		main_panel.add(buttons_panel,BorderLayout.SOUTH);
		
		station_button_map = new Hashtable<JPanel, JButton>();
		station_show_map =  new Hashtable<JPanel, JPanel>();
		station_name_map =  new Hashtable<JPanel, String>();
		station_show_map.put(defualt_panel, defualt_panel);
		
		this.add(main_panel);
		this.setVisible(true);
	}
	
	/**
	 * Translate the given message to native language.
	 * @param message
	 * @return
	 */
	public String translate(Messages message){
		return dictionary.translate(message);
	}
	
	public void add_button(View view, JPanel station_panel){
		JButton viewButton = new JButton(view.getName());
		viewButton.addActionListener(new ClickView(station_panel,this));
		buttons_panel.add(viewButton);
		
		station_button_map.put(station_panel, viewButton);
		station_show_map.put(station_panel, station_panel);
		station_name_map.put(station_panel, view.getName());
	}
	
	public void show_window(){
		main_panel.setVisible(false);
		main_panel.remove(current_panel);
		current_panel = station_show_map.get(current_station_panel);
		if(current_station_panel == defualt_panel){
			current_panel.setBackground(BackGroundColor);
		}
		else{
			current_panel.setBackground(Color.WHITE);
		}
		String name = station_name_map.get(current_station_panel);
		if(name != null) current_panel.setBorder(new TitledBorder(name));
		main_panel.add(current_panel,BorderLayout.CENTER);
		main_panel.setPreferredSize(getPreferredSize());
		main_panel.setVisible(true);
	}
	
	public void show_station(JPanel station_panel){
		current_station_panel = station_panel;
		show_window();
	}
	
	public void show_if_current(JPanel station_panel, JPanel new_panel){
		station_show_map.remove(station_panel);
		station_show_map.put(station_panel, new_panel);
		if(station_panel == current_station_panel){
			show_window();
		}
	}
	
	public void hide_panel(JPanel station_panel){
		if(station_panel != current_station_panel) return;
		current_station_panel = defualt_panel;
		show_window();
	}
	
	public void remove_panel(JPanel station_panel){
		hide_panel(station_panel);
		if(!station_button_map.containsKey(station_panel)){
			return;
		}
		JButton button_to_remove = station_button_map.get(station_panel);
		buttons_panel.remove(button_to_remove);
		station_button_map.remove(station_panel);
		station_show_map.remove(station_panel);
		station_name_map.remove(station_panel);
		if(station_button_map.size() == 0){
			this.dispose();
		}
		else{
			show_window();
		}
	}
}
