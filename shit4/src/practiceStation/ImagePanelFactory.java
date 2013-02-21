package practiceStation;

import javax.swing.JPanel;

import GUI.IImagePanel;
import GUI.IListImages;
import GUI.ImagePanel;
import GUI.Main_Window;

public class ImagePanelFactory implements IImagePanelFactory {

	Main_Window mainWindow;
	
	public ImagePanelFactory(Main_Window main_window) {
		this.mainWindow = main_window;
	}
	
	@Override
	public IImagePanel createInstance(IListImages images, JPanel caller) {
		return new ImagePanel(images,caller,mainWindow);
	}

}
