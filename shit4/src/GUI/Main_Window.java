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

public class Main_Window extends JFrame {
	private static final long serialVersionUID = 56L;
	public static final Color BackGroundColor = new Color(58,95,205);
	
	private static final JPanel defualt_panel = new JPanel(new FlowLayout());
	private JPanel main_panel;
	private JPanel buttons_panel;
	private JPanel current_panel;
	private static Integer counter = 1;
	private Hashtable<JPanel, JButton> panel_button_map;
	
	public Main_Window() {
		super("Main_Window");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setMinimumSize(new Dimension(500,300));
		main_panel = new JPanel(new BorderLayout());
		buttons_panel = new JPanel(new FlowLayout());
		current_panel = defualt_panel;
		panel_button_map = new Hashtable<JPanel, JButton>();
		this.add(main_panel);
		this.setVisible(true);
	}
	
	public void add_button(View view, JPanel new_panel){
		JButton viewButton = new JButton(view.getName());
		viewButton.addActionListener(new ClickView(new_panel,this));
		buttons_panel.add(viewButton);
		panel_button_map.put(new_panel, viewButton);
	}
	
	public void show_window(){
		main_panel.setVisible(false);
		main_panel.removeAll();
		if(current_panel == defualt_panel){
			current_panel.setBackground(BackGroundColor);
		}
		else{
			current_panel.setBackground(Color.WHITE);
		}
		
		buttons_panel.setBackground(BackGroundColor);
		main_panel.add(buttons_panel,BorderLayout.SOUTH);
		main_panel.add(current_panel,BorderLayout.CENTER);
		JButton current_Button = panel_button_map.get(current_panel);
		if(current_Button != null) current_panel.setBorder(new TitledBorder(current_Button.getText()));
		
		main_panel.setVisible(true);
	}
	
	public void show_panel(JPanel new_panel){
		main_panel.remove(current_panel);
		main_panel.add(new_panel,BorderLayout.CENTER);
		current_panel = new_panel;
		show_window();
	}
	
	public void show_if_current(JPanel new_panel){
		if(new_panel == current_panel){
			show_panel(new_panel);
		}
	}
	
	public void switch_panels(JPanel old_panel,JPanel new_panel){
		hide_panel(old_panel);
		if(old_panel == null){
			synchronized (counter) {
				add_button(new View("defualt "+counter),new_panel);
				counter++;
			}
			show_window();
			return;
		}
		if(new_panel == null){
			remove_panel(old_panel);
			show_window();
			return;
		}
		JButton button_to_change = panel_button_map.get(old_panel);
		panel_button_map.remove(button_to_change);
		panel_button_map.put(new_panel,button_to_change);
		button_to_change.removeActionListener(button_to_change.getActionListeners()[0]);
		button_to_change.addActionListener(new ClickView(new_panel,this));
		show_panel(new_panel);
	}
	
	public void hide_panel(JPanel panel){
		if(panel != current_panel || panel == null) return;
		main_panel.remove(current_panel);
		main_panel.add(defualt_panel);
		current_panel = panel;
		show_window();
	}
	
	public void remove_panel(JPanel new_panel){
		hide_panel(new_panel);
		if(!panel_button_map.containsKey(new_panel)){
			return;
		}
		JButton button_to_remove = panel_button_map.get(new_panel);
		buttons_panel.remove(button_to_remove);
		panel_button_map.remove(new_panel);
		show_window();
		if(panel_button_map.size() == 0){
			this.dispose();
		}
	}
}
