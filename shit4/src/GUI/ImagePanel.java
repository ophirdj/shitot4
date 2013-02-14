package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class ImagePanel  extends JPanel implements IImagePanel{
	static final long serialVersionUID = 1L;
	final private Color INACTIVE_COLOR = Color.GRAY;
	final private Color ACTIVE_COLOR = UIManager.getColor("Button.background");
	
	File fileToShow;
	int fileShownIndex;
	IListImages images_list;
	
	JButton nextButton = new JButton(">");
	JButton prevButton = new JButton("<");
	JButton exitButton = new JButton("exit");
	
	JPanel callerStation;
	Canvas canvas;
	Main_Window window;
	
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
	
	public ImagePanel(IListImages images_list, JPanel callerStation) {
		super(new BorderLayout());
		
		this.callerStation = callerStation;
		this.images_list = images_list;
		nextButton.addActionListener(new DirectionClick(image_action.next));
		prevButton.addActionListener(new DirectionClick(image_action.prev));
		exitButton.addActionListener(new DirectionClick(image_action.exit));
		this.add(nextButton, BorderLayout.EAST);
		this.add(prevButton, BorderLayout.WEST);
		this.add(exitButton, BorderLayout.SOUTH);
		this.canvas = new Canvas();
		this.add(canvas, BorderLayout.CENTER);
		
		fileToShow = images_list.getFile(0);
		fileShownIndex = 0;
		window = Global_Window.main_window;
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
	
	public void showFirstImage() {
		fileShownIndex = 0;
		fileToShow = images_list.getFile(fileShownIndex);
		showImage(fileToShow);
		window.show_if_current(callerStation, this);
		synchronized (this) {
			try{
				if(!timeout) this.wait();
			}catch(InterruptedException e){
				
			}
		}
	}
	
	public void retire(){
		synchronized (this) {
			timeout = true;
			this.notify();
		}
		window.show_if_current(callerStation, callerStation);
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
				retire();
				break;
			default:
				break;
			}
		}
	}
}
