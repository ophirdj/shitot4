package practiceStation.guides;

import global.dictionaries.Languages;
import global.dictionaries.Messages;
import global.gui.Main_Window;
import global.gui.StationPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;




public class ImagePanel  extends JPanel implements IImagePanel{
	static final long serialVersionUID = 1L;
	final private Color INACTIVE_COLOR = Color.GRAY;
	final private Color ACTIVE_COLOR = UIManager.getColor("Button.background");
	
	private File fileToShow;
	private int fileShownIndex;
	private IListImages images_list;
	
	private JButton nextButton = new JButton(">");
	private JButton prevButton = new JButton("<");
	private JButton exitButton = new JButton();
	private Map<Languages, IListImages> guideMap;
	
	private StationPanel callerStation;
	private Canvas canvas;
	private Main_Window window;
	
	boolean timeout = false;
	
	private void showImage(File from){
		try{
			Image imageToShow = ImageIO.read(fileToShow);
			canvas.showImage(imageToShow);
			if(hasNext()){
				nextButton.setBackground(ACTIVE_COLOR);
			}
			else{
				nextButton.setBackground(INACTIVE_COLOR);
			}
			
			if(hasPrev()){
				prevButton.setBackground(ACTIVE_COLOR);
			}
			else{
				prevButton.setBackground(INACTIVE_COLOR);
			}
			synchronized (this) {
				if(!timeout) window.show_if_current(callerStation, this);
			}
			
		}catch(IOException e){}
		
	}
	
	public ImagePanel(Map<Languages, IListImages> guideMap,StationPanel callerStation, Main_Window main_window) {
		super(new BorderLayout());
		this.callerStation = callerStation;
		this.guideMap = guideMap;
		this.images_list = guideMap.get(main_window.MAINFRAME_LANGUAGE);
		nextButton.addActionListener(new DirectionClick(image_action.next));
		prevButton.addActionListener(new DirectionClick(image_action.prev));
		exitButton.addActionListener(new DirectionClick(image_action.exit));
		this.add(nextButton, BorderLayout.EAST);
		this.add(prevButton, BorderLayout.WEST);
		this.add(exitButton, BorderLayout.SOUTH);
		this.canvas = new Canvas();
		this.add(canvas, BorderLayout.CENTER);
		fileShownIndex = 0;
		window = main_window;
	}
	
	public boolean hasNext(){
		if(fileShownIndex >= images_list.size()-1){
			return false;
		}
		return true;
	}
	
	public boolean hasPrev(){
		if(fileShownIndex <= 0){
			return false;
		}
		return true;
	}
	
	public void showNextImage() {
		if(fileShownIndex >= images_list.size()-1){
			return;
		}
		fileToShow = images_list.getFile(++fileShownIndex);
		showImage(fileToShow);
	}
	
	public void showPrevImage() {
		if(fileShownIndex <= 0) return;
		fileToShow = images_list.getFile(--fileShownIndex);
		showImage(fileToShow);
	}
	
	public void showGuide(Languages language) {
		images_list = guideMap.get(language);
		fileShownIndex = 0;
		fileToShow = images_list.getFile(fileShownIndex);
		showImage(fileToShow);
		exitButton.setText(callerStation.translate(Messages.Exit));
		window.show_if_current(callerStation, this);
		synchronized (this) {
			try{
				if(!timeout) this.wait();
			}catch(InterruptedException e){
				
			}
		}
		window.show_if_current(callerStation, callerStation);
	}
	
	public void retire(){
		synchronized (this) {
			timeout = true;
			this.notify();
		}
	}
	 
	private void continuePractice(){
		synchronized (this) {
			this.notify();
		}
	}
	
	
	public enum image_action{
		next,prev,exit
	}
	
	class DirectionClick implements ActionListener{
		
		image_action toWhere;
		
		public DirectionClick(image_action d) {
			toWhere = d;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch (toWhere) {
			case next:
				showNextImage();
				break;
			case prev:
				showPrevImage();
				break;
			case exit:
				continuePractice();
				break;
			default:
				break;
			}
		}
	}
}
