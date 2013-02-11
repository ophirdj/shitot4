package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class ImagePanel  extends JPanel{
	static final long serialVersionUID = 1L;
	
	File fileToShow;
	int fileShownIndex;
	IListImages images_list;
	
	
	public ImagePanel(IListImages images_list) {
		super(new BorderLayout());
		this.images_list = images_list;
		fileToShow = images_list.getFile(0);
		fileShownIndex = 0;
		repaint();
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
		repaint();
	}
	
	public void showPrevImage() {
		if(0 <= fileShownIndex) return;
		fileToShow = images_list.getFile(--fileShownIndex);
		repaint();
	}
	 
	/** Paint the histogram */
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    try {
	    	BufferedImage img = ImageIO.read(fileToShow);
	    	 g.drawImage(img, 0, 0, getWidth(), getHeight(), new Color(0,0,0), null);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	  }
}
