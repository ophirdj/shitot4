package practiceStation;

import java.util.Map;

import javax.swing.JPanel;

import dictionaries.Languages;

import GUI.IImagePanel;
import GUI.IListImages;
import GUI.ImagePanel;
import GUI.Main_Window;
import GUI.StationPanel;

public class ImagePanelFactory implements IImagePanelFactory {

	Main_Window mainWindow;
	
	public ImagePanelFactory(Main_Window main_window) {
		this.mainWindow = main_window;
	}
	
	@Override
	public IImagePanel createInstance(Map<Languages,IListImages> images, StationPanel caller) {
		return new ImagePanel(images,caller,mainWindow);
	}

}
