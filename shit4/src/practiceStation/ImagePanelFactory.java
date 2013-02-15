package practiceStation;

import javax.swing.JPanel;

import GUI.IImagePanel;
import GUI.IListImages;
import GUI.ImagePanel;
import GUI.Main_Window;

public class ImagePanelFactory implements IImagePanelFactory {

	@Override
	public IImagePanel createInstance(IListImages images, JPanel caller, Main_Window mainWindow) {
		return new ImagePanel(images,caller, mainWindow);
	}

}
