package practiceStation;


import javax.swing.JPanel;

import GUI.IImagePanel;
import GUI.IListImages;
import GUI.Main_Window;


public interface IImagePanelFactory {
	
	/**
	 * 
	 * @param images: images to show
	 * @param caller: the caller station
	 * @param mainWindow: main window
	 * @return a new object implement ImagePanel
	 */
	IImagePanel createInstance(IListImages images, JPanel caller, Main_Window mainWindow);
}
