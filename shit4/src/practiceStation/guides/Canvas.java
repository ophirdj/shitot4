package practiceStation.guides;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Canvas for drawing the guide on
 * 
 * @author Ziv Ronen
 * 
 */
public class Canvas extends JPanel {
	public static final long serialVersionUID = 1L;

	Image imageToShow;

	/**
	 * Show given image
	 * 
	 * @param imageToShow
	 */
	void showImage(Image imageToShow) {
		this.imageToShow = imageToShow;
		repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageToShow, 0, 0, getWidth(), getHeight(), new Color(0, 0,
				0), null);
	}
}
