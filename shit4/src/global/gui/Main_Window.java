package global.gui;


import global.dictionaries.Languages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.TitledBorder;



public class Main_Window extends JFrame {
	private static final long serialVersionUID = 56L;
	public static final Color BackGroundColor = new Color(58,95,205);
	
	private final StationPanel defualt_panel = new StationPanel(this, new JPanel());
	public Languages MAINFRAME_LANGUAGE = Languages.English;
	
	
	private JPanel main_panel;
	private JPanel buttons_panel;
	private JPanel current_panel;
	private StationPanel current_station_panel;
	
	private JScrollPane buttons_scroll;
	
	public Main_Window() {
		super("Main_Window");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    setMinimumSize(new Dimension(500, 350));
	    
	    main_panel = new JPanel();
	    main_panel.setLayout(new BorderLayout());
		buttons_panel = new JPanel(new FlowLayout());
		buttons_panel.setBackground(BackGroundColor);
		
		current_station_panel = defualt_panel;
		current_panel = defualt_panel;
		
		//main_panel.add(buttons_panel,BorderLayout.SOUTH);
		
		buttons_scroll = new JScrollPane(buttons_panel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		buttons_scroll.setLayout(new ScrollPaneLayout());
		main_panel.add(buttons_scroll, BorderLayout.SOUTH);
		
		this.add(main_panel);
		this.setVisible(true);
	}
	
	public void add_button(View view, StationPanel station_panel){
		JButton viewButton = new JButton(view.toString());
		viewButton.addActionListener(new ClickView(station_panel,this));
		buttons_panel.add(viewButton);
		station_panel.setButton(viewButton);
		station_panel.setPanel(station_panel);
		station_panel.setStationName(view.getName());
		
		
		buttons_scroll.setPreferredSize(new Dimension(buttons_panel.getPreferredSize().width, buttons_panel.getPreferredSize().height + 20));
	}
	
	public void show_window(){
		main_panel.setVisible(false);
		main_panel.remove(current_panel);
		current_panel = current_station_panel.getPanel();
		if(current_station_panel == defualt_panel){
			current_panel.setBackground(BackGroundColor);
		}
		else{
			current_panel.setBackground(Color.WHITE);
		}
		String name = current_station_panel.getStationName();
		if(name != null){
			current_panel.setBorder(new TitledBorder(name));
		}
		main_panel.add(current_panel,BorderLayout.CENTER);
		main_panel.setPreferredSize(getPreferredSize());
		main_panel.setVisible(true);
	}
	
	public void show_station(StationPanel station_panel){
		current_station_panel = station_panel;
		show_window();
	}
	
	public void show_if_current(StationPanel station_panel, JPanel new_panel){
		station_panel.setPanel(new_panel);
		if(station_panel == current_station_panel){
			show_window();
		}
	}
	
	public void hide_panel(StationPanel station_panel){
		if(station_panel != current_station_panel) return;
		current_station_panel = defualt_panel;
		show_window();
	}
	
	public void remove_panel(StationPanel station_panel){
		hide_panel(station_panel);
		JButton button_to_remove = station_panel.getButton();
		buttons_panel.remove(button_to_remove);
		if(buttons_panel.getComponentCount() == 0){
			this.dispose();
		}
		else{
			show_window();
		}
	}
}
