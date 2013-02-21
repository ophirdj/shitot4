package practiceStation.factories;

import global.dictionaries.Languages;
import global.gui.Main_Window;
import global.gui.StationPanel;

import java.util.Map;

import practiceStation.guides.IImagePanel;
import practiceStation.guides.IListImages;
import practiceStation.guides.ImagePanel;



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
