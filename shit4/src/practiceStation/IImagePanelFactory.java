package practiceStation;


import javax.swing.JPanel;

import GUI.IImagePanel;
import GUI.IListImages;


public interface IImagePanelFactory {
	
	/**
	 * 
	 * @param images: images to show
	 * @param caller: the caller station
	 * @return a new object implement ImagePanel
	 */
	IImagePanel createInstance(IListImages images, JPanel caller);
}
